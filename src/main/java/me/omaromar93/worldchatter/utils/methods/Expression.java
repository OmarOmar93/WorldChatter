package me.omaromar93.worldchatter.utils.methods;

import me.clip.placeholderapi.PlaceholderAPI;
import me.omaromar93.worldchatter.utils.Others.ConfigSystem;
import me.omaromar93.worldchatter.utils.Others.PAPIDependSystem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public final class Expression {

    private static ConfigurationSection textsSection;

    public static void update() {
        textsSection = ConfigSystem.getConfig().getConfigurationSection("texts.messages");
    }

    public static String expressIt(final Player player, final String format) {
        return PAPIDependSystem.placeholderapisuppport ? PlaceholderAPI.setPlaceholders(player, format) : nativeExpression(player, format);
    }

    public static String replaceIt(final Player player, String message) {
        final boolean perm = player.hasPermission("worldchatter.admintext");
        for (final String name : textsSection.getKeys(false)) {
            if (textsSection.getBoolean(name + ".perm") && !perm) {
                continue;
            }
            message = message.replace(textsSection.getString(name + ".text"), textsSection.getString(name + ".replace"));
        }
        return message;
    }

    public static String nativeExpression(final Player player, final String message) {
        return message
                .replace("%player_name%", player.getName())
                .replace("%player_world%", player.getWorld().getName())
                .replace("%player_displayname%", player.getDisplayName());
    }
}