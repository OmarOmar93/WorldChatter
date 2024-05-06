package functions;

import UniversalFunctions.UniLog;
import net.md_5.bungee.api.ProxyServer;

public class BungeeLog implements UniLog {
    @Override
    public void sendmessage(final String message) {
        ProxyServer.getInstance().getLogger().info(message);
    }
}
