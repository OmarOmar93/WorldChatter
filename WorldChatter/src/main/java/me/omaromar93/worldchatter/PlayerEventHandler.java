package me.omaromar93.worldchatter;

import Others.ConfigSystem;
import Others.PlayerSystem;
import UniversalFunctions.Player;
import me.clip.placeholderapi.PlaceholderAPI;
import me.omaromar93.worldchatter.PAPI.PAPIDependSystem;
import me.omaromar93.worldchatter.functions.SpigotPlayer;
import methods.Expression;
import methods.MoreFormat;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlayerEventHandler implements Listener {

    private static HashMap<String, HashMap<String, Object>> joinSection, quitSection;

    private static boolean joinMode, quitMode = false;

    public PlayerEventHandler() {
        update();
    }

    public static void update() {
        joinMode = ConfigSystem.INSTANCE.getMessages().getBoolean("Join.permmode");
        quitMode = ConfigSystem.INSTANCE.getMessages().getBoolean("Quit.permmode");
        if (joinMode) {
            joinSection = ConfigSystem.INSTANCE.getMessages().getConfigurationSection("Join.permissions");
        }
        if (quitMode) {
            quitSection = ConfigSystem.INSTANCE.getMessages().getConfigurationSection("Quit.permissions");
        }
    }


    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = new SpigotPlayer(event.getPlayer());
        PlayerSystem.INSTANCE.addPlayer(player.getUUID(), player);
        if (ConfigSystem.INSTANCE.getMessages().getBoolean("CustomJoinQuit")) {
            final TextComponent messagedefault = MoreFormat.FormatMore(getDefaultMessage(event.getPlayer(), false));
            event.setJoinMessage(null);
            for (final org.bukkit.entity.Player p : Bukkit.getOnlinePlayers()) {
                if (p != event.getPlayer()) {
                    if (joinMode) {
                        for (final HashMap<String, Object> map : joinSection.values()) {
                            if (hasPermission(p, (List<String>) map.get("permissions"))) {
                                p.spigot().sendMessage(MoreFormat.FormatMore(PAPIDependSystem.INSTANCE.isPAPIThere() ? PlaceholderAPI.setPlaceholders(event.getPlayer(), Expression.translateColors(map.get("message").toString())) : Expression.translateColors(map.get("message").toString())
                                        .replace("%player%", event.getPlayer().getName())));
                                continue;
                            }
                            p.spigot().sendMessage(messagedefault);
                        }
                        continue;
                    }
                    p.spigot().sendMessage(messagedefault);
                }
            }
            Bukkit.getConsoleSender().spigot().sendMessage(messagedefault);
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        PlayerSystem.INSTANCE.removePlayer(event.getPlayer().getUniqueId());
        if (ConfigSystem.INSTANCE.getMessages().getBoolean("CustomJoinQuit")) {
            final TextComponent messagedefault = MoreFormat.FormatMore(getDefaultMessage(event.getPlayer(), true));
            event.setQuitMessage(null);
            for (final org.bukkit.entity.Player p : Bukkit.getOnlinePlayers()) {
                if (p != event.getPlayer()) {
                    if (quitMode) {
                        for (final HashMap<String, Object> map : quitSection.values()) {
                            if (hasPermission(p, (List<String>) map.get("permissions"))) {
                                p.spigot().sendMessage(MoreFormat.FormatMore(PAPIDependSystem.INSTANCE.isPAPIThere() ? PlaceholderAPI.setPlaceholders(event.getPlayer(), Expression.translateColors(map.get("message").toString())) : Expression.translateColors(map.get("message").toString())
                                        .replace("%player%", event.getPlayer().getName())));
                                continue;
                            }
                            p.spigot().sendMessage(messagedefault);
                        }
                        continue;
                    }
                    p.spigot().sendMessage(messagedefault);
                }
            }
            Bukkit.getConsoleSender().spigot().sendMessage(messagedefault);
        }
    }

    public String getDefaultMessage(org.bukkit.entity.Player player, boolean quit) {
        return PAPIDependSystem.INSTANCE.isPAPIThere() ?
                PlaceholderAPI.setPlaceholders(player, Expression.translateColors(
                        quit ? Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Quit.message")).toString()
                                :
                                Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Join.message")).toString()
                                        .replace("%player%", player.getName()))) :
                quit ? Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Quit.message")).toString()
                        .replace("%player%", player.getName()) :
                        Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Join.message")).toString()
                                .replace("%player%", player.getName());
    }

    private boolean hasPermission(final org.bukkit.entity.Player player, final List<String> permissions) {
        for (final String permission : permissions) {
            if (player.hasPermission(permission)) return true;
        }
        return false;
    }
}