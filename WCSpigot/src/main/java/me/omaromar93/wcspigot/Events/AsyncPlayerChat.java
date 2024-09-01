package me.omaromar93.wcspigot.Events;

import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Players.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class AsyncPlayerChat implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        new ChatEventConnector(PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId()), event.getMessage());
    }
}
