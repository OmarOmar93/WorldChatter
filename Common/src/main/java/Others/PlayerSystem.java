package Others;

import UniversalFunctions.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class PlayerSystem {

    public static PlayerSystem INSTANCE;
    private final HashMap<UUID, Player> players;

    public PlayerSystem() {
        INSTANCE = this;

        players = new HashMap<>();
    }

    public void addPlayer(final UUID uuid, final Player player) {
        if (!players.containsKey(uuid)) players.put(uuid, player);
    }

    public void removePlayer(final UUID uuid) {
        players.remove(uuid);
    }

    public Player getPlayer(final UUID uuid){
        return players.get(uuid);
    }

    public final Collection<Player> getPlayers() {
        return players.values();
    }
}
