package methods;

import Others.ConfigSystem;
import Others.PlayerSystem;
import UniversalFunctions.Player;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMention {

    private static String prefix;
    private static String soundName;
    private static float soundVolume;
    private static float soundPitch;// Prefix for hex color codes
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("(?i)(&#[0-9A-F]{6})");
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("(?i)(&[0-9A-FK-OR])");

    public static void update() {
        prefix = Objects.requireNonNull(ConfigSystem.INSTANCE.getFormat().get("UserMention.prefix")).toString();
        soundName = Objects.requireNonNull(ConfigSystem.INSTANCE.getFormat().get("UserMention.sound.name")).toString();
        soundVolume = ConfigSystem.INSTANCE.getFormat().getFloat("UserMention.sound.volume", 1);
        soundPitch = ConfigSystem.INSTANCE.getFormat().getFloat("UserMention.sound.pitch", 1);
    }


    public static String mentionUsers(String message, final Player sender) {
        for (final Player player : PlayerSystem.INSTANCE.getPlayers()) {
            final String lowerCaseMessage = message.toLowerCase();
            final String playerName = player.getName().toLowerCase();
            final String mention = prefix + playerName;

            if (lowerCaseMessage.contains(mention)) {

                final String colorBeforeMention = getLastColorBefore(message, mention);


                final String formattedMention = Objects.requireNonNull(ConfigSystem.INSTANCE.getFormat().get("UserMention.format")).toString()
                        .replace("%player_name%", player.getName());


                message = message.replace(mention, formattedMention);

                message = appendColorToMessage(message, formattedMention, colorBeforeMention);

                if (!Objects.equals(sender.getName(), player.getName())) {
                    player.playSound(soundName, soundVolume, soundPitch);
                }
            }
        }
        return message;
    }

    private static String getLastColorBefore(final String message, final String mention) {
        final int mentionIndex = message.toLowerCase().indexOf(mention);
        if (mentionIndex == -1) {
            return "";
        }

        final String substring = message.substring(0, mentionIndex);
        final Matcher hexMatcher = HEX_COLOR_PATTERN.matcher(substring);
        final Matcher colorCodeMatcher = COLOR_CODE_PATTERN.matcher(substring);

        String lastHexColor = null;
        String lastColorCode = null;

        while (hexMatcher.find()) {
            lastHexColor = hexMatcher.group();
        }

        while (colorCodeMatcher.find()) {
            lastColorCode = colorCodeMatcher.group();
        }

        return lastHexColor != null ? lastHexColor : (lastColorCode != null ? lastColorCode : "");
    }

    private static String appendColorToMessage(final String message, final String formattedMention, final String colorCode) {
        final int mentionIndex = message.indexOf(formattedMention);
        if (mentionIndex == -1 || colorCode.isEmpty()) {
            return message;
        }

        final int endOfMention = mentionIndex + formattedMention.length();
        return message.substring(0, endOfMention) + colorCode + message.substring(endOfMention);
    }
}