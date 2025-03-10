package WorldChatterCore.Features;

import WorldChatterCore.API.WCPlaceHolder;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Players.Player;

public final class PlaceHolders {

    public static String applyPlaceHoldersifPossible(String message, final Player player) {
        if (player != null) {
            if (LuckPermsConnector.INSTANCE != null) {
                message = LuckPermsConnector.INSTANCE.formatMessage(player.getUniqueId(), message);
            }

            message = MainPluginConnector.INSTANCE.getWorldChatter().supporttheMessage(message
                    .replace("{player_name}", Aliases.INSTANCE.getFormattedPlayerName(player.getName()))
                    .replace("{player_place}", Aliases.INSTANCE.getFormattedPlace(player.getRawPlace()))
                    .replace("{player_place_raw}", player.getRawPlace())
                    .replace("{player_name_raw}", player.getName())
                    .replace("{player_displayname}", player.getDisplayName())
                    .replace("{player_uuid}", player.getUniqueId().toString())
                    .replace("\\n", "\r")
                    .replace("\\r", "\r"), player);
        }
        message = WCPlaceHolder.formatMessage(message,player);

        if (MiniMessageConnector.INSTANCE != null) {
            message = MiniMessageConnector.INSTANCE.returnFormattedString(message);
        }

        return message;
    }
}