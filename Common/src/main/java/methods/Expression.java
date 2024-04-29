package methods;

import Others.ConfigSystem;
import UniversalFunctions.CommandSender;
import UniversalFunctions.Player;
import net.md_5.bungee.api.ChatColor;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Expression {

    private static int formatMode;
    private static boolean hexCode;
    private static final Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");

    private static HashMap<String, HashMap<String, Object>> textsSection, formatSection;

    public static void update() {
        textsSection = ConfigSystem.INSTANCE.getTexts().getConfigurationSection("texts.messages");
        formatMode = ConfigSystem.INSTANCE.getFormat().getInt("FormatSettings.Mode", 2);
        formatSection = ConfigSystem.INSTANCE.getFormat().getConfigurationSection("FormatSettings.Formats");
        hexCode = ConfigSystem.INSTANCE.getConfig().getBoolean("HexColor");
    }

    public static String formatChat(final Player sender, boolean papi) {
        String format = ConfigSystem.INSTANCE.getFormat().get("DefaultFormat", "[%player_place%] %player_name%: ").toString();
        for (final String key : formatSection.keySet()) {
            if (formatMode == 1) {
                if (sender.hasPermission(formatSection.get(key).get("name").toString())) {
                    format = formatSection.get(key).get("format").toString();
                    break;
                }
                continue;
            }

            if (sender.getName().equalsIgnoreCase(formatSection.get(key).get("name").toString())) {
                format = formatSection.get(key).get("format").toString();
                break;
            }
        }
        return papi ? format : nativeExpression(sender, format);
    }

    public static String replaceIt(final CommandSender sender, String message) {
        final boolean perm = sender.hasPermission("worldchatter.admintext");

        for (final String name : textsSection.keySet()) {
            if (!perm && Boolean.parseBoolean(textsSection.get(name).getOrDefault("perm", "false").toString())) {
                continue;
            }
            message = message.replace(textsSection.get(name).getOrDefault("text", "").toString(), textsSection.get(name).getOrDefault("replace", "").toString());
        }
        return message;
    }


    public static String translateColors(String message) {
        if (hexCode) {
            Matcher matcher = pattern.matcher(message);
            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                try {
                    message = message.replace(color, ChatColor.of(color.replace("&", "")) + "");
                } catch (final NoSuchMethodError e) {
                    return ChatColor.translateAlternateColorCodes('&', message);
                }
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String nativeExpression(final Player player, final String message) {
        return message
                .replace("%player_name%", player.getName())
                .replace("%player_place%", player.getPlace())
                .replace("%player_displayname%", player.getDisplayName());
    }

}