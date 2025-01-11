package me.omaromar93.wcbungee.Events;

import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Players.PlayerHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public final class PlayerChat implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final ChatEvent event) {
        if (event.isCancelled()) return;
        if (event.getMessage().startsWith("/")) return;
        event.getReceiver().disconnect();
        new ChatEventConnector(PlayerHandler.INSTANCE.getPlayerUUID(((ProxiedPlayer) event.getSender()).getUniqueId()), event.getMessage());
    }
}
