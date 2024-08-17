package me.omaromar93.wcvelocity.Events;


import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import me.omaromar93.wcvelocity.Parent.VelocityPlayer;

public class PlayerJoin {
    @Subscribe
    public void onPlayerLogin(LoginEvent event) {
        final Player player = new VelocityPlayer(event.getPlayer());
        PlayerHandler.INSTANCE.addPlayer(player);
        PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player, true);
    }
}