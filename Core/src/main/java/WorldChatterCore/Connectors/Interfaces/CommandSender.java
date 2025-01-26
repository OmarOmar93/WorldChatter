package WorldChatterCore.Connectors.Interfaces;

import WorldChatterCore.Players.Player;

/**
 * This represents a custom Command Sender Interface for WorldChatter
 */
public interface CommandSender {

    /**
     * This checks if the sender is a player or a server.
     * @return if it's player or not
     */
    boolean isPlayer();

    /**
     * Send message to the command sender
     * @param message message to send
     */
    void sendMessage(final String message);

    /**
     * This checks if the sender has a specific permission
     * @param permission registered permission
     * @return if the sender has the permission or not
     */
    boolean hasPermission(final String permission);

    /**
     * Returns the sender's name
     * @return a {@link String} of sender's name
     */
    String getName();

    /**
     * Returns the player instance to execute player related functions
     * @return returns the {@link Player}
     */
    Player getPlayer();
}
