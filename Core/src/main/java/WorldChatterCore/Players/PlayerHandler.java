package WorldChatterCore.Players;

import java.util.HashMap;
import java.util.UUID;

public final class PlayerHandler {
    private final HashMap<UUID, Player> players = new HashMap<>();
    public static PlayerHandler INSTANCE;


    public PlayerHandler() {
        INSTANCE = this;
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public Player getPlayerUUID(final UUID uuid) {
        return players.get(uuid);
    }

    public void addPlayer(final Player player) {
        players.put(player.getUniqueId(), player);
    }

    public void removePlayer(final Player player) {
        players.remove(player.getUniqueId());
    }

}