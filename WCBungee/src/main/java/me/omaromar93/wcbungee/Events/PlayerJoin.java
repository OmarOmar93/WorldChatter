package me.omaromar93.wcbungee.Events;

import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Others.ServerOptions;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import me.omaromar93.wcbungee.Parent.BungeePlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public final class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(final ServerConnectEvent event) {
        if (event.getPlayer().getServer() == null) {
            final Player player = new BungeePlayer(event.getPlayer(), event.getTarget().getName());
            PlayerHandler.INSTANCE.addPlayer(player);
            PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player, true);
            return;
        }
        if (ServerOptions.INSTANCE.isSwitchMessage()) {
            ServerOptions.INSTANCE.loopType(PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId()),
                    event.getPlayer().getServer().getInfo().getName(),
                    event.getTarget().getName());
        }
    }
}
