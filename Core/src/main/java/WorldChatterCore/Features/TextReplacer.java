package WorldChatterCore.Features;

import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.ConfigSystem;

public class TextReplacer {

    public static TextReplacer INSTANCE;
    private Configuration texts;
    private boolean textReplacer;

    public TextReplacer() {
        INSTANCE = this;
    }

    public void update() {
        textReplacer = ConfigSystem.INSTANCE.getTexts().getBoolean("texts.enabled");
        if (textReplacer) {
            texts = ConfigSystem.INSTANCE.getTexts().getSection("texts.messages");
        }
    }

    public String formatTexts(String message, final Player player) {
        if (textReplacer) {
            for (String key : texts.getKeys()) {
                if ((player.hasPermission("worldchatter.admintext") && texts.getBoolean(key + ".perm")) || !texts.getBoolean(key + ".perm")) {
                    message = message.replace(
                            texts.getString(key + ".text"),
                            PlaceHolders.applyPlaceHoldersifPossible(texts.getString(key + ".replace"), player));
                }
            }
        }
        return message;
    }
}