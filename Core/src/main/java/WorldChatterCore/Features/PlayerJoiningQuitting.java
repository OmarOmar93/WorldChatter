package WorldChatterCore.Features;


import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.Collection;
import java.util.List;

public class PlayerJoiningQuitting {

    public static PlayerJoiningQuitting INSTANCE;

    private boolean joinMode, quitMode, joinEnabled, quitEnabled, greetingsEnabled;

    private Configuration joinPermissions, quitPermissions;

    private int joinLevel, quitLevel;

    private String joinPlace, quitPlace, defaultJoin, defaultQuit, greetingsMessage;

    public PlayerJoiningQuitting() {
        INSTANCE = this;
    }

    public boolean isJoinEnabled() {
        return joinEnabled;
    }

    public boolean isQuitEnabled() {
        return quitEnabled;
    }

    public void update() {
        joinEnabled = ConfigSystem.INSTANCE.getSystem().getBoolean("Join.enabled");
        greetingsEnabled = ConfigSystem.INSTANCE.getPlayer().getBoolean("Greetings.enabled");
        quitEnabled = ConfigSystem.INSTANCE.getSystem().getBoolean("Quit.enabled");
        if (greetingsEnabled) {
            greetingsMessage = ConfigSystem.INSTANCE.getPlayer().getString("Greetings.message");
        }
        if (joinEnabled) {
            joinPermissions = ConfigSystem.INSTANCE.getSystem().getSection("Join.permissions");
            joinMode = ConfigSystem.INSTANCE.getSystem().getBoolean("Join.permmode");
            joinLevel = ConfigSystem.INSTANCE.getSystem().getInt("Join.level");
            defaultJoin = ConfigSystem.INSTANCE.getSystem().getString("Join.message");
            joinPlace = ConfigSystem.INSTANCE.getSystem().getString("Join.place");
        }

        if (quitEnabled) {
            quitPermissions = ConfigSystem.INSTANCE.getSystem().getSection("Quit.permissions");
            quitMode = ConfigSystem.INSTANCE.getSystem().getBoolean("Quit.permmode");
            quitLevel = ConfigSystem.INSTANCE.getSystem().getInt("Quit.level");
            defaultQuit = ConfigSystem.INSTANCE.getSystem().getString("Quit.message");
            quitPlace = ConfigSystem.INSTANCE.getSystem().getString("Join.place");
        }
    }

    private void loopType(final boolean mode, final Player joiner, final String defaultString, final Configuration perms, final Collection<Player> playerList) {
        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(formatQuick(defaultString, joiner));
        for (Player player : playerList) {
            if (!mode) {
                if(player != joiner) player.sendMessage(formatQuick(defaultString, joiner));
            } else {
                for (String key : perms.getKeys()) {
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


    public void commitPlayerActivities(final Player joiner, boolean type) {
        if (type && joinEnabled) {
            if (greetingsEnabled) {
                joiner.sendMessage(formatQuick(greetingsMessage, joiner));
            }
            switch (joinLevel) {
                case 1:
                    loopType(joinMode, joiner, defaultJoin, joinPermissions, PlayerHandler.INSTANCE.getPlayersFromPlace(joinPlace).values());
                    return;
                case 2:
                    loopType(joinMode, joiner, defaultJoin, joinPermissions, PlayerHandler.INSTANCE.getPlayers().values());
                    return;
            }
        }
        if (quitEnabled) {
            switch (quitLevel) {
                case 1:
                    loopType(quitMode, joiner, defaultQuit, quitPermissions, PlayerHandler.INSTANCE.getPlayersFromPlace(quitPlace).values());
                    return;
                case 2:
                    loopType(quitMode, joiner, defaultQuit, quitPermissions, PlayerHandler.INSTANCE.getPlayers().values());
            }
        }
    }


    private String formatQuick(final String message, final Player player) {
        return PlaceHolders.applyPlaceHoldersifPossible(ColorSystem.tCC(message), player);
    }
}