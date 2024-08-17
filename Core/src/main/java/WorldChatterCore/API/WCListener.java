package WorldChatterCore.API;

import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Players.Player;

import java.util.List;

public interface WCListener {


    /**
     *
     * @param flags Detected Flags from the plugin
     * @param player Detected Player who caused these flags
     * @param message Detected Message
     */
    void messageDetect(List<String> flags, final Player player, String message);


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

}