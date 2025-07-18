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
    public void onWorldChatterDisable() {
        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.BLUE + "Goodbye and thanks for using WorldChatter ^ - ^");
    }

    @Override
    public void onWorldChatterEnable() {
        WCPlaceHolder.register("player_name");
        WCPlaceHolder.register("player_place");
        WCPlaceHolder.register("player_place_raw");
        WCPlaceHolder.register("player_name_raw");
        WCPlaceHolder.register("player_displayname");
        WCPlaceHolder.register("player_ip");
        if (LuckPermsConnector.INSTANCE != null) {
            WCPlaceHolder.register("player_prefix");
            WCPlaceHolder.register("player_suffix");
        }
        if (MainPluginConnector.INSTANCE.getWorldChatter().isPluginEnabled("Multiverse-Core")) {
            WCPlaceHolder.register("player_mvworld");
        }
    }

    @Override
    public void senderConfigReload(final CommandSender sender) {
        sender.sendMessage(ColorSystem.GREEN + "Reloaded the WorldChatter's Configuration!");
    }

    @Override
    public void customPlaceholderCall(final String name, final String message , final Player player) {
        if (player != null) {
            switch (name) {
                case "player_name":
                    WCPlaceHolder.setResult(name, Aliases.INSTANCE.getFormattedPlayerName(player.getName()));
                    return;
                case "player_place":
                    WCPlaceHolder.setResult(name, Aliases.INSTANCE.getFormattedPlace(player.getRawPlace()));
                    return;
                case "player_place_raw":
                    WCPlaceHolder.setResult(name, player.getRawPlace());
                    return;
                case "player_name_raw":
                    WCPlaceHolder.setResult(name, player.getName());
                    return;
                case "player_displayname":
                    WCPlaceHolder.setResult(name, player.getDisplayName());
                    return;
                case "player_uuid":
                    WCPlaceHolder.setResult(name, player.getUniqueId().toString());
                    return;
                case "player_ip":
                    WCPlaceHolder.setResult(name, player.getIP());
                    return;
                case "player_suffix":
                    WCPlaceHolder.setResult(name, LuckPermsConnector.INSTANCE.getSuffix(Objects.requireNonNull(LuckPermsConnector.INSTANCE.getMetaData(player.getUniqueId()))));
                    return;
                case "player_prefix":
                    WCPlaceHolder.setResult(name, LuckPermsConnector.INSTANCE.getPrefix(Objects.requireNonNull(LuckPermsConnector.INSTANCE.getMetaData(player.getUniqueId()))));
                    return;
                case "player_mvworld":
                    WCPlaceHolder.setResult(name, player.getPlace());
            }
        }
    }
}