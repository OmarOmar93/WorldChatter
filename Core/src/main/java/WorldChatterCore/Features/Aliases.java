package WorldChatterCore.Features;

import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Systems.ConfigSystem;

public final class Aliases {

    public static Aliases INSTANCE;
    private Configuration serverAli, playerAli;

    public Aliases() {
        INSTANCE = this;
    }

    /**
     * This is executed by ConfigSystem's reload function
     */
    public void update() {
        if (ConfigSystem.INSTANCE.getTexts().getBoolean("aliases.enabled")) {
            serverAli = ConfigSystem.INSTANCE.getTexts().getSection("aliases.place");
            playerAli = ConfigSystem.INSTANCE.getTexts().getSection("aliases.player");
            return;
        }
        serverAli = null;
        playerAli = null;
    }

    /**
     * Returns the place formatted in the config texts.yml file
     * @param place the raw place name
     * @return the formatted place
     */
    public String getFormattedPlace(final String place) {
        return serverAli != null ? serverAli.getString(place, place) : place;
    }

    /**
     * Returns the player formatted in the config texts.yml file
     * @param playerName the raw player name
     * @return the formatted player
     */
    public String getFormattedPlayerName(final String playerName) {
        return playerAli != null ? playerAli.getString(playerName, playerName) : playerName;
    }
}