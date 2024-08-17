package WorldChatterCore.Others;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.ArrayList;
import java.util.List;

public class ServerOptions {

    public static ServerOptions INSTANCE;
    private boolean globalChat;

    public ServerOptions() {
        INSTANCE = this;
    }

    public void update() {
        globalChat = ConfigSystem.INSTANCE.getPlace().getBoolean("GlobalChat");
    }

    public List<Player> getPlayersinPlace(final String place) {
        final List<Player> placePlayers = new ArrayList<>();
        for (Player p : PlayerHandler.INSTANCE.getPlayers().values()) {
            if (p.getPlace().equalsIgnoreCase(place)) {
                placePlayers.add(p);
            }
        }
        return placePlayers;
    }

    public boolean isGlobalChat() {
        return globalChat;
    }
}
