package WorldChatterCore.Connectors.Interfaces;

import WorldChatterCore.Players.Player;

public interface MainPlugin {

    boolean isPluginEnabled(final String name);

    void sendConsoleMessage(final String message);

    void refreshPlayers();

    void broadcastMessage(final String message);

    String supporttheMessage(final String message, final Player player);

    void tryToSupportMiniMessage();

    String getVersion();
}
