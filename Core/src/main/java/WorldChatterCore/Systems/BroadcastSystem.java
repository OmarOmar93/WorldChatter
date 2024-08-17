package WorldChatterCore.Systems;

import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Players.PlayerHandler;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import WorldChatterCore.Players.Player;

public class BroadcastSystem {

    public static BroadcastSystem INSTANCE;
    private final Random rn = new Random();
    private boolean enabled, shuffle;
    private Configuration places;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public BroadcastSystem() {
        INSTANCE = this;
    }

    public void update() {
        enabled = ConfigSystem.INSTANCE.getPlace().getBoolean("broadcast.enabled");
        if (enabled) {
            shuffle = ConfigSystem.INSTANCE.getPlace().getBoolean("broadcast.shufflemessages");
            places = ConfigSystem.INSTANCE.getPlace().getSection("broadcast.places");
            startBroadcast();
        }
    }

    private void startBroadcast() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!enabled) {
                scheduler.shutdown();  // Stop the scheduler if not enabled
                return;
            }
            for (String key : places.getKeys()) {
                final List<String> messages = ConfigSystem.INSTANCE.getPlace().getStringList(key + ".messages");
                final boolean random = ConfigSystem.INSTANCE.getPlace().getBoolean(key + ".randomized");
                final int timer = ConfigSystem.INSTANCE.getPlace().getInt(key + ".timer");
                final Collection<Player> players = PlayerHandler.INSTANCE.getPlayersFromPlace(key).values();

                long delay = (random ? rn.nextInt(timer) : timer) * 1000L;

                scheduler.schedule(() -> {
                    String message = messages.get(rn.nextInt(messages.size()));
                    if (shuffle) {
                        for (Player player : players) {
                            player.sendMessage(messages.get(rn.nextInt(messages.size())));
                        }
                    } else {
                        for (Player player : players) {
                            player.sendMessage(message);
                        }
                    }
                }, delay, TimeUnit.MILLISECONDS);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}