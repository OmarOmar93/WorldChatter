package WorldChatterCore.Packets.Sound;

import WorldChatterCore.Packets.Sound.Sounds.P_107;
import WorldChatterCore.Packets.Sound.Sounds.P_47;

public final class SoundManager {

    public static String getSound(final String soundName, final int protocolVersion) {
        switch (protocolVersion) {
            case 47:
                return P_47.getSound(soundName);
            case 107:
                return P_107.getSound(soundName);
            default:
                return soundName;
        }
    }

}
