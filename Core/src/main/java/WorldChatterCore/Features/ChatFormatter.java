package WorldChatterCore.Features;

import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

public class ChatFormatter {

    public static ChatFormatter INSTANCE;
    private boolean newLine, userMention, coloredText;
    private String DefaultFormat;
    private int mode;
    private Configuration formats;

    public ChatFormatter() {
        INSTANCE = this;
    }

    public void update() {
        if (ConfigSystem.INSTANCE.getChatFormatter().getBoolean("ChatFormat.enabled")) {
            // client side options
            newLine = ConfigSystem.INSTANCE.getPlayer().getBoolean("newline");
            coloredText = ConfigSystem.INSTANCE.getPlayer().getBoolean("ColoredText");
            userMention = ConfigSystem.INSTANCE.getPlayer().getBoolean("UserMention.enabled");
            // format variables
            mode = ConfigSystem.INSTANCE.getChatFormatter().getInt("ChatFormat.FormatSettings.Mode");
            DefaultFormat = ConfigSystem.INSTANCE.getChatFormatter().getString("ChatFormat.FormatSettings.DefaultFormat");
            formats = ConfigSystem.INSTANCE.getChatFormatter().getSection("ChatFormat.FormatSettings.Formats");
        }
    }

    public String formatMessage(String message, final Player player) {
        if (MiniMessageConnector.INSTANCE != null) {
            message = MiniMessageConnector.INSTANCE.cancelMiniMessage(message);
        }
        if (newLine) {
            message = message
                    .replace("\\n", "\n")
                    .replace("\\r", "\n");
        }
        if (userMention) {
            message = UserMention.INSTANCE.mentionUsers(message, player);
        }
        if (!coloredText) {
            message = ColorSystem.stripColor(message);
        }
        return TextReplacer.INSTANCE.formatTexts(message, player);
    }

    public String createFormat(final Player player) {
        final String format = getPlayerFormatIfPossible(player);
        return ColorSystem.tCC(
                PlaceHolders.applyPlaceHoldersifPossible(
                        format != null ?
                                format : DefaultFormat, player));
    }


    private String getPlayerFormatIfPossible(final Player player) {
        if (mode == 1) {
            for (String key : formats.getKeys()) {
                if (player.hasPermission(formats.getString(key + ".name"))) {
                    return formats.getString(key + ".format");
                }
            }
        }
        if (mode == 2) {
            for (String key : formats.getKeys()) {
                if (player.getName().equalsIgnoreCase(formats.getString(key + ".name"))) {
                    return formats.getString(key + ".format");
                }
            }
        }
        return null;
    }

}