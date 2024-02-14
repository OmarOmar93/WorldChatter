package me.omaromar93.worldchatter.utils.Others;

public final class ThreadsSystem {
    public static void runAsync(final Runnable runnable) {
        new Thread(runnable).start();
    }

}
