package me.omaromar93.worldchatter.utils.Others;

import me.omaromar93.worldchatter.WorldChatter;
import me.omaromar93.worldchatter.utils.API.WorldChatterAPI;
import me.omaromar93.worldchatter.utils.chatting.BroadCastSystem;
import me.omaromar93.worldchatter.utils.chatting.ChattingSystem;
import me.omaromar93.worldchatter.utils.methods.AntiSwear;
import me.omaromar93.worldchatter.utils.methods.Expression;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;

public final class ConfigSystem {
    private static FileConfiguration config;

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void updateConfig(CommandSender sender) {
        WorldChatter.INSTANCE.getConfig().options().copyDefaults(true);
        WorldChatter.INSTANCE.saveDefaultConfig();
        WorldChatter.INSTANCE.reloadConfig();
        config = WorldChatter.INSTANCE.getConfig();
        Expression.update();
        BroadCastSystem.update();
        for (final BukkitTask task : ChattingSystem.cooldowns.values()) {
            task.cancel();
        }
        ChattingSystem.cooldowns.clear();
        try {
            AntiSwear.update();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "An error has occurred while updating the profanity list.. If your network is enabled then contact the developer!");
        }
        for (final WorldChatterAPI api : WorldChatter.INSTANCE.getAPICore().getListeners()) api.configReload(sender);
    }
}