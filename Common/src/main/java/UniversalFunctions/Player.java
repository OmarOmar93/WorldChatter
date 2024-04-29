package UniversalFunctions;


import java.util.UUID;

public interface Player extends CommandSender {

    void sendMessage(final String message);
    boolean hasPermission(final String permission);
    void playSound(final String soundName, final float volume, final float pitch);

    UUID getUUID();
    String getName();
    String getPlace();
    String getDisplayName();
}
