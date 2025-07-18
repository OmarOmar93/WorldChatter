package WorldChatterCore.API;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class WCA {

    private static final List<WCListener> listeners = new CopyOnWriteArrayList<>();
    public static WCA INSTANCE;

    public WCA() {
        if (INSTANCE == null) INSTANCE = this;
    }

    /**
     * @param wcListener Listener Class
     */
    public void addListener(final WCListener wcListener) {
        if (wcListener == null) return;
        listeners.add(wcListener);
    }

    public List<WCListener> getListeners() {
        return listeners;
    }

}