package WorldChatterCore.API;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.UpdateSystem;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class WCA {

    private static final List<WCListener> listners = new CopyOnWriteArrayList<>();
    public static WCA INSTANCE;

    public WCA() {
        if (INSTANCE == null) INSTANCE = this;
    }

    /**
     * @param wcListener Listener Class
     */
    public void addListener(final WCListener wcListener) {
        if (wcListener == null) return;
        listners.add(wcListener);
    }

    public List<WCListener> getListeners() {
        return listners;
    }

}