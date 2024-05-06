package me.omaromar93.worldchatter.Legacy;

import Others.ConfigSystem;
import Others.PlayerSystem;
import Others.ThreadsSystem;
import chatting.BroadcastSystemConnector;
import chatting.BroadcastSystemInterface;
import me.omaromar93.worldchatter.WorldChatter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LegacyBroadcastSystem implements BroadcastSystemInterface {

    private BukkitTask broadcastTasker;
    private final Random rand = new Random();
    private HashMap<String, HashMap<String, Object>> worldsSection;

    public LegacyBroadcastSystem() {
        BroadcastSystemConnector.INSTANCE.setBroadcastSystem(this);
    }

    @Override
    public void update() {
        if (broadcastTasker != null) broadcastTasker.cancel();
        ThreadsSystem.runAsync(() -> {
            if (ConfigSystem.INSTANCE.getBroadcast().getBoolean("broadcast.enabled")) {
                worldsSection = ConfigSystem.INSTANCE.getBroadcast().getConfigurationSection("broadcast.places");
                final List<World> worlds = new ArrayList<>();
                final List<String> list = ConfigSystem.INSTANCE.getConfig().getStringList("BlackListPlaces");
                for (final Object world : worldsSection.keySet()) {
                    if (!list.contains(world.toString()))
                        worlds.add(Bukkit.getWorld(world.toString()));
                }
                broadcastTasker = Bukkit.getScheduler().runTaskTimerAsynchronously(WorldChatter.INSTANCE, () -> {
                    if (rand.nextInt(100) < 80) {
                        final World world = Bukkit.getWorld(worlds.get(getrandomint(worlds.size())).getName());
                        final List<String> messages = (List<String>) worldsSection.get(world.getName());
                        if (ConfigSystem.INSTANCE.getBroadcast().getBoolean("broadcast.shufflemessages")) {
                            for (final Player player : world.getPlayers()) {
                                PlayerSystem.INSTANCE.getPlayer(player.getUniqueId()).sendMessage(ChatColor.translateAlternateColorCodes('&',messages.get(getrandomint(messages.size()))));
                            }
                            return;
                        }
                        final String message = messages.get(getrandomint(messages.size()));
                        for (final Player player : world.getPlayers()) {
                            PlayerSystem.INSTANCE.getPlayer(player.getUniqueId()).sendMessage(ChatColor.translateAlternateColorCodes('&',message));
                        }
                        return;
                    }
                    final List<String> messages = ConfigSystem.INSTANCE.getBroadcast().getStringList("broadcast.commonmessages");
                    if (ConfigSystem.INSTANCE.getBroadcast().getBoolean("broadcast.shufflemessages")) {
                        for (final Player player : Bukkit.getOnlinePlayers()) {
                            PlayerSystem.INSTANCE.getPlayer(player.getUniqueId()).sendMessage(ChatColor.translateAlternateColorCodes('&',messages.get(getrandomint(messages.size()))));
                        }
                        return;
                    }
                    final String message = ChatColor.translateAlternateColorCodes('&',messages.get(getrandomint(messages.size())));
                    for (final Player player : Bukkit.getOnlinePlayers()) {
                        PlayerSystem.INSTANCE.getPlayer(player.getUniqueId()).sendMessage(message);
                    }
                }, 0L, ConfigSystem.INSTANCE.getBroadcast().getInt("broadcast.messagecycle") * 20L);
            }
        });
    }

    private int getrandomint(final int bound) {
        return bound > 1 ? rand.nextInt(bound) : 0;
    }
}
