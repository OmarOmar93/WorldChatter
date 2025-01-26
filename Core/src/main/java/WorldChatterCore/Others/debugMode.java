package WorldChatterCore.Others;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Systems.ConfigSystem;

public final class debugMode {
    public static debugMode INSTANCE;
    private boolean isDebug;
    private String info, warning, error;

    public debugMode() {
        INSTANCE = this;
    }

    public enum printType {
        INFO, WARNING, ERROR
    }

    public void update() {
        isDebug = ConfigSystem.INSTANCE.getSystem().getBoolean("debug.enabled");
        if (isDebug) {
            info = ConfigSystem.INSTANCE.getSystem().getString("debug.info");
            warning = ConfigSystem.INSTANCE.getSystem().getString("debug.warning");
            error = ConfigSystem.INSTANCE.getSystem().getString("debug.error");
            return;
        }
        info = null;
        warning = null;
        error = null;
    }

    /**
     * Prints a debug message
     * @param message the message
     * @param type the printed message's type
     */
    public void println(final String message, final printType type) {
        if (isDebug) {
            switch (type) {
                case INFO:
                    MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(info + message);
                    return;
                case WARNING:
                    MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(warning + message);
                    return;
                case ERROR:
                    MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(error + message);
            }
        }
    }
}
