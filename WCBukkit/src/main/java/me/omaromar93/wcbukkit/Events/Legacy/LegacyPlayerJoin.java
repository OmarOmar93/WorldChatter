package me.omaromar93.wcbukkit.Events.Legacy;

import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import me.omaromar93.wcbukkit.Parent.BukkitPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public final class LegacyPlayerJoin extends PlayerListener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = new BukkitPlayer(event.getPlayer());
        PlayerHandler.INSTANCE.addPlayer(player);
        PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player,true);
        if(PlayerJoiningQuitting.INSTANCE.isJoinEnabled()) event.setJoinMessage("");
    }
}
