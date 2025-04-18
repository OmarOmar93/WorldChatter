package me.omaromar93.wcvelocity.Events;


import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Others.ServerOptions;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import me.omaromar93.wcvelocity.Parent.VelocityPlayer;

public final class PlayerJoin {
    @Subscribe
    public void onPlayerLogin(final ServerConnectedEvent event) {
        if (event.getPreviousServer().isEmpty()) {
            final Player player = new VelocityPlayer(event.getPlayer(), event.getServer().getServerInfo().getName());
            PlayerHandler.INSTANCE.addPlayer(player);
            PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player, true);
            return;
        }
        if (ServerOptions.INSTANCE.isSwitchMessage()) {
            ServerOptions.INSTANCE.loopType(PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId()),
                    event.getPreviousServer().get().getServerInfo().getName(),
                    event.getServer().getServerInfo().getName());
        }
    }
}