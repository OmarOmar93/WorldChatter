package WorldChatterCore.Features;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.HashMap;

public final class AntiSpam {

    public static AntiSpam INSTANCE;

    public AntiSpam() {
        INSTANCE = this;
    }

    private final HashMap<Player, Long> cooldowns = new HashMap<>();
    private long endtime;

    public void update() {
        cooldowns.clear();
        endtime = ConfigSystem.INSTANCE.getSecurity().getInt("AntiSpam") * 1000L;
    }

    public void coolThatPlayerDown(final Player player) {
        cooldowns.put(player, System.currentTimeMillis() + endtime);
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