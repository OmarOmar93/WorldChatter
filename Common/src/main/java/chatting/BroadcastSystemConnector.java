package chatting;

public final class BroadcastSystemConnector {

    public static BroadcastSystemConnector INSTANCE;
    private BroadcastSystemInterface broadcastSystem;

    public BroadcastSystemConnector() {
        INSTANCE = this;
    }

    public void setBroadcastSystem(final BroadcastSystemInterface broadcastSystem) {
        this.broadcastSystem = broadcastSystem;
    }

    public void update() {
        broadcastSystem.update();
    }
}