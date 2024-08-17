package WorldChatterCore.Features;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AntiSpam {

    public static AntiSpam INSTANCE;

    public AntiSpam() {
        INSTANCE = this;
    }

    private final HashMap<Player, Long> cooldowns = new HashMap<>();


    public void update() {
        cooldowns.clear();
    }

    public void coolThatPlayerDown(final Player player) {
        final long endtime = ConfigSystem.INSTANCE.getSecurity().getInt("AntiSpam") * 1000L;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cooldowns.remove(player);
            }
        }, endtime);
        cooldowns.put(player, System.currentTimeMillis() + endtime);
    }

    public String getTimeLeft(final Player player) {
        if (cooldowns.containsKey(player)) {
            final long l = cooldowns.get(player) - System.currentTimeMillis();
            return String.valueOf(l / 1000);
        }
        return null;
    }

}
