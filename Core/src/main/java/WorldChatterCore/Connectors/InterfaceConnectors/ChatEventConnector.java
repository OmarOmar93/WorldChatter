package WorldChatterCore.Connectors.InterfaceConnectors;

import WorldChatterCore.Connectors.Interfaces.PlayerChatEvent;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.FeatureSystem;

public final class ChatEventConnector implements PlayerChatEvent {

    private final Player player;
    private final String message;
    private final FeatureSystem featureSystem;

    public ChatEventConnector(final Player player, final String message) {
        this.player = player;
        this.message = message;
        featureSystem = new FeatureSystem(player, message);
    }

    public FeatureSystem getFeatureSystem() {
        return featureSystem;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
