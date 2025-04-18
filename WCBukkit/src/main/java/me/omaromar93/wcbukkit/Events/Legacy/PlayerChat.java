package me.omaromar93.wcbukkit.Events.Legacy;

import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ThreadsSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public final class PlayerChat extends PlayerListener {

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerChat(final PlayerChatEvent event) {
        if (event.isCancelled()) return;
        event.getRecipients().clear();
        ThreadsSystem.runAsync(() -> new ChatEventConnector(PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId()), event.getMessage()));
    }
}