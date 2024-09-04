package me.omaromar93.wcvelocity;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.MainPlugin;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ThreadsSystem;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.omaromar93.wcvelocity.Events.PlayerChat;
import me.omaromar93.wcvelocity.Events.PlayerJoin;
import me.omaromar93.wcvelocity.Events.PlayerQuit;
import me.omaromar93.wcvelocity.Parent.Command;
import me.omaromar93.wcvelocity.Parent.VelocityPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.*;

@Plugin(
        id = "worldchatter",
        name = "WorldChatter",
        version = "3.0.0",
        description = "Enhance your Chatting Experience.",
        authors = {"OmarOmar93"}
)
public final class WCVelocity implements MainPlugin {

    private final ProxyServer server;


    @Inject
    public WCVelocity(final ProxyServer server) {
        this.server = server;
        new MainPluginConnector();
        MainPluginConnector.INSTANCE.setWorldChatter(this);
        ThreadsSystem.runAsync(() -> {
            server.getEventManager().register(this, new PlayerChat());
            server.getEventManager().register(this, new PlayerJoin());
            server.getEventManager().register(this, new PlayerQuit());
            final CommandManager cm = server.getCommandManager();
            cm.register(cm.metaBuilder("worldchatter")
                    .aliases("wc")
                    .plugin(server)
                    .build(), new Command());
        });
    }

    @Override
    public boolean isPluginEnabled(final String name) {
        return server.getPluginManager().isLoaded(name.toLowerCase(Locale.ROOT));
    }

    @Override
    public void sendConsoleMessage(final String message) {
        server.getConsoleCommandSource().sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
    }

    @Override
    public void refreshPlayers() {
        for (final com.velocitypowered.api.proxy.Player player : server.getAllPlayers()) {
            PlayerHandler.INSTANCE.addPlayer(new VelocityPlayer(player, null));
        }
    }

    @Override
    public void broadcastMessage(final String message) {
        final Component component = MiniMessage.miniMessage().deserialize(message);
        for (final com.velocitypowered.api.proxy.Player player : server.getAllPlayers()) {
            player.sendMessage(component);
        }
    }

    @Override
    public String supporttheMessage(final String message, final Player player) {
        final VelocityPlayer velocityPlayer = (VelocityPlayer) player;

        final com.velocitypowered.api.proxy.Player p = velocityPlayer.getVelocityPlayer();
        final String clientBrand = p.getClientBrand();
        return message
                .replace("%player_ping%", String.valueOf(p.getPing()))
                .replace("%player_clientbrand%", clientBrand == null ? "" : clientBrand)
                .replace("%player_protocolversion%", p.getProtocolVersion().toString())
                .replace("%player_protocol%", String.valueOf(p.getProtocolVersion().getProtocol()));
    }

    @Override
    public void tryToSupportMiniMessage() {}

    @Override
    public String getVersion() {
        return "3.0.0";
    }
}