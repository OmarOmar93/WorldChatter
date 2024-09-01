package WorldChatterCore.Systems;

import WorldChatterCore.Others.ServerOptions;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import WorldChatterCore.Players.Player;

public final class BroadcastSystem {

    public static BroadcastSystem INSTANCE;
    private final Random rn = new Random();
    private boolean enabled, shuffle;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Map<String, placeData> placesC = new HashMap<>();
    public BroadcastSystem() {
        INSTANCE = this;
    }

    public void update() {
        enabled = ConfigSystem.INSTANCE.getPlace().getBoolean("broadcast.enabled");
        if (enabled) {
            shuffle = ConfigSystem.INSTANCE.getPlace().getBoolean("broadcast.shufflemessages");
            for (final String key : ConfigSystem.INSTANCE.getPlace().getSection("broadcast.places").getKeys()) {
                placesC.put(key, new placeData(key,
                        ConfigSystem.INSTANCE.getPlace().getStringList(key + ".messages"),
                        ConfigSystem.INSTANCE.getPlace().getBoolean(key + ".randomized"),
                        ConfigSystem.INSTANCE.getPlace().getInt(key + ".timer")
                ));
            }
            startBroadcast();
        }
    }

    private void startBroadcast() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!enabled) {
                scheduler.shutdown();
                placesC.clear();
                return;
            }
            for (final placeData place : placesC.values()) {
                final Collection<Player> players = ServerOptions.INSTANCE.getPlayersinPlace(place.name);
                long delay = (place.random ? rn.nextInt(place.timer) : place.timer);
                scheduler.schedule(() -> {
                    final String message = place.messages.get(rn.nextInt(place.messages.size()));
                    if (shuffle) {
                        for (final Player player : players) {
                            player.sendMessage(place.messages.get(rn.nextInt(place.messages.size())));
                        }
                    } else {
                        for (final Player player : players) {
                            player.sendMessage(message);
                        }
                    }
                }, delay, TimeUnit.SECONDS);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private static class placeData {
        final List<String> messages;
        final boolean random;
        final int timer;
        final String name;


        public placeData(final String name, final List<String> messages, final boolean random, final int timer) {
            this.messages = messages;
            this.random = random;
            this.timer = timer;
            this.name = name;
        }
    }



}