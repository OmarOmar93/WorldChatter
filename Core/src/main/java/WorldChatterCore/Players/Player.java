package WorldChatterCore.Players;

import WorldChatterCore.Connectors.Interfaces.CommandSender;

import java.util.UUID;

public interface Player extends CommandSender {

    void sendMessage(final String message);

    boolean hasPermission(final String permission);

    void playSound(final String soundName, final float volume, final float pitch);

    UUID getUniqueId();

    String getName();

    String getPlace();

    String getRawPlace();

    String getDisplayName();

    void kick(final String reason);

}
