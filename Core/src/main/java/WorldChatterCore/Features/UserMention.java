package WorldChatterCore.Features;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMention {
    private String prefix;
    private String soundName;
    private float soundVolume;
    private float soundPitch;
    public static UserMention INSTANCE;
    private final Pattern HEX_COLOR_PATTERN = Pattern.compile("(?i)(&#[0-9A-F]{6})");
    private final Pattern COLOR_CODE_PATTERN = Pattern.compile("(?i)(&[0-9A-FK-OR])");

    public UserMention() {
        INSTANCE = this;
    }

    public void update() {
        prefix = ConfigSystem.INSTANCE.getPlayer().getString("UserMention.prefix");
        soundName = ConfigSystem.INSTANCE.getPlayer().getString("UserMention.sound.name");
        soundVolume = ConfigSystem.INSTANCE.getPlayer().getFloat("UserMention.sound.volume", 1);
        soundPitch = ConfigSystem.INSTANCE.getPlayer().getFloat("UserMention.sound.pitch", 1);
    }


    public String mentionUsers(String message, final Player sender) {
        for (final Player player : PlayerHandler.INSTANCE.getPlayers().values()) {
            final String lowerCaseMessage = message.toLowerCase();
            final String playerName = player.getName().toLowerCase();
            final String mention = prefix + playerName;

            if (lowerCaseMessage.contains(mention)) {

                final String colorBeforeMention = getLastColorBefore(message, mention);


                final String formattedMention = PlaceHolders.applyPlaceHoldersifPossible(ConfigSystem.INSTANCE.getPlayer().getString("UserMention.format"), player);


                message = message.replace(mention, formattedMention);

                message = appendColorToMessage(message, formattedMention, colorBeforeMention);

                if (!Objects.equals(sender.getName(), player.getName())) {
                    player.playSound(soundName, soundVolume, soundPitch);
                }
            }
        }
        return message;
    }

    private String getLastColorBefore(final String message, final String mention) {
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

    private String appendColorToMessage(final String message, final String formattedMention, final String colorCode) {
        final int mentionIndex = message.indexOf(formattedMention);
        if (mentionIndex == -1 || colorCode.isEmpty()) {
            return message;
        }

        final int endOfMention = mentionIndex + formattedMention.length();
        return message.substring(0, endOfMention) + colorCode + message.substring(endOfMention);
    }
}