package me.omaromar93.worldchatter.utils.chatting;

import me.omaromar93.worldchatter.WorldChatter;
import me.omaromar93.worldchatter.utils.Others.ConfigSystem;
import me.omaromar93.worldchatter.utils.Others.ThreadsSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BroadCastSystem {

    static BukkitTask broadcastTasker;
    static final Random rand = new Random();

    private static ConfigurationSection worldsSection;

    public static void update() {
        if (broadcastTasker != null) broadcastTasker.cancel();
        ThreadsSystem.runAsync(() -> {
            if (ConfigSystem.getConfig().getBoolean("broadcast.enabled")) {
                worldsSection = ConfigSystem.getConfig().getConfigurationSection("broadcast.worlds");
                final List<World> worlds = new ArrayList<>();
                for (Object world : worldsSection.getKeys(false)) {
                    if (!ConfigSystem.getConfig().getList("BlackListWorlds").contains(world.toString()))
                        worlds.add(Bukkit.getWorld(world.toString()));
                }
                broadcastTasker = Bukkit.getScheduler().runTaskTimerAsynchronously(WorldChatter.INSTANCE, () -> {
                    if (rand.nextInt(100) < 80) {
                        final World world = Bukkit.getWorld(worlds.get(getrandomint(worlds.size())).getName());
                        final List<String> messages = worldsSection.getStringList(world.getName());
                        if (ConfigSystem.getConfig().getBoolean("broadcast.shufflemessages")) {
                            for (final Player player : world.getPlayers()) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.get(getrandomint(messages.size()))));
                            }
                            return;
                        }
                        final String message = messages.get(getrandomint(messages.size()));
                        for (final Player player : world.getPlayers()) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                        return;
                    }
                    final List<String> messages = ConfigSystem.getConfig().getStringList("broadcast.commonmessages");
                    if (ConfigSystem.getConfig().getBoolean("broadcast.shufflemessages")) {
                        for (final Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.get(getrandomint(messages.size()))));
                        }
                        return;
                    }
                    final String message = messages.get(getrandomint(messages.size()));
                    for (final Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }, 0L, ConfigSystem.getConfig().getInt("broadcast.messagecycle") * 20L);
            }
        });
    }

    public static int getrandomint(final int bound) {
        return bound > 1 ? rand.nextInt(bound) : 0;
    }
}