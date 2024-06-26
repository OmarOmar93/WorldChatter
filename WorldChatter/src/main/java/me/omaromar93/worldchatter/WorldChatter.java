package me.omaromar93.worldchatter;

import API.APICore;
import API.WorldChatterAPI;
import Others.ConfigSystem;
import Others.PlayerSystem;
import Others.ThreadsSystem;
import Others.UpdaterSystem;
import UniversalFunctions.UniLogHandler;
import me.omaromar93.worldchatter.Legacy.LegacyBroadcastSystem;
import me.omaromar93.worldchatter.Legacy.LegacyChatEventHandler;
import me.omaromar93.worldchatter.Legacy.LegacyPlayerEventHandler;
import me.omaromar93.worldchatter.Legacy.LegacySpigotPlayer;
import me.omaromar93.worldchatter.PAPI.PAPIDependSystem;
import me.omaromar93.worldchatter.PAPI.PAPIExpansion;
import me.omaromar93.worldchatter.functions.SpigotLog;
import me.omaromar93.worldchatter.functions.SpigotPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.Objects;

import static org.bukkit.Bukkit.getConsoleSender;

public class WorldChatter extends JavaPlugin {

    public static WorldChatter INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        new ConfigSystem(Bukkit.getLogger());
        new PAPIDependSystem();
        boolean legacy = false;
        try {
            new BroadcastSystem();
        } catch (final NoClassDefFoundError ignored) {
            new LegacyBroadcastSystem();
            legacy = true;
        }
        new UniLogHandler(new SpigotLog());
        if(isBeforeVersion()){
            getServer().getPluginManager().registerEvents(new LegacyChatEventHandler(), this);
        } else{
            getServer().getPluginManager().registerEvents(new ChatEventHandler(), this);
        }
        Objects.requireNonNull(getCommand("worldchatter")).setExecutor(new CommandSystem());
        final boolean finalLegacy = legacy;
        ThreadsSystem.runAsync(() -> {
            ConfigSystem.INSTANCE.update();
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners()) api.configReload(null, null);
            if (!finalLegacy) {
                getServer().getPluginManager().registerEvents(new PlayerEventHandler(), this);
                final String s = PAPIDependSystem.INSTANCE.isPAPIThere() ? ChatColor.GREEN + "Your server does have " + ChatColor.BLUE + "'PlaceholderAPI'" + ChatColor.GREEN + " installed! " + ChatColor.WHITE + "(Unlocking the boundaries with PlaceholderAPI and Unlocking WorldChatter's PlaceHolders!)" : ChatColor.RED + "Your server does not have " + ChatColor.BLUE + "'PlaceholderAPI'" + ChatColor.RED + " installed! " + ChatColor.WHITE + "(Limited to only the built-in expressions)";
                getConsoleSender().sendMessage(ChatColor.GOLD + "[WorldChatter] " + s);
            } else {
                getConsoleSender().sendMessage(ChatColor.GOLD + "[WorldChatter] " + ChatColor.YELLOW + "Looks like we're going far back! " + ChatColor.LIGHT_PURPLE + "[WorldChatter Legacy Mode]");
                getServer().getPluginManager().registerEvents(new LegacyPlayerEventHandler(), this);
            }
            ConfigSystem.INSTANCE.updatePlayerEvent();


            try {
                for (final Player player : getServer().getOnlinePlayers()) {
                    PlayerSystem.INSTANCE.addPlayer(player.getUniqueId(), new SpigotPlayer(player));
                }
            } catch (final NoSuchMethodError ignored) {
                for (final OfflinePlayer player : getServer().getOfflinePlayers()) {
                    if (player.isOnline()) {
                        Player p = (Player) player;
                        PlayerSystem.INSTANCE.addPlayer(p.getUniqueId(), new LegacySpigotPlayer(p));
                    }
                }
            }
            final Boolean b = UpdaterSystem.isUpdated();
            if (b == null) {
                getConsoleSender().sendMessage(ChatColor.RED + "Error has occurred while fetching the update.");
            }
            if (Boolean.TRUE.equals(b)) {
                getConsoleSender().sendMessage(ChatColor.YELLOW + "WorldChatter has released a new" + (UpdaterSystem.isDev ? ChatColor.GOLD + " DEVELOPMENT" : "") + ChatColor.YELLOW + " update! " + ChatColor.GRAY + "( " + ChatColor.GOLD + UpdaterSystem.updatetitle + ChatColor.GRAY + " )" + ChatColor.WHITE + " -> " + ChatColor.GREEN + UpdaterSystem.newupdate + ChatColor.BLUE + "\nDownload the update at https://www.spigotmc.org/resources/worldchatter.101226/");
            } else {
                getConsoleSender().sendMessage(ChatColor.YELLOW + "WorldChatter is in it's latest update!");
            }
        });
        if (PAPIDependSystem.INSTANCE.isPAPIThere()) new PAPIExpansion().register();
    }

    @Override
    public void onDisable() {
        getConsoleSender().sendMessage(ChatColor.GOLD + "[WorldChatter] " + ChatColor.BLUE + "Goodbye and thanks for using WorldChatter ^ - ^");
    }

    private boolean isBeforeVersion() {
        final String[] currentVersionParts = Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.");
        final String[] targetVersionParts = "1.3.1".split("\\.");

        for (int i = 0; i < targetVersionParts.length; i++) {
            final int current = i < currentVersionParts.length ? Integer.parseInt(currentVersionParts[i]) : 0;
            final int target = Integer.parseInt(targetVersionParts[i]);
            if (current < target) return true;
            if (current > target) return false;
        }
        return false;
    }

}