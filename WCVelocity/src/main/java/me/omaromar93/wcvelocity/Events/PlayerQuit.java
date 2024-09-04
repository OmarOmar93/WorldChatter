package me.omaromar93.wcvelocity.Events;

import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;

public final class PlayerQuit {
    @Subscribe
    public void onPlayerLeave(final DisconnectEvent event) {
        final Player player = PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId());
        if(player == null) return;
        PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player, false);
        PlayerHandler.INSTANCE.removePlayer(player);
    }
}
