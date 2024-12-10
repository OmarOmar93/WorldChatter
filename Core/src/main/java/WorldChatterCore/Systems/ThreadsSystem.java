package WorldChatterCore.Systems;

import java.util.Timer;
import java.util.TimerTask;

public final class ThreadsSystem {

    private static final Timer timer = new Timer();

    public static void runAsync(final Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void runAsyncLater(final Runnable runnable, final long delayMiliseconds) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runAsync(runnable);
            }
        }, delayMiliseconds);
    }
}