package WorldChatterCore.Players;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlayerHandler {
    private static final Map<UUID, Player> players = new ConcurrentHashMap<>();
    public static PlayerHandler INSTANCE;


    public PlayerHandler() {
        INSTANCE = this;
    }

    public Map<UUID, Player> getPlayers() {
        return players;
    }

    public Player getPlayerUUID(final UUID uuid) {
        return players.get(uuid);
    }

    public Player getPlayerName(final String name) {
        return players.values().stream()
                .filter(player -> player.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void addPlayer(final Player player) {
        players.put(player.getUniqueId(), player);
    }

    public void removePlayer(final Player player) {
        players.remove(player.getUniqueId());
    }

}