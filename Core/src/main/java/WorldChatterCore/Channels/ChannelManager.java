package WorldChatterCore.Channels;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Others.ServerOptions;
import WorldChatterCore.Others.debugMode;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ChannelManager {

    public static ChannelManager INSTANCE;
    private boolean globalSending;
    private Configuration channels;
    private static final List<Channel> channelList = new CopyOnWriteArrayList<>();

    /**
     * The Channel Management service is in your hands
     */
    public ChannelManager() {
        INSTANCE = this;
    }

    /**
     * Check if the global sending is enabled or not
     * @return {@link Boolean} based check
     */
    public boolean isGlobalSending() {
        return globalSending;
    }

    /**
     * This is executed by ConfigSystem's reload function
     */
    public void update() {
        globalSending = ConfigSystem.INSTANCE.getPlace().getBoolean("GlobalSending");
        channels = ConfigSystem.INSTANCE.getPlace().getSection("channels");
        channelList.clear();

        channels.getKeys().forEach(key -> {
            final List<String> places = channels.getStringList(key + ".places");
            final List<String> players = channels.getStringList(key + ".players");

            if (!isChannelExist(key)) {
                channelList.add(new Channel(key, places, players));
            } else {
                getChannel(key).setPlayerList(players).setPlaceList(places);
            }
        });

    }

    /**
     *
     * @param name the channel name
     * @return the channel's existence
     */
    public boolean isChannelExist(final String name) {
        return channelList.stream()
                .anyMatch(channel -> channel.getName().equalsIgnoreCase(name));
    }

    /**
     * Gets the channel by adding it's name
     * @param name the channel's name
     * @return the {@link Channel}
     */
    public Channel getChannel(final String name) {
        return channelList.stream()
                .filter(channel -> channel.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * The channel's send message
     * @param player the player
     * @param message the message
     */
    public void sendMessage(final Player player, final String message) {
        final List<Player> recipients = channels.getKeys().stream()
                .map(this::getChannel)
                .filter(channel -> channel != null && (
                        channel.getPlaceList().contains(player.getRawPlace()) ||
                                channel.getPlayerList().contains(player.getName())))
                .findFirst() // Stop at the first matching channel
                .map(channel -> {
                    final Stream<Player> playersInPlaces = channel.getPlaceList().stream()
                            .flatMap(place -> ServerOptions.INSTANCE.getPlayersinPlace(place).stream())
                            .filter(Objects::nonNull);

                    final Stream<Player> playersInList = channel.getPlayerList()
                            .stream()
                            .filter(Objects::nonNull)
                            .map(p -> PlayerHandler.INSTANCE.getPlayerName(p))
                            .filter(Objects::nonNull);

                    debugMode.INSTANCE.println(player.getName() + " is detected in channel \"" + channel.getName() + "\"", debugMode.printType.INFO);
                    return Stream.concat(playersInPlaces, playersInList);
                })
                .orElseGet(Stream::empty) // If no matching channel, return an empty stream
                .distinct()
                .collect(Collectors.toList());

        if (recipients.isEmpty()) {
            if (globalSending) {
                MainPluginConnector.INSTANCE.getWorldChatter().broadcastMessage(message);
                return;
            }
            ServerOptions.INSTANCE.getPlayersinPlace(player.getRawPlace()).stream()
                    .filter(Objects::nonNull) // Filter null players here as well
                    .forEach(p -> p.sendMessage(message));
            return;
        }
        debugMode.INSTANCE.println(player.getName() + "'s sent messages to " + recipients, debugMode.printType.INFO);
        recipients.forEach(p -> p.sendMessage(message));
    }
}