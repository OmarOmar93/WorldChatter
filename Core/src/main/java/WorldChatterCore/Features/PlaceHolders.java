package WorldChatterCore.Features;

import WorldChatterCore.API.WCPlaceHolder;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Players.Player;

public final class PlaceHolders {

    public static String applyPlaceHoldersifPossible(String message, final Player player) {
        message = MainPluginConnector.INSTANCE.getWorldChatter().supporttheMessage(message
                .replace("\\n", "\r")
                .replace("\\r", "\r"), player);
        message = WCPlaceHolder.formatMessageDoNotUse(message, player);

        if (MiniMessageConnector.INSTANCE != null)
            message = MiniMessageConnector.INSTANCE.returnFormattedString(message);


        return message;
    }
}