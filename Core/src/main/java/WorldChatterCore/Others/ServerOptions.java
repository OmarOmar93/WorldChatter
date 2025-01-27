package WorldChatterCore.Others;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Features.PlaceHolders;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ServerOptions {

    public static ServerOptions INSTANCE;
    private boolean switchMessage;
    private boolean switchGlobal;
    private String preMessage, coMessage;

    public ServerOptions() {
        INSTANCE = this;
    }

    public void update() {
        switchMessage = ConfigSystem.INSTANCE.getMessages().getBoolean("SwitchSettings.enabled");
        if (switchMessage) {
            preMessage = ConfigSystem.INSTANCE.getMessages().getString("SwitchSettings.premessage");
            coMessage = ConfigSystem.INSTANCE.getMessages().getString("SwitchSettings.comessage");
            switchGlobal = ConfigSystem.INSTANCE.getMessages().getBoolean("SwitchSettings.global");
            return;
        }
        preMessage = null;
        coMessage = null;
    }

    public boolean isSwitchMessage() {
        return switchMessage;
    }

    public List<Player> getPlayersinPlace(final String place) {
        return PlayerHandler.INSTANCE.getPlayers().values().stream()
                .filter(p -> place.equalsIgnoreCase(p.getRawPlace()))
                .collect(Collectors.toList());
    }


    public void loopType(final Player joiner, final String previous, final String current) {
        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(formatQuickPlayerServers(preMessage, joiner, previous, current));
        for (final Player player : !switchGlobal ? Stream.concat(getPlayersinPlace(previous).stream(), getPlayersinPlace(current).stream()).collect(Collectors.toCollection(ArrayList::new)) : PlayerHandler.INSTANCE.getPlayers().values()) {
            if (player != joiner) {
                player.sendMessage(formatQuickPlayerServers(player.getRawPlace().equalsIgnoreCase(previous) ? preMessage : coMessage, joiner, previous, current));
            }
        }
    }

    private String formatQuickPlayerServers(final String message, final Player player, final String previous, final String current) {
        return ColorSystem.tCC(PlaceHolders.applyPlaceHoldersifPossible(message
                .replace("{previous_server}", previous)
                .replace("{current_server}", current), player));
    }
}
