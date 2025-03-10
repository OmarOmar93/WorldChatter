package me.omaromar93.wcspigot.Events.Legacy;


import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Features.PlayerJoiningQuitting;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ThreadsSystem;
import me.omaromar93.wcspigot.Parent.BukkitPlayer;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Method;

public class LegacyEventInterceptor {
    public void intercept(final Method method, final Object[] args) {
        Player player;
        switch (method.getName()) {
            case "onPlayerChat":
                final PlayerChatEvent chatEvent = (PlayerChatEvent) args[0];
                // Handle chat event (for example, clear recipients and run async logic)
                if (!chatEvent.isCancelled()) {
                    chatEvent.getRecipients().clear();
                    // Your asynchronous processing here
                    ThreadsSystem.runAsync(() -> new ChatEventConnector(
                            PlayerHandler.INSTANCE.getPlayerUUID(chatEvent.getPlayer().getUniqueId()),
                            chatEvent.getMessage()));
                }
                break;
            case "onPlayerJoin":
                final PlayerJoinEvent joinEvent = (PlayerJoinEvent) args[0];
                player = new BukkitPlayer(joinEvent.getPlayer());
                PlayerHandler.INSTANCE.addPlayer(player);
                PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player,true);
                if(PlayerJoiningQuitting.INSTANCE.isJoinEnabled()) joinEvent.setJoinMessage("");
                break;
            case "onPlayerQuit":
                final PlayerQuitEvent quitEvent = (PlayerQuitEvent) args[0];
                player = PlayerHandler.INSTANCE.getPlayerUUID(quitEvent.getPlayer().getUniqueId());
                PlayerJoiningQuitting.INSTANCE.commitPlayerActivities(player,false);
                PlayerHandler.INSTANCE.removePlayer(player);
                if(PlayerJoiningQuitting.INSTANCE.isQuitEnabled()) quitEvent.setQuitMessage("");
                break;
            default:
                break;
        }
    }
}
