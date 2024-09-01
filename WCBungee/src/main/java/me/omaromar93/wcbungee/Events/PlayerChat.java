package me.omaromar93.wcbungee.Events;

import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Players.PlayerHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public final class PlayerChat implements Listener {

    @EventHandler
    public void onChat(final ChatEvent event) {
        event.setCancelled(true);
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        new ChatEventConnector(PlayerHandler.INSTANCE.getPlayerUUID(player.getUniqueId()), event.getMessage());
    }
}
