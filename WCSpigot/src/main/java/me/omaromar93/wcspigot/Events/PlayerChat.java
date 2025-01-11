package me.omaromar93.wcspigot.Events;

import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ThreadsSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public final class PlayerChat implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLegacyPlayerChat(final PlayerChatEvent event) {
        if (event.isCancelled()) return;
        event.getRecipients().clear();
        ThreadsSystem.runAsync(() -> new ChatEventConnector(PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId()), event.getMessage()));
    }
}