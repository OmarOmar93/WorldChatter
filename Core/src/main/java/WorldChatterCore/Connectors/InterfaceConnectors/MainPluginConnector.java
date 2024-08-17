package WorldChatterCore.Connectors.InterfaceConnectors;

import WorldChatterCore.Connectors.Interfaces.MainPlugin;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;
import WorldChatterCore.Systems.UpdateSystem;


public class MainPluginConnector {

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
        new ConfigSystem();
        try {
            new MiniMessageConnector();
            getWorldChatter().tryToSupportMiniMessage();
            getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "Enabled MiniMessage Support!");
        } catch (NoSuchMethodError e) {
            getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "MiniMessage isn't supported in this version.");
        }
        getWorldChatter().refreshPlayers();
        for (final String plugin : new String[]{"PlaceHolderAPI", "Multiverse-Core"}) {
            if (getWorldChatter().isPluginEnabled(plugin)) {
                getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "Enabled Support for " + ColorSystem.YELLOW + plugin + "!");
            }
        }
        new UpdateSystem();
        UpdateSystem.INSTANCE.messageCheck(null);
    }

    public void onDisable() {
        getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.BLUE + "Goodbye and thanks for using WorldChatter ^ - ^");
    }

}
