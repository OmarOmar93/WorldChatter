package WorldChatterCore.Systems;

public final class ThreadsSystem {
    public static void runAsync(final Runnable runnable) {
        new Thread(runnable).start();
    }
}
