package me.omaromar93.wcvelocity;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.MainPlugin;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import me.omaromar93.wcvelocity.Events.PlayerChat;
import me.omaromar93.wcvelocity.Events.PlayerJoin;
import me.omaromar93.wcvelocity.Events.PlayerQuit;
import me.omaromar93.wcvelocity.Parent.Command;
import me.omaromar93.wcvelocity.Parent.VelocityPlayer;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Locale;
import java.util.Objects;

@Plugin(
        id = "worldchatter",
        name = "WorldChatterPREVIEW",
        version = "3.0.0",
        description = "Enhance your Chatting Experience.",
        authors = {"OmarOmar93"}
)
public class WCVelocity implements MainPlugin {

    private final ProxyServer server;
    private final PluginContainer pluginContainer;


    @Inject
    public WCVelocity(ProxyServer server, PluginContainer pluginContainer) {
        this.server = server;
        this.pluginContainer = pluginContainer;
        new MainPluginConnector();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        MainPluginConnector.INSTANCE.setWorldChatter(this);
        server.getEventManager().register(this, new PlayerChat());
        server.getEventManager().register(this, new PlayerJoin());
        server.getEventManager().register(this, new PlayerQuit());
        final CommandManager cm = server.getCommandManager();
        cm.register(cm.metaBuilder("worldchatter")
                .aliases("wc")
                .plugin(server)
                .build(), new Command());
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
        for (com.velocitypowered.api.proxy.Player player : server.getAllPlayers()) {
            PlayerHandler.INSTANCE.addPlayer(new VelocityPlayer(player));
        }
    }

    @Override
    public void broadcastMessage(final String message) {
        server.getAllPlayers().forEach(player ->
                player.sendMessage(MiniMessage.miniMessage().deserialize(message)));
    }

    @Override
    public String supporttheMessage(final String message, final Player player) {
        com.velocitypowered.api.proxy.Player p = server.getPlayer(player.getUniqueId()).get();
        return message
                .replace("%player_ping%", String.valueOf(p.getPing()))
                .replace("%player_clientbrand%", Objects.requireNonNull(p.getClientBrand()))
                .replace("%player_protocolversion%", p.getProtocolVersion().toString())
                .replace("%player_protocol%", String.valueOf(p.getProtocolVersion().getProtocol()));
    }

    @Override
    public void tryToSupportMiniMessage() {
        sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "MiniMessage is already built-in..! yey");
    }

    @Override
    public String getVersion() {
        return pluginContainer.getDescription().getVersion().toString();
    }
}