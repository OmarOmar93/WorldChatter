package WorldChatterCore.API;

import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.FeatureSystem;

import java.util.List;

public interface WCListener {


    /**
     *
     * @param flags Detected Flags from the plugin
     * @param player Detected Player who caused these flags
     * @param message Detected Message
     */
    void messageDetect(final List<String> flags, final Player player, final String message);

    /**
     *
     * @param name Placeholder's Name
     * @param message The Player's Message
     * @param player The Player
     */
    void customPlaceholderCall(final String name, final String message, final Player player);

    /**
     *
     * @param sender The Command Sender
     */
    void chatLockToggle(final CommandSender sender);

    /**
     *
     * @param sender The Command Sender
     */
    void updateChecked(final CommandSender sender);

    /**
     *
     * @param sender The Command Sender
     */
    void senderConfigReload(final CommandSender sender);


    /**
     *
     * @param featureSystem Used to approve the message and perform the important checks!
     * @param sender The Command Sender
     * @param message The Sender's Message
     */
    void onMessage(final FeatureSystem featureSystem, final CommandSender sender, final String message);


}
