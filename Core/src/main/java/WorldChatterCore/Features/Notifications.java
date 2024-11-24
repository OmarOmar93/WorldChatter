package WorldChatterCore.Features;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

public final class Notifications {

    public static Notifications INSTANCE;

    private String staffsound, playersound, staffMessage, playerMessage;
    private float staffvolume, playervolume, staffpitch, playerpitch;

    public Notifications() {
        INSTANCE = this;
    }

    public void update() {
        if (ConfigSystem.INSTANCE.getPlayer().getBoolean("notification.enabled")) {
            staffMessage = ConfigSystem.INSTANCE.getMessages().getString("DetectedMessage");
            playerMessage = ConfigSystem.INSTANCE.getMessages().getString("DetectedPlayerMessage");

            staffsound = ConfigSystem.INSTANCE.getPlayer().getString("notification.staff.sound", "BLOCK_NOTE_BLOCK_PLING");
            staffvolume = ConfigSystem.INSTANCE.getPlayer().getFloat("notification.staff.volume", 1f);
            staffpitch = ConfigSystem.INSTANCE.getPlayer().getFloat("notification.staff.pitch", 1f);

            playersound = ConfigSystem.INSTANCE.getPlayer().getString("notification.player.sound", "BLOCK_NOTE_BLOCK_PLING");
            playervolume = ConfigSystem.INSTANCE.getPlayer().getFloat("notification.player.volume", 1f);
            playerpitch = ConfigSystem.INSTANCE.getPlayer().getFloat("notification.player.pitch", 1f);
            return;
        }
        staffMessage = null;
        playerMessage = null;
        staffsound = null;
        playersound = null;

    }


    public void alertStaffandPlayer(final String methods, final Player detectedPlayer, final String message) {
        detectedPlayer.playSound(playersound, playervolume, playerpitch);
        detectedPlayer.sendMessage(ColorSystem.tCC(PlaceHolders.applyPlaceHoldersifPossible(playerMessage.replace("%flags%", methods), detectedPlayer)));
        final String sM = ColorSystem.tCC(PlaceHolders.applyPlaceHoldersifPossible(staffMessage
                .replace("%flags%", methods)
                .replace("%message%",message), detectedPlayer));
        for (final Player player : PlayerHandler.INSTANCE.getPlayers().values()) {
            if (player.hasPermission("worldchatter.control")) {
                player.playSound(staffsound, staffvolume, staffpitch);
                player.sendMessage(sM);
            }
        }
        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(sM);
    }
}