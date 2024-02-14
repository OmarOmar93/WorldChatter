package me.omaromar93.worldchatter;

import me.omaromar93.worldchatter.utils.Others.ConfigSystem;
import me.omaromar93.worldchatter.utils.Others.PAPIDependSystem;
import me.omaromar93.worldchatter.utils.Others.ThreadsSystem;
import me.omaromar93.worldchatter.utils.Others.UpdaterSystem;
import me.omaromar93.worldchatter.utils.chatting.ChatEventHandler;
import me.omaromar93.worldchatter.utils.chatting.CommandSystem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static org.bukkit.Bukkit.getConsoleSender;

public final class Main extends JavaPlugin {

    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(new ChatEventHandler(), this);
        getCommand("worldchatter").setExecutor(new CommandSystem());
        ThreadsSystem.runAsync(() -> {
            ConfigSystem.updateConfig();
            final String s = PAPIDependSystem.isPAPIThere() ? ChatColor.GREEN + "Your server does have " + ChatColor.BLUE + "'PlaceholderAPI'" + ChatColor.GREEN + " installed! " + ChatColor.GRAY + "(Unlocking the boundaries with PlaceholderAPI)" : ChatColor.RED + "Your server does not have " + ChatColor.BLUE + "'PlaceholderAPI'" + ChatColor.RED + " installed! " + ChatColor.GRAY + "(Limited to only the built-in expressions)";
            getConsoleSender().sendMessage(s);
            Boolean b = UpdaterSystem.isUpdated();
            if (b == null) {
                getConsoleSender().sendMessage(ChatColor.RED + "Error has occurred while fetching the update.");
                return;
            }
            if (b) {
                getConsoleSender().sendMessage(ChatColor.YELLOW + "WorldChatter has released a new update! " + ChatColor.GRAY + "-> " + ChatColor.GREEN + UpdaterSystem.newupdate + ChatColor.BLUE + "\nDownload the update at https://www.spigotmc.org/resources/worldchatter.101226/");
                return;
            }
            getConsoleSender().sendMessage(ChatColor.YELLOW + "WorldChatter is in it's latest update!");
        });
    }

    @Override
    public void onDisable() {
        getConsoleSender().sendMessage(ChatColor.BLUE + "Goodbye and thanks for using WorldChatter ^ - ^");
    }
}
