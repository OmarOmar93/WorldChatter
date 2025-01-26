package WorldChatterCore.Connectors.Interfaces;

import WorldChatterCore.Players.Player;

public interface PlayerChatEvent {
    /**
     * This returns WorldChatter's Registered Player
     * @return {@link Player}
     */
    Player getPlayer();

    /**
     * Gets the message of the event
     * @return {@link String}
     */
    String getMessage();

}