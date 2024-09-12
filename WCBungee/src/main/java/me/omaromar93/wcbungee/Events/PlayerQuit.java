package me.omaromar93.wcbungee.Events;

import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public final class PlayerQuit implements Listener {
    @EventHandler
    public void onQuit(final PlayerDisconnectEvent event) {
        final Player player = PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId());
        if (player == null) return;

        PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player, false);
        PlayerHandler.INSTANCE.removePlayer(player);
    }
}