package WorldChatterCore.Systems;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Features.*;
import WorldChatterCore.Others.ServerOptions;
import WorldChatterCore.Players.Player;

import java.util.ArrayList;
import java.util.List;

public final class FeatureIterator {

    private final List<String> security;
    public static FeatureIterator INSTANCE;

    public FeatureIterator() {
        INSTANCE = this;

        security = new ArrayList<>();
    }

    public List<String> securityCheck(final Player player, final String message) {
        if (!player.hasPermission("worldchatter.bypass.antiads")
                && ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiADS")
                && AntiADS.hasAds(message))

            security.add("Anti-ADs");
        if (!player.hasPermission("worldchatter.bypass.anticaps")
                && ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiCaps.enabled")
                && AntiCaps.INSTANCE.hasAlotOfCaps(message))
            security.add("Anti-Caps");

        if (!player.hasPermission("worldchatter.bypass.antiswear")
                && ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiSwear.enabled")
                && AntiSwear.INSTANCE.containsCurseWord(message))
            security.add("Anti-Swear");
        return security;
    }

    public String prepareTheMessage(final String message, final Player player) {
        return ChatFormatter.INSTANCE.formatMessage(message, player);
    }

    public void initalizeTheMessage(final String format, final String message, final Player player) {
        final String sendmessage = MiniMessageConnector.INSTANCE != null ? MiniMessageConnector.INSTANCE.returnFormattedString(format + message) : ColorSystem.tCC(format + message);
        if (ServerOptions.INSTANCE.isGlobalChat()) {
            MainPluginConnector.INSTANCE.getWorldChatter().broadcastMessage(sendmessage);
            return;
        }
        final List<Player> placePlayers = ServerOptions.INSTANCE.getPlayersinPlace(player.getPlace());

        for (final Player p : placePlayers) {
            p.sendMessage(sendmessage);
        }
    }
}