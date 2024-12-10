package me.omaromar93.wcbungee;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.MainPlugin;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import me.omaromar93.wcbungee.Events.PlayerChat;
import me.omaromar93.wcbungee.Events.PlayerJoin;
import me.omaromar93.wcbungee.Events.PlayerQuit;
import me.omaromar93.wcbungee.Parent.BungeePlayer;
import me.omaromar93.wcbungee.Parent.Command;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public final class WCBungee extends Plugin implements MainPlugin {

    public static BungeeAudiences adventure;

    public WCBungee() {
        new MainPluginConnector();
    }

    @Override
    public void onEnable() {
        MainPluginConnector.INSTANCE.setWorldChatter(this);
        getProxy().getPluginManager().registerListener(this, new PlayerChat());
        getProxy().getPluginManager().registerListener(this, new PlayerJoin());
        getProxy().getPluginManager().registerListener(this, new PlayerQuit());
        getProxy().getPluginManager().registerCommand(this, new Command());
        MainPluginConnector.INSTANCE.loadSupportedPlugins();
    }

    @Override
    public void onDisable() {
        MainPluginConnector.INSTANCE.onDisable();
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    @Override
    public boolean isPluginEnabled(final String name) {
        return getProxy().getPluginManager().getPlugin(name) != null;
    }

    @Override
    public void sendConsoleMessage(final String message) {
        try {
            adventure.console().sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
        } catch (final NoSuchMethodError ignored) {
            getProxy().getConsole().sendMessage(new TextComponent(message));
        }
    }

    @Override
    public void refreshPlayers() {
        for (final ProxiedPlayer player : getProxy().getPlayers()) {
            PlayerHandler.INSTANCE.addPlayer(new BungeePlayer(player,null));
        }
    }

    @Override
    public void broadcastMessage(final String message) {
        try {
            adventure.all().sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
        } catch (final NoSuchMethodError ignored) {
            getProxy().broadcast(new TextComponent(message));
        }
    }

    @Override
    public String supporttheMessage(final String message, final Player player) {
        return message
                .replace("%player_ping%", String.valueOf(getProxy().getPlayer(player.getUniqueId()).getPing()));
    }

    @Override
    public void tryToSupportMiniMessage() {
        adventure = BungeeAudiences.create(this);
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }
}
