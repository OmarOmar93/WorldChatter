package me.omaromar93.worldchatter;

import Others.ConfigSystem;
import Others.PlayerSystem;
import UniversalFunctions.Player;
import UniversalFunctions.PlayerEventConnector;
import UniversalFunctions.PlayerEventInterface;
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

public class PlayerEventHandler implements Listener, PlayerEventInterface {

    private static HashMap<String, HashMap<String, Object>> joinSection, quitSection;

    private static boolean joinMode, quitMode = false;

    private static boolean greetingmode = true;

    private static String greetingmessage;

    public PlayerEventHandler() {
        PlayerEventConnector.INSTANCE.setPlayerSystem(this);
        update();
    }

    public static void update() {
        greetingmode = ConfigSystem.INSTANCE.getMessages().getBoolean("Greetings.enabled");
        greetingmessage = Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Greetings.message")).toString();
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
            final String messagedefault = getDefaultMessage(event.getPlayer(), false);
            event.setJoinMessage(null);
            for (final Player p : PlayerSystem.INSTANCE.getPlayers()) {
                if (p != player) {
                    if (joinMode) {
                        for (final HashMap<String, Object> map : joinSection.values()) {
                            if (hasPermission(p, (List<String>) map.get("permissions"))) {
                                p.sendMessage(Expression.translateColors(PAPIDependSystem.INSTANCE.isPAPIThere() ? PlaceholderAPI.setPlaceholders(event.getPlayer(), Expression.translateColors(map.get("message").toString())) : Expression.translateColors(map.get("message").toString())
                                        .replace("%player_name%", event.getPlayer().getName())));
                                continue;
                            }
                            p.sendMessage(messagedefault);
                        }
                        continue;
                    }
                    p.sendMessage(messagedefault);
                }
            }
            try {
                Bukkit.getConsoleSender().spigot().sendMessage(MoreFormat.FormatMore(messagedefault));
            } catch (final NoSuchMethodError ignored) {
                assert messagedefault != null;
                Bukkit.getConsoleSender().sendMessage(Expression.translateColors(messagedefault));
            }
        }
        if (greetingmode) {
            player.sendMessage(
                    Expression.translateColors(PAPIDependSystem.INSTANCE.isPAPIThere() ?
                            PlaceholderAPI.setPlaceholders(event.getPlayer(), greetingmessage) :
                            greetingmessage
                                    .replace("%player_name%", event.getPlayer().getName())
                                    .replace("%player_displayname%", event.getPlayer().getDisplayName())
                    ));
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        PlayerSystem.INSTANCE.removePlayer(event.getPlayer().getUniqueId());
        if (ConfigSystem.INSTANCE.getMessages().getBoolean("CustomJoinQuit")) {
            final String messagedefault = getDefaultMessage(event.getPlayer(), true);
            event.setQuitMessage(null);
            for (final Player p : PlayerSystem.INSTANCE.getPlayers()) {
                if (quitMode) {
                    for (final HashMap<String, Object> map : quitSection.values()) {
                        if (hasPermission(p, (List<String>) map.get("permissions"))) {
                            p.sendMessage(Expression.translateColors(PAPIDependSystem.INSTANCE.isPAPIThere() ? PlaceholderAPI.setPlaceholders(event.getPlayer(), Expression.translateColors(map.get("message").toString())) : Expression.translateColors(map.get("message").toString())
                                    .replace("%player_name%", event.getPlayer().getName())));
                            continue;
                        }
                        p.sendMessage(messagedefault);
                    }
                    continue;
                }
                p.sendMessage(messagedefault);
            }
            try {
                Bukkit.getConsoleSender().spigot().sendMessage(MoreFormat.FormatMore(messagedefault));
            } catch (final NoSuchMethodError ignored) {
                assert messagedefault != null;
                Bukkit.getConsoleSender().sendMessage(Expression.translateColors(messagedefault));
            }
        }
    }

    public String getDefaultMessage(org.bukkit.entity.Player player, boolean quit) {
        return Expression.translateColors(PAPIDependSystem.INSTANCE.isPAPIThere() ?
                PlaceholderAPI.setPlaceholders(player,
                        quit ? Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Quit.message")).toString()
                                :
                                Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Join.message")).toString()
                                        .replace("%player_name%", player.getName())) :
                quit ? Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Quit.message")).toString()
                        .replace("%player_name%", player.getName()) :
                        Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Join.message")).toString()
                                .replace("%player_name%", player.getName()));
    }

    private boolean hasPermission(final Player player, final List<String> permissions) {
        for (final String permission : permissions) {
            if (player.hasPermission(permission)) return true;
        }
        return false;
    }


    @Override
    public void reloadPlayerEvent() {
        update();
    }
}