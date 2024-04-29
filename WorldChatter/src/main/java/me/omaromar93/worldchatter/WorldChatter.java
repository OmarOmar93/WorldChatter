package me.omaromar93.worldchatter;

import Others.ConfigSystem;
import Others.PlayerSystem;
import Others.ThreadsSystem;
import Others.UpdaterSystem;
import UniversalFunctions.UniLogHandler;
import me.omaromar93.worldchatter.PAPI.PAPIDependSystem;
import me.omaromar93.worldchatter.PAPI.PAPIExpansion;
import me.omaromar93.worldchatter.functions.SpigotLog;
import me.omaromar93.worldchatter.functions.SpigotPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static org.bukkit.Bukkit.getConsoleSender;

public class WorldChatter extends JavaPlugin {

    public static WorldChatter INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        new ConfigSystem(getLogger());
        new PAPIDependSystem();
        new BroadcastSystem();
        new UniLogHandler(new SpigotLog());
        getServer().getPluginManager().registerEvents(new ChatEventHandler(), this);
        Objects.requireNonNull(getCommand("worldchatter")).setExecutor(new CommandSystem());
        ThreadsSystem.runAsync(() -> {
            ConfigSystem.INSTANCE.update();
            getServer().getPluginManager().registerEvents(new PlayerEventHandler(), this);

            final String s = PAPIDependSystem.INSTANCE.isPAPIThere() ? ChatColor.GREEN + "Your server does have " + ChatColor.BLUE + "'PlaceholderAPI'" + ChatColor.GREEN + " installed! " + ChatColor.WHITE + "(Unlocking the boundaries with PlaceholderAPI and Unlocking WorldChatter's PlaceHolders!)" : ChatColor.RED + "Your server does not have " + ChatColor.BLUE + "'PlaceholderAPI'" + ChatColor.RED + " installed! " + ChatColor.WHITE + "(Limited to only the built-in expressions)";
            getConsoleSender().sendMessage(ChatColor.GOLD + "[WorldChatter] " + s);
            for (final Player player : getServer().getOnlinePlayers()) {
                PlayerSystem.INSTANCE.addPlayer(player.getUniqueId(), new SpigotPlayer(player));
            }
            final Boolean b = UpdaterSystem.isUpdated();
            if (b == null) {
                getConsoleSender().sendMessage(ChatColor.GOLD + "[WorldChatter] " + ChatColor.RED + "Error has occurred while fetching the update.");
                return;
            }
            if (b) {
                getConsoleSender().sendMessage(ChatColor.GOLD + "[WorldChatter] " + ChatColor.YELLOW + "WorldChatter has released a new update! " + ChatColor.WHITE + "-> " + ChatColor.GREEN + UpdaterSystem.newupdate + ChatColor.BLUE + "\nDownload the update at https://www.spigotmc.org/resources/worldchatter.101226/");
                return;
            }
            getConsoleSender().sendMessage(ChatColor.GOLD + "[WorldChatter] " + ChatColor.YELLOW + "WorldChatter is in it's latest update!");
        });
        if (PAPIDependSystem.INSTANCE.isPAPIThere()) new PAPIExpansion().register();
    }

    @Override
    public void onDisable() {
        getConsoleSender().sendMessage(ChatColor.GOLD + "[WorldChatter] " + ChatColor.BLUE + "Goodbye and thanks for using WorldChatter ^ - ^");
    }
}