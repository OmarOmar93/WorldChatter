package me.omaromar93.wcspigot;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.MainPlugin;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.PlayerHandler;
import com.onarandombox.MultiverseCore.MultiverseCore;
import me.clip.placeholderapi.PlaceholderAPI;
import me.omaromar93.wcspigot.Events.AsyncPlayerChat;
import me.omaromar93.wcspigot.Events.PlayerChat;
import me.omaromar93.wcspigot.Events.PlayerJoin;
import me.omaromar93.wcspigot.Events.PlayerQuit;
import me.omaromar93.wcspigot.Parent.Command;
import me.omaromar93.wcspigot.Parent.SpigotPlayer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class WCSpigot extends JavaPlugin implements MainPlugin {

    public static BukkitAudiences adventure;
    public static MultiverseCore mvcore;

    public WCSpigot() {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        mvcore = plugin != null ? (MultiverseCore) plugin : null;
        new MainPluginConnector();
    }


    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        try {
            pm.registerEvents(new AsyncPlayerChat(), this);
        } catch (Exception ignored) {
            pm.registerEvents(new PlayerChat(), this);
        }
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerQuit(), this);
        MainPluginConnector.INSTANCE.setWorldChatter(this);
        getCommand("worldchatter").setExecutor(new Command());
    }

    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        MainPluginConnector.INSTANCE.onDisable();
    }

    @Override
    public boolean isPluginEnabled(final String name) {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }

    @Override
    public void sendConsoleMessage(final String message) {
        if (adventure != null) {
            adventure.console().sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
            return;
        }
        Bukkit.getConsoleSender().sendMessage(message);
    }

    @Override
    public void refreshPlayers() {
        try {
            if (!getServer().getOnlinePlayers().isEmpty()) {
                for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
                    PlayerHandler.INSTANCE.addPlayer(new SpigotPlayer(p));
                }
            }
        } catch (final NoSuchMethodError ignored) {
            for (final OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                if (p.isOnline()) PlayerHandler.INSTANCE.addPlayer(new SpigotPlayer((Player) p));
            }
        }
    }

    @Override
    public void broadcastMessage(final String message) {
        if(adventure != null) {
            adventure.all().sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
            return;
        }
        getServer().broadcastMessage(message);
    }

    @Override
    public String supporttheMessage(final String message, final WorldChatterCore.Players.Player player) {
        if (MainPluginConnector.INSTANCE.getWorldChatter().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(getServer().getPlayer(player.getUniqueId()), message);
        }
        return message;
    }

    @Override
    public void tryToSupportMiniMessage() throws NoSuchMethodError {
        adventure = BukkitAudiences.create(this);
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

}