package Others;

import UniversalFunctions.Player;

public class SoundSystem {

    private static boolean notificationsenabled;

    private static String staffsound, playersound;
    private static float staffvolume, playervolume, staffpitch, playerpitch;

    public static void update() {
        notificationsenabled = ConfigSystem.INSTANCE.getConfig().getBoolean("notifications");
        if (notificationsenabled) {

            staffsound = ConfigSystem.INSTANCE.getConfig().get("notification.staff.sound", "BLOCK_NOTE_BLOCK_PLING").toString();
            playersound = ConfigSystem.INSTANCE.getConfig().get("notification.player.sound", "BLOCK_NOTE_BLOCK_PLING").toString();
            staffvolume = ConfigSystem.INSTANCE.getConfig().getFloat("notification.staff.volume", 1f);
            staffpitch = ConfigSystem.INSTANCE.getConfig().getFloat("notification.staff.pitch", 1f);
            playervolume = ConfigSystem.INSTANCE.getConfig().getFloat("notification.player.volume", 1f);
            playerpitch = ConfigSystem.INSTANCE.getConfig().getFloat("notification.player.pitch", 1f);
        }
    }

    public static void playSoundToPlayer(final Player p, final boolean staff) {
        ThreadsSystem.runAsync(() -> {
            if (notificationsenabled) {
                if (staff) {
                    p.playSound(staffsound, staffvolume, staffpitch);
                    return;
                }
                p.playSound(playersound, playervolume, playerpitch);
            }
        });
    }
}