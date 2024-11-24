package WorldChatterCore.Features;

import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

public final class TextReplacer {

    public static TextReplacer INSTANCE;
    private Configuration texts;

    public TextReplacer() {
        INSTANCE = this;
    }

    public void update() {
        if (ConfigSystem.INSTANCE.getTexts().getBoolean("texts.enabled")) {
            texts = ConfigSystem.INSTANCE.getTexts().getSection("texts.messages");
            return;
        }
        texts = null;
    }

    public String formatTexts(String message, final Player player) {
        if (texts != null) {
            if (player != null) {
                for (final String key : texts.getKeys()) {
                    if ((player.hasPermission("worldchatter.admintext") && texts.getBoolean(key + ".perm")) || !texts.getBoolean(key + ".perm")) {
                        message = message.replace(
                                texts.getString(key + ".text"),
                                PlaceHolders.applyPlaceHoldersifPossible(ColorSystem.tCC(texts.getString(key + ".replace")), player));
                    }
                }

            }
        }
        return message;
    }
}