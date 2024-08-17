package WorldChatterCore.Systems;

public class ThreadsSystem {
    public static void runAsync(final Runnable runnable) {
        new Thread(runnable).start();
    }
}
