package me.omaromar93.wcspigot.Events;

import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import me.omaromar93.wcspigot.Parent.BukkitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoin implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = new BukkitPlayer(event.getPlayer());
        PlayerHandler.INSTANCE.addPlayer(player);
        PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player,true);
        if(PlayerJoiningQuitting.INSTANCE.isJoinEnabled()) event.setJoinMessage("");

    }
}
