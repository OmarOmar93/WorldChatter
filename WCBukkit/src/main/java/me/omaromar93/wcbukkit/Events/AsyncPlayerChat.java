package me.omaromar93.wcbukkit.Events;

import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Players.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class AsyncPlayerChat implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;
        event.getRecipients().clear();
        new ChatEventConnector(PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId()), event.getMessage());
    }
}