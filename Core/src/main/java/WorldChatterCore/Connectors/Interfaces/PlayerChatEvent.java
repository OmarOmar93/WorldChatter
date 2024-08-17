package WorldChatterCore.Connectors.Interfaces;

import WorldChatterCore.Players.Player;

public interface PlayerChatEvent {
    Player getPlayer();

    String getMessage();

}