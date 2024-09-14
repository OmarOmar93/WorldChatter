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


    public void update() {
        cooldowns.clear();
        duration = ConfigSystem.INSTANCE.getSecurity().getInt("AntiSpam") * 1000L;
    }

    public void coolThatPlayerDown(final Player player) {
        cooldowns.put(player, System.currentTimeMillis() + duration);
    }

    public String getTimeLeft(final Player player) {
        if (cooldowns.containsKey(player)) {
            final long l = cooldowns.get(player) - System.currentTimeMillis();
            return String.valueOf(l / 1000);
        }
        return null;
    }

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