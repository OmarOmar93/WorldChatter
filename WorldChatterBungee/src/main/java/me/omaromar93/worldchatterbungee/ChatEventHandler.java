package me.omaromar93.worldchatterbungee;

import Others.CacheSystem;
import Others.ConfigSystem;
import UniversalFunctions.Player;
import chatting.ChattingSystem;
import functions.BungeePlayer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEventHandler implements Listener {

    @EventHandler
    public void onChat(final ChatEvent event) {
        if (event.isCommand()) return;
        event.setCancelled(true);
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        final UniversalFunctions.ChatEvent chatEvent = new UniversalFunctions.ChatEvent(event, false, true, (Player) CacheSystem.getOrAddCache("player:" + player.getUniqueId(), new BungeePlayer(player)), "<" + player.getName() + "> " + event.getMessage(), event.getMessage(), null);
        final TextComponent message;
        if (ConfigSystem.INSTANCE.getConfig().getStringList("BlackListPlaces").contains(player.getServer().getInfo().getName())) {
            message = new TextComponent(chatEvent.getFormat());
            if (ConfigSystem.INSTANCE.getConfig().getBoolean("solomessage")) {
                broadcastMessage(player.getServer().getInfo(), message);
                return;
            }

            broadcastMessage(null, message);
            return;
        }

        ChattingSystem.returnFormattedMessage(chatEvent,false);
        if (chatEvent.isCancelled()) return;
        message = new TextComponent(chatEvent.getMessage());

        if (ConfigSystem.INSTANCE.getConfig().getBoolean("GlobalChat")) {
            broadcastMessage(null, message);
            return;
        }

        broadcastMessage(player.getServer().getInfo(), message);
    }

    private void broadcastMessage(final ServerInfo server, final TextComponent message) {
        if (server != null) {
            if (!ConfigSystem.INSTANCE.getFormat().getBoolean("NewLine")) {
                message.setText(message.getText().replace("\n", ""));
            }
            for (final ProxiedPlayer player : server.getPlayers()) {
                player.sendMessage(message);
            }
            return;
        }

        for (final ServerInfo serverInfo : ProxyServer.getInstance().getServers().values()) {
            broadcastMessage(serverInfo, message);
        }
    }

}