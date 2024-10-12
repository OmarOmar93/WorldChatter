package WorldChatterCore.Others;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Features.PlaceHolders;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ServerOptions {

    public static ServerOptions INSTANCE;
    private boolean globalChat, switchMessage;
    private boolean switchLevel;
    private String preMessage, coMessage;

    public ServerOptions() {
        INSTANCE = this;
    }

    public void update() {
        globalChat = ConfigSystem.INSTANCE.getPlace().getBoolean("GlobalChat");
        switchMessage = ConfigSystem.INSTANCE.getMessages().getBoolean("SwitchSettings.enabled");
        if (switchMessage) {
            preMessage = ConfigSystem.INSTANCE.getMessages().getString("SwitchSettings.premessage");
            coMessage = ConfigSystem.INSTANCE.getMessages().getString("SwitchSettings.comessage");
            switchLevel = ConfigSystem.INSTANCE.getMessages().getBoolean("SwitchSettings.global");
            return;
        }
        preMessage = null;
        coMessage = null;
    }

    public boolean isSwitchMessage() {
        return switchMessage;
    }

    public List<Player> getPlayersinPlace(final String place) {
        final List<Player> placePlayers = new ArrayList<>();
        for (final Player p : PlayerHandler.INSTANCE.getPlayers().values()) {
            if (place.equalsIgnoreCase(p.getRawPlace())) {
                placePlayers.add(p);
            }
        }
        return placePlayers;
    }

    public void loopType(final Player joiner, final String previous, final String current) {
        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(formatQuickPlayerServers(preMessage, joiner, previous, current));
        final Collection<Player> playerList;
        if (!switchLevel) {
            playerList = getPlayersinPlace(previous);
            playerList.addAll(getPlayersinPlace(current));
        } else {
            playerList = PlayerHandler.INSTANCE.getPlayers().values();
        }
        for (final Player player : playerList) {
            if (player != joiner) {
                player.sendMessage(formatQuickPlayerServers(player.getRawPlace().equalsIgnoreCase(previous) ? preMessage : coMessage, joiner, previous, current));
            }
        }
    }

    private String formatQuickPlayerServers(final String message, final Player player, final String previous, final String current) {
        return PlaceHolders.applyPlaceHoldersifPossible(ColorSystem.tCC(message), player)
                .replace("%previous_server%", previous)
                .replace("%current_server%", current);
    }

    public boolean isGlobalChat() {
        return globalChat;
    }
}
