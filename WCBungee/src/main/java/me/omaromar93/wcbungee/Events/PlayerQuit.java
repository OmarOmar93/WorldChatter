package me.omaromar93.wcbungee.Events;

import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerQuit implements Listener {
    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        final Player player = PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId());
        PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player,false);
        PlayerHandler.INSTANCE.removePlayer(player);
    }
}