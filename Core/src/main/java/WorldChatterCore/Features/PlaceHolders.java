package WorldChatterCore.Features;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Players.Player;

public final class PlaceHolders {

    public static String applyPlaceHoldersifPossible(String message, final Player player) {
        if (MiniMessageConnector.INSTANCE != null) {
            message = MiniMessageConnector.INSTANCE.returnFormattedString(message);
        }
        if (player != null) {
            if (MainPluginConnector.INSTANCE.getWorldChatter().isPluginEnabled("PlaceholderAPI")) {
                return MainPluginConnector.INSTANCE.getWorldChatter().supporttheMessage(message
                        .replace("\\n", "\r")
                        .replace("\\r", "\r"), player)
                        .replace("%worldchatter_player_name%", Aliases.INSTANCE.getFormattedPlayerName(player.getName()))
                        .replace("%worldchatter_player_place%", Aliases.INSTANCE.getFormattedPlace(player.getRawPlace()));
            }
            return MainPluginConnector.INSTANCE.getWorldChatter().supporttheMessage(message
                    .replace("\\n", "\r")
                    .replace("\\r", "\r")
                    .replace("%player_name%", player.getName())
                    .replace("%player_displayname%", player.getDisplayName())
                    .replace("%player_uuid%", player.getUniqueId().toString())
                    .replace("%player_place%", player.getPlace()), player)
                    .replace("%worldchatter_player_name%", Aliases.INSTANCE.getFormattedPlayerName(player.getName()))
                    .replace("%worldchatter_player_place%", Aliases.INSTANCE.getFormattedPlace(player.getRawPlace()));

        }
        return message;
    }
}