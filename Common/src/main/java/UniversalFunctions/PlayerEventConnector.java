package UniversalFunctions;

public class PlayerEventConnector {

    public static PlayerEventConnector INSTANCE;
    private PlayerEventInterface playerSystem;

    public PlayerEventConnector() {
        INSTANCE = this;
    }

    public void setPlayerSystem(final PlayerEventInterface playerSystem) {
        this.playerSystem = playerSystem;
    }

    public void update() {
        playerSystem.reloadPlayerEvent();
    }
}
