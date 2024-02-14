package me.omaromar93.worldchatter.utils.Others;

import me.omaromar93.worldchatter.Main;

import java.util.HashMap;

import static org.bukkit.Bukkit.getScheduler;

public final class CacheSystem {
    public final static HashMap<String, Object> cache = new HashMap<>();

    public static void addCache(final String key, final Object value) {
        cache.put(key, value);
    }

    public static void removeCache(final String key) {
        cache.remove(key);
    }

    public static Object getCache(final String key) {
       return cache.getOrDefault(key,null);
    }

    public static void removeCacheAfterSeconds(final String key, final int seconds) {
        getScheduler().runTaskLaterAsynchronously(Main.INSTANCE, () -> removeCache(key), seconds * 20L);
    }

    public static boolean hasCache(final String key) {
        return cache.containsKey(key);
    }

}
