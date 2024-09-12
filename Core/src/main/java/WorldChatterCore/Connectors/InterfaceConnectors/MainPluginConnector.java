package WorldChatterCore.Connectors.InterfaceConnectors;

import WorldChatterCore.Connectors.Interfaces.MainPlugin;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;
import WorldChatterCore.Systems.UpdateSystem;


public final class MainPluginConnector {

    public static MainPluginConnector INSTANCE;
    private MainPlugin WorldChatter;

    public MainPluginConnector() {
        INSTANCE = this;
    }

    public void setWorldChatter(final MainPlugin WorldChatter) {
        this.WorldChatter = WorldChatter;
        onEnable();
    }

    public MainPlugin getWorldChatter() {
        return WorldChatter;
    }

    private void onEnable() {
        new PlayerHandler();
        try {
            getWorldChatter().tryToSupportMiniMessage();
            new MiniMessageConnector();
            getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "Enabled MiniMessage Support!");
        } catch (final NoSuchMethodError e) {
            getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "MiniMessage isn't supported in this version.");
        }
        new ConfigSystem();
        getWorldChatter().refreshPlayers();
        new UpdateSystem();
        UpdateSystem.INSTANCE.messageCheck(null);
        for (final String plugin : new String[]{"PlaceholderAPI", "Multiverse-Core"}) {
            if (getWorldChatter().isPluginEnabled(plugin)) {
                getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "Enabled Support for " + ColorSystem.YELLOW + plugin + "!");
            }
        }
    }

    public void onDisable() {
        getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.BLUE + "Goodbye and thanks for using WorldChatter ^ - ^");
    }

}
