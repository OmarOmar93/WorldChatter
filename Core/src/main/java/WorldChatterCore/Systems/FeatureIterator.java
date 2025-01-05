package WorldChatterCore.Systems;


import WorldChatterCore.Channels.ChannelManager;
import WorldChatterCore.Features.*;
import WorldChatterCore.Players.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class FeatureIterator {

    private final List<String> security;
    public static FeatureIterator INSTANCE;

    public FeatureIterator() {
        INSTANCE = this;

        security = new CopyOnWriteArrayList<>();
    }

    public List<String> securityCheck(final Player player, final String message) {
        security.clear();
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
        if (!player.hasPermission("worldchatter.bypass.antirepeat")
                && ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiRepeat.enabled")
                && AntiRepeat.INSTANCE.isSimilarMessage(player.getUniqueId(), message))
            security.add("Anti-Repeat");
        return security;
    }

    public String prepareTheMessage(final String message, final Player player) {
        return ChatFormatter.INSTANCE.formatMessage(message, player);
    }

    public void initalizeTheMessage(final String format, final String message, final Player player) {
        ChannelManager.INSTANCE.sendMessage(player, MiniMessageConnector.INSTANCE != null ? MiniMessageConnector.INSTANCE.returnFormattedString(format + message) : ColorSystem.tCC(format + message));
    }
}