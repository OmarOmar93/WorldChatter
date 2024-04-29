package Others;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public final class CacheSystem {
    public final static HashMap<String, Object> cache = new HashMap<>();

    public static void addCache(final String key, final Object value) {
        cache.put(key, value);
    }

    public static void removeCache(final String key) {
        cache.remove(key);
    }

    public static Object getCache(final String key) {
        return cache.getOrDefault(key, null);
    }

    public static Object getOrAddCache(final String key, final Object def) {
        return cache.getOrDefault(key, cache.put(key, def));
    }

    public static void removeCacheAfterSeconds(final String key, final int seconds) {
        ThreadsSystem.runAsync(() -> new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                removeCache(key);
            }
        }, seconds * 1000L));
    }

    public static boolean hasCache(final String key) {
        return cache.containsKey(key);
    }

}