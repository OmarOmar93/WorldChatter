package me.omaromar93.worldchatter.utils.methods;

import me.clip.placeholderapi.PlaceholderAPI;
import me.omaromar93.worldchatter.utils.Others.PAPIDependSystem;
import org.bukkit.entity.Player;

public final class Expression {

    public static String expressIt(final Player player, final String message) {
        return PAPIDependSystem.placeholderapisuppport ? PlaceholderAPI.setPlaceholders(player, message) : nativeExpression(player, message);
    }

    public static String nativeExpression(final Player player, final String message) {
        return message
                .replace("%player_name%", player.getName())
                .replace("%player_world%", player.getWorld().getName())
                .replace("%player_displayname%", player.getDisplayName());
    }
}