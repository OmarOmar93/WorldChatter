package me.omaromar93.worldchatter.utils.methods;

import me.omaromar93.worldchatter.WorldChatter;
import me.omaromar93.worldchatter.utils.API.WorldChatterAPI;
import me.omaromar93.worldchatter.utils.Others.ConfigSystem;
import me.omaromar93.worldchatter.utils.Others.ThreadsSystem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class MethodHandler {

    public static void runMethodsOnMessage(final Player player, @NotNull String message, final AsyncPlayerChatEvent event) {
        final List<String> list = detectMethods(message, player);
        if (list.contains("AntiSwear") || list.contains("AntiADS")) {
            event.setCancelled(true);
            for (final WorldChatterAPI api : WorldChatter.INSTANCE.getAPICore().getListeners())
                api.messageDetect(event, list);
            ThreadsSystem.runAsync(() -> sendToConsoleAndStaff(event, list));
            return;
        }
        if (ConfigSystem.getConfig().getBoolean("ChatFormat"))
            event.setFormat(Expression.expressIt(player, ChatColor.translateAlternateColorCodes('&', ConfigSystem.getConfig().getString("FormatString"))).replace("%", "%%") + "%2$s");
        if (ConfigSystem.getConfig().getBoolean("texts.enabled"))
            message = Expression.replaceIt(player, message);
        event.setMessage(ConfigSystem.getConfig().getBoolean("ColoredText") ? ChatColor.translateAlternateColorCodes('&', message) : message);
    }

    public static void sendToConsoleAndStaff(final AsyncPlayerChatEvent event, final List<String> list) {
        final String message = ChatColor.translateAlternateColorCodes('&', ConfigSystem.getConfig().getString("DetectedMessage")
                .replace("%nl%", "\n")
                .replace("%player%", event.getPlayer().getName())
                .replace("%flags%", String.join(", ", list))
                .replace("%message%", event.getMessage()));
        Bukkit.getConsoleSender().sendMessage(message);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("worldchatter.control")) player.sendMessage(message);
        }
        final String playermessage = ChatColor.translateAlternateColorCodes('&', ConfigSystem.getConfig().getString("DetectedPlayerMessage")
                .replace("%flags%", String.join(", ", list)));
        if (!playermessage.isEmpty())
            event.getPlayer().sendMessage(playermessage);
    }

    public static List<String> detectMethods(final String message, final Player player) {
        final ArrayList<String> detections = new ArrayList<>();
        if (ConfigSystem.getConfig().getBoolean("AntiSwear") && AntiSwear.hasSwearWords(message) && !player.hasPermission("worldchatter.bypass.antiswear"))
            detections.add("AntiSwear");
        if (ConfigSystem.getConfig().getBoolean("AntiADS") && AntiADs.hasAds(message) && !player.hasPermission("worldchatter.bypass.antiads"))
            detections.add("AntiADS");
        return detections;
    }
}