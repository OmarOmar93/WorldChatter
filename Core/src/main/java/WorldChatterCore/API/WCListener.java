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
    default void messageDetect(final List<String> flags, final Player player, final String message) {}

    /**
     *
     * @param name Placeholder's Name
     * @param message The Player's Message
     * @param player The Player
     */
    default void customPlaceholderCall(final String name, final String message, final Player player){}

    /**
     *
     * @param sender The Command Sender
     */
    default void chatLockToggle(final CommandSender sender){}

    default void onWorldChatterEnable(){}

    default void onWorldChatterDisable(){}

    /**
     *
     * @param sender The Command Sender
     */
    default void updateChecked(final CommandSender sender){}

    /**
     *
     * @param sender The Command Sender
     */
    default void senderConfigReload(final CommandSender sender){}


    /**
     *
     * @param featureSystem Used to approve the message and perform the important checks!
     * @param sender The Command Sender
     */
    default void onMessage(final FeatureSystem featureSystem, final CommandSender sender){}


}
