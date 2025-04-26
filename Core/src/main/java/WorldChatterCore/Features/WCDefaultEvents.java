package WorldChatterCore.Features;

import WorldChatterCore.API.WCListener;
import WorldChatterCore.API.WCPlaceHolder;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.ColorSystem;

import java.util.Objects;


public final class WCDefaultEvents implements WCListener {

    @Override
    public void senderConfigReload(CommandSender sender) {
        sender.sendMessage(ColorSystem.GREEN + "Reloaded the WorldChatter's Configuration!");
    }

    @Override
    public void customPlaceholderCall(final String name, final String message, final Player player) {
        if (player != null) {
            switch (name) {
                case "player_name":
                    WCPlaceHolder.getPlaceholders().put("player_name", Aliases.INSTANCE.getFormattedPlayerName(player.getName()));
                    return;
                case "player_place":
                    WCPlaceHolder.getPlaceholders().put("player_place", Aliases.INSTANCE.getFormattedPlace(player.getRawPlace()));
                    return;
                case "player_place_raw":
                    WCPlaceHolder.getPlaceholders().put("player_place_raw", player.getRawPlace());
                    return;
                case "player_name_raw":
                    WCPlaceHolder.getPlaceholders().put("player_name_raw", player.getName());
                    return;
                case "player_displayname":
                    WCPlaceHolder.getPlaceholders().put("player_displayname", player.getDisplayName());
                    return;
                case "player_uuid":
                    WCPlaceHolder.getPlaceholders().put("player_uuid", player.getUniqueId().toString());
                    return;
            }
            if (LuckPermsConnector.INSTANCE != null) {
                switch (name) {
                    case "player_suffix":
                        WCPlaceHolder.getPlaceholders().put("player_suffix", LuckPermsConnector.INSTANCE.getSuffix(Objects.requireNonNull(LuckPermsConnector.INSTANCE.getMetaData(player.getUniqueId()))));
                        return;
                    case "player_prefix":
                        WCPlaceHolder.getPlaceholders().put("player_prefix", LuckPermsConnector.INSTANCE.getPrefix(Objects.requireNonNull(LuckPermsConnector.INSTANCE.getMetaData(player.getUniqueId()))));
                        return;
                }
            }
            if (MainPluginConnector.INSTANCE.getWorldChatter().isPluginEnabled("Multiverse-Core") && name.equalsIgnoreCase("player_mvworld")) {
                WCPlaceHolder.getPlaceholders().put("player_mvworld", player.getPlace());
            }
        }
    }
}