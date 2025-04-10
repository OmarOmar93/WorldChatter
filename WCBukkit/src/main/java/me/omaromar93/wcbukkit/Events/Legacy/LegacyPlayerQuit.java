package me.omaromar93.wcbukkit.Events.Legacy;

import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class LegacyPlayerQuit extends PlayerListener {

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId());
        PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player,false);
        PlayerHandler.INSTANCE.removePlayer(player);
        if(PlayerJoiningQuitting.INSTANCE.isQuitEnabled()) event.setQuitMessage("");
    }
}
