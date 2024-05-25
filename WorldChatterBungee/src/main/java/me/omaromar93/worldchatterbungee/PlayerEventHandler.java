package me.omaromar93.worldchatterbungee;

import Others.ConfigSystem;
import Others.PlayerSystem;
import UniversalFunctions.Player;
import functions.BungeePlayer;
import methods.Expression;
import methods.MoreFormat;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlayerEventHandler implements Listener {

    private static HashMap<String, HashMap<String, Object>> joinSection, quitSection;

    private static boolean joinMode, quitMode = false;

    private static boolean greetingmode = true;

    private static String greetingmessage;

    public PlayerEventHandler() {
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
    public void onJoin(PostLoginEvent event) {
        final Player player = new BungeePlayer(event.getPlayer());
        PlayerSystem.INSTANCE.addPlayer(player.getUUID(), player);
        if (ConfigSystem.INSTANCE.getMessages().getBoolean("CustomJoinQuit")) {
            final TextComponent messagedefault = MoreFormat.FormatMore(getDefaultMessage(event.getPlayer(), false));
            for (final ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p != event.getPlayer()) {
                    if (joinMode) {
                        for (final HashMap<String, Object> map : joinSection.values()) {
                            if (hasPermission(p, (List<String>) map.get("permissions"))) {
                                p.sendMessage(MoreFormat.FormatMore(Expression.translateColors(map.get("message").toString())
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
        }
        if (greetingmode) {
            event.getPlayer().sendMessage(MoreFormat.FormatMore(
                    Expression.translateColors(greetingmessage
                            .replace("%player_name%", event.getPlayer().getName())
                            .replace("%player_displayname%", event.getPlayer().getDisplayName())
                    )));
        }
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        if (ConfigSystem.INSTANCE.getMessages().getBoolean("CustomJoinQuit")) {
            final TextComponent messagedefault = MoreFormat.FormatMore(getDefaultMessage(event.getPlayer(), true));
            for (final ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if (p != event.getPlayer()) {
                    if (quitMode) {
                        for (final HashMap<String, Object> map : quitSection.values()) {
                            if (hasPermission(p, (List<String>) map.get("permissions"))) {
                                p.sendMessage(MoreFormat.FormatMore(map.get("message").toString()));
                                continue;
                            }
                            p.sendMessage(messagedefault);
                        }
                        continue;
                    }
                    p.sendMessage(messagedefault);
                }
            }
        }
        PlayerSystem.INSTANCE.removePlayer(event.getPlayer().getUniqueId());
    }

    public String getDefaultMessage(final ProxiedPlayer player, final boolean quit) {
        return Expression.translateColors(quit ? Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Quit.message")).toString()
                .replace("%player_name%", player.getName()) :
                Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("Join.message")).toString()
                        .replace("%player_name%", player.getName()));
    }

    private boolean hasPermission(final ProxiedPlayer player, final List<String> permissions) {
        for (final String permission : permissions) {
            if (player.hasPermission(permission)) return true;
        }
        return false;
    }
}