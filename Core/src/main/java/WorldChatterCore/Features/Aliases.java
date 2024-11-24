package WorldChatterCore.Features;

import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Systems.ConfigSystem;

public class Aliases {

    public static Aliases INSTANCE;
    private Configuration serverAli, playerAli;

    public Aliases() {
        INSTANCE = this;
    }

    public void update() {
        if (ConfigSystem.INSTANCE.getTexts().getBoolean("aliases.enabled")) {
            serverAli = ConfigSystem.INSTANCE.getTexts().getSection("aliases.place");
            playerAli = ConfigSystem.INSTANCE.getTexts().getSection("aliases.player");
            return;
        }
        serverAli = null;
        playerAli = null;
    }

    public String getFormattedPlace(final String place) {
        return serverAli != null ? serverAli.getString(place, place) : place;
    }

    public String getFormattedPlayerName(final String playerName) {
        return playerAli != null ? playerAli.getString(playerName, playerName) : playerName;
    }
}