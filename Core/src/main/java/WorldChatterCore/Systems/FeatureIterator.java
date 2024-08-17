package WorldChatterCore.Systems;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Features.*;
import WorldChatterCore.Others.ServerOptions;
import WorldChatterCore.Players.Player;

import java.util.ArrayList;
import java.util.List;

public class FeatureIterator {

    public static FeatureIterator INSTANCE;
    private final List<String> security = new ArrayList<>();


    public FeatureIterator() {
        INSTANCE = this;
    }

    public List<String> securityCheck(final Player player, final String message) {
        if (
                !player.hasPermission("worldchatter.bypass.antiads") &&
                        ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiADS") &&
                        AntiADS.hasAds(message)
        )
            security.add("Anti-ADs");
        if (
                !player.hasPermission("worldchatter.bypass.anticaps") &&
                        ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiCaps.enabled") &&
                        AntiCaps.INSTANCE.hasAlotOfCaps(message)
        )
            security.add("Anti-Caps");
        if (
                !player.hasPermission("worldchatter.bypass.antiswear") &&
                        ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiSwear.enabled") &&
                        AntiSwear.INSTANCE.containsCurseWord(message)
        )
            security.add("Anti-Swear");
        return security;
    }

    public String preparetheMessage(final String message, final Player player) {
        return ChatFormatter.INSTANCE.formatMessage(message, player);
    }

    public void initalizeTheMessage(final String format, final String message, final Player player) {
        if (ServerOptions.INSTANCE.isGlobalChat()) {
            MainPluginConnector.INSTANCE.getWorldChatter().broadcastMessage(format + MiniMessageConnector.INSTANCE.returnFormattedString(message));
            return;
        }
        final List<Player> placePlayers = ServerOptions.INSTANCE.getPlayersinPlace(player.getPlace());
        for (Player p : placePlayers) {
            p.sendMessage(format + MiniMessageConnector.INSTANCE.returnFormattedString(message));
        }
    }

}