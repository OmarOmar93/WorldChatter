package WorldChatterCore.Features;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

public final class UserMention {
    private String prefix;
    private String soundName;
    private float soundVolume;
    private float soundPitch;
    public static UserMention INSTANCE;

    public UserMention() {
        INSTANCE = this;
    }

    public void update() {
        if (ConfigSystem.INSTANCE.getPlayer().getBoolean("UserMention.enabled")) {
            prefix = ConfigSystem.INSTANCE.getPlayer().getString("UserMention.prefix");
            soundName = ConfigSystem.INSTANCE.getPlayer().getString("UserMention.sound.name");
            soundVolume = ConfigSystem.INSTANCE.getPlayer().getFloat("UserMention.sound.volume", 1);
            soundPitch = ConfigSystem.INSTANCE.getPlayer().getFloat("UserMention.sound.pitch", 1);
            return;
        }
        prefix = null;
        soundName = null;
    }


    public String mentionUsers(String message, final Player sender) {
        for (final Player player : PlayerHandler.INSTANCE.getPlayers().values()) {
            final String playerName = player.getName(),
                    regex = prefix + playerName;

            int mentionIndex = message.toLowerCase().indexOf(prefix + playerName.toLowerCase());
            while (mentionIndex != -1) {
                String colorBeforeMention = ColorSystem.getLastColors(ColorSystem.tCC(message.substring(0, mentionIndex)));
                if (colorBeforeMention.isEmpty()) {
                    colorBeforeMention = ColorSystem.RESET.toString();
                }

                if (sender != player) {
                    player.playSound(soundName, soundVolume, soundPitch);
                }

                final String replacement = PlaceHolders.applyPlaceHoldersifPossible(
                        ConfigSystem.INSTANCE.getPlayer().getString("UserMention.format"), player) + colorBeforeMention;
                message = message.substring(0, mentionIndex) + replacement + message.substring(mentionIndex + regex.length());

                mentionIndex = message.toLowerCase().indexOf(prefix + playerName.toLowerCase(), mentionIndex + replacement.length());
            }
        }
        return message;
    }
}