package WorldChatterCore.Features;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AntiSpam {

    private long duration;
    public static AntiSpam INSTANCE;
    private final Map<Player, Long> cooldowns;

    public AntiSpam() {
        INSTANCE = this;

        cooldowns = new ConcurrentHashMap<>();
    }

    /**
     * This is executed by ConfigSystem's reload function
     */
    public void update() {
        cooldowns.clear();
        duration = ConfigSystem.INSTANCE.getSecurity().getInt("AntiSpam") * 1000L;
    }

    /**
     * adds the player in a cooldown
     * @param player the player
     */
    public void coolThatPlayerDown(final Player player) {
        cooldowns.put(player, System.currentTimeMillis() + duration);
    }

    /**
     * Gets the time remaining from player if it is in a cooldown
     * @param player the player
     * @return the time left
     */
    public String getTimeLeft(final Player player) {
        return cooldowns.containsKey(player) ? String.valueOf((cooldowns.get(player) - System.currentTimeMillis()) / 1000) : null;
    }

    /**
     * Checks if the player is in a cooldown or not
     * @param player the player
     * @return if there is or not
     */
    public boolean isTimeLeft(final Player player) {
        if (cooldowns.containsKey(player)) {
            if (cooldowns.get(player) < System.currentTimeMillis()) {
                cooldowns.remove(player);
                return false;
            }
            return true;
        }
        return false;
    }
}