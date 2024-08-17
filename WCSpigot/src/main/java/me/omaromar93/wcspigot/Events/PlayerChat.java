package me.omaromar93.wcspigot.Events;

import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ThreadsSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChat implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onLegacyPlayerChat(final PlayerChatEvent event) {
        event.setCancelled(true);
        ThreadsSystem.runAsync(() -> new ChatEventConnector(PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId()), event.getMessage()));
    }
}
