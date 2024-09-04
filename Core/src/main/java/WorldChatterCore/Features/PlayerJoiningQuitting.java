package WorldChatterCore.Features;


import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Others.ServerOptions;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.Collection;
import java.util.List;

public final class PlayerJoiningQuitting {

    public static PlayerJoiningQuitting INSTANCE;

    private boolean joinMode, quitMode;

    private Configuration joinPermissions, quitPermissions;

    private int joinLevel, quitLevel;

    private String joinPlace, quitPlace, defaultJoin, defaultQuit, greetingsMessage;

    public PlayerJoiningQuitting() {
        INSTANCE = this;
    }

    public boolean isJoinEnabled() {
        return defaultJoin != null;
    }

    public boolean isQuitEnabled() {
        return defaultQuit != null;
    }

    public void update() {
        if (ConfigSystem.INSTANCE.getPlayer().getBoolean("Greetings.enabled")) {
            greetingsMessage = ConfigSystem.INSTANCE.getPlayer().getString("Greetings.message");
        } else {
            greetingsMessage = null;
        }

        if (ConfigSystem.INSTANCE.getSystem().getBoolean("Join.enabled")) {
            joinPermissions = ConfigSystem.INSTANCE.getSystem().getSection("Join.permissions");
            joinMode = ConfigSystem.INSTANCE.getSystem().getBoolean("Join.permmode");
            joinLevel = ConfigSystem.INSTANCE.getSystem().getInt("Join.level");
            defaultJoin = ConfigSystem.INSTANCE.getSystem().getString("Join.message");
            joinPlace = ConfigSystem.INSTANCE.getSystem().getString("Join.place");
        } else {
            joinPermissions = null;
            defaultJoin = null;
            joinPlace = null;
        }

        if (ConfigSystem.INSTANCE.getSystem().getBoolean("Quit.enabled")) {
            quitPermissions = ConfigSystem.INSTANCE.getSystem().getSection("Quit.permissions");
            quitMode = ConfigSystem.INSTANCE.getSystem().getBoolean("Quit.permmode");
            quitLevel = ConfigSystem.INSTANCE.getSystem().getInt("Quit.level");
            defaultQuit = ConfigSystem.INSTANCE.getSystem().getString("Quit.message");
            quitPlace = ConfigSystem.INSTANCE.getSystem().getString("Quit.place");
        } else {
            quitPermissions = null;
            defaultQuit = null;
            quitPlace = null;
        }
    }

    private void loopType(final boolean mode, final Player joiner, final String defaultString, final Configuration perms, final Collection<Player> playerList) {


        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(formatQuick(defaultString, joiner));
        for (final Player player : playerList) {
            if (player != joiner) {
                if (!mode) {
                    player.sendMessage(formatQuick(defaultString, joiner));
                    continue;
                }
                for (final String key : perms.getKeys()) {
                    if (hasPermission(player, perms.getStringList(key + ".permissions"))) {
                        player.sendMessage(formatQuick(perms.getString(key + ".message"), joiner));
                    }
                }
            }
        }
    }


    private boolean hasPermission(final Player player, final List<String> permissions) {
        for (final String permission : permissions) {
            if (player.hasPermission(permission)) return true;
        }
        return false;
    }


    public void commitPlayerActivities(final Player joiner, final boolean type) {
        if (type && defaultJoin != null) {
            if (greetingsMessage != null) {
                joiner.sendMessage(formatQuick(greetingsMessage, joiner));
            }
            switch (joinLevel) {
                case 1:
                    loopType(joinMode, joiner, defaultJoin, joinPermissions, ServerOptions.INSTANCE.getPlayersinPlace(joinPlace));
                    return;
                case 2:
                    loopType(joinMode, joiner, defaultJoin, joinPermissions, PlayerHandler.INSTANCE.getPlayers().values());
                    return;
            }
        }
        if (defaultQuit != null) {
            switch (quitLevel) {
                case 1:
                    loopType(quitMode, joiner, defaultQuit, quitPermissions, ServerOptions.INSTANCE.getPlayersinPlace(quitPlace.replace("%place%", joiner.getPlace())));
                    return;
                case 2:
                    loopType(quitMode, joiner, defaultQuit, quitPermissions, PlayerHandler.INSTANCE.getPlayers().values());
                    break;
            }
        }
    }


    private String formatQuick(final String message, final Player player) {
        return PlaceHolders.applyPlaceHoldersifPossible(ColorSystem.tCC(message), player);
    }
}