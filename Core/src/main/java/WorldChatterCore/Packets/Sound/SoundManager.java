package WorldChatterCore.Packets.Sound;

import WorldChatterCore.Packets.Sound.Sounds.P_107;
import WorldChatterCore.Packets.Sound.Sounds.P_210;
import WorldChatterCore.Packets.Sound.Sounds.P_47;

public final class SoundManager {

    public static String getSound(final String soundName, final int protocolVersion) {
        switch (protocolVersion) {
            case 47:
                return P_47.getSound(soundName);
            case 107:
            case 108:
            case 109:
            case 110:
                return P_107.getSound(soundName);
            case 210:
                return P_210.getSound(soundName);
            default:
                return soundName;
        }
    }

}
