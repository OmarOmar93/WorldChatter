package WorldChatterCore.Players;

import java.util.HashMap;
import java.util.UUID;

public class PlayerHandler {
    private final HashMap<UUID, Player> players = new HashMap<>();
    public static PlayerHandler INSTANCE;


    public PlayerHandler() {
        INSTANCE = this;
    }

    public HashMap<UUID, Player> getPlayers() {
        return players;
    }

    public HashMap<UUID, Player> getPlayersFromPlace(final String place) {
        final HashMap<UUID, Player> places = new HashMap<>();
        for (Player player : getPlayers().values()) {
            if (player.getPlace().equalsIgnoreCase(place)) {
                places.put(player.getUniqueId(), player);
            }
        }
        return places;
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