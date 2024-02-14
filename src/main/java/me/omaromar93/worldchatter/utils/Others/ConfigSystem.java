package me.omaromar93.worldchatter.utils.Others;

import me.omaromar93.worldchatter.Main;
import me.omaromar93.worldchatter.utils.chatting.ChattingSystem;
import me.omaromar93.worldchatter.utils.methods.AntiSwear;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;

public final class ConfigSystem {
    private static FileConfiguration config;

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void updateConfig() {
        Main.INSTANCE.getConfig().options().copyDefaults(true);
        Main.INSTANCE.saveDefaultConfig();
        Main.INSTANCE.reloadConfig();
        config = Main.INSTANCE.getConfig();
        for (final BukkitTask task : ChattingSystem.cooldowns.values()) {
            task.cancel();
        }
        ChattingSystem.cooldowns.clear();
        try {
            AntiSwear.update();
        } catch (IOException e) {
            e.printStackTrace();
            Main.INSTANCE.getServer().getConsoleSender().sendMessage(ChatColor.RED + "That didn't suppose to happen,An error has occurred while updating the profanity list.. Please contact the developer to check about it");
        }
    }
}