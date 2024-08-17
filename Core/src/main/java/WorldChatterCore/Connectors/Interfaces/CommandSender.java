package WorldChatterCore.Connectors.Interfaces;

import WorldChatterCore.Players.Player;

public interface CommandSender {

    boolean isPlayer();

    void sendMessage(final String message);

    boolean hasPermission(final String permission);

    String getName();

    Player getPlayer();
}
