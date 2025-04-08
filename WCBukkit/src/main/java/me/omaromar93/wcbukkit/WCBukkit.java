package me.omaromar93.wcbukkit;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.MainPlugin;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Others.debugMode;
import WorldChatterCore.Players.PlayerHandler;
import com.onarandombox.MultiverseCore.MultiverseCore;
import me.clip.placeholderapi.PlaceholderAPI;
import me.omaromar93.wcbukkit.Events.AsyncPlayerChat;
import me.omaromar93.wcbukkit.Events.Legacy.LegacyListener;
import me.omaromar93.wcbukkit.Events.Legacy.PlayerChat;
import me.omaromar93.wcbukkit.Parent.Command;
import me.omaromar93.wcbukkit.Parent.BukkitPlayer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class WCBukkit extends JavaPlugin implements MainPlugin {

    public static BukkitAudiences adventure;
    public static MultiverseCore mvcore;
    public static WCBukkit INSTANCE;

    public WCBukkit() {
        INSTANCE = this;
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        mvcore = plugin != null ? (MultiverseCore) plugin : null;
        new MainPluginConnector();
    }


    @Override
    public void onEnable() throws RuntimeException {
        MainPluginConnector.INSTANCE.setWorldChatter(this);
        registerEvents(getServer().getPluginManager());
        getCommand("worldchatter").setExecutor(new Command());
        MainPluginConnector.INSTANCE.loadSupportedPlugins();
    }


    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        MainPluginConnector.INSTANCE.onDisable();
    }


    private void registerEvents(final PluginManager pm) {
        // Determine if we have the modern asynchronous event.
        boolean modernEvents;
        try {
            Class.forName("org.bukkit.event.player.AsyncPlayerChatEvent");
            modernEvents = true;
        } catch (ClassNotFoundException e) {
            modernEvents = false;
        }

        if (modernEvents) {
            pm.registerEvents(new AsyncPlayerChat(), this);
            pm.registerEvents(new me.omaromar93.wcbukkit.Events.PlayerJoin(), this);
            pm.registerEvents(new me.omaromar93.wcbukkit.Events.PlayerQuit(), this);
        } else {
            // First try standard registration.
            try {
                pm.registerEvents(new PlayerChat(), this);
                pm.registerEvents(new me.omaromar93.wcbukkit.Events.PlayerJoin(), this);
                pm.registerEvents(new me.omaromar93.wcbukkit.Events.PlayerQuit(), this);
            } catch (Throwable t) {
                // If standard registration fails, we will fall back on the proxy.
                debugMode.INSTANCE.println("Standard registerEvents not available.", debugMode.printType.WARNING);
                try {
                    debugMode.INSTANCE.println("Using legacy event system", debugMode.printType.WARNING);
                    // Obtain legacy registerEvent method
                    final Method legacyRegisterEvent = pm.getClass().getMethod(
                            "registerEvent",
                            Class.forName("org.bukkit.event.Event$Type"),      // legacy Event.Type
                            Listener.class,                                     // Our listener must implement Listener
                            Class.forName("org.bukkit.event.Event$Priority"),    // legacy Event.Priority
                            Plugin.class
                    );
                    final Class<?> eventTypeClass = Class.forName("org.bukkit.event.Event$Type");

                    final LegacyListener legacyListener = new LegacyListener();

                    // Register legacy events
                    legacyRegisterEvent.invoke(pm,
                            Enum.valueOf((Class<Enum>) eventTypeClass, "PLAYER_CHAT"),
                            legacyListener,
                            Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.event.Event$Priority"), "Normal"),
                            this
                    );
                    legacyRegisterEvent.invoke(pm,
                            Enum.valueOf((Class<Enum>) eventTypeClass, "PLAYER_QUIT"),
                            legacyListener,
                            Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.event.Event$Priority"), "Normal"),
                            this
                    );
                    legacyRegisterEvent.invoke(pm,
                            Enum.valueOf((Class<Enum>) eventTypeClass, "PLAYER_JOIN"),
                            legacyListener,
                            Enum.valueOf((Class<Enum>) Class.forName("org.bukkit.event.Event$Priority"), "Normal"),
                            this
                    );
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @Override
    public boolean isPluginEnabled(final String name) {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }

    @Override
    public void sendConsoleMessage(final String message) {
        if (adventure != null) {
            adventure.console().sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
            return;
        }
        try {
            getServer().getConsoleSender().sendMessage(message);
        } catch (final IncompatibleClassChangeError ignored) {
            try {
                Class.forName("org.bukkit.command.ConsoleCommandSender").getMethod("sendMessage", String.class).invoke(Bukkit.getConsoleSender(), message);
            } catch (final Exception p) {
                debugMode.INSTANCE.println("idek anymore. \n" +
                        Arrays.toString(p.getStackTrace()), debugMode.printType.ERROR);
            }
        }
    }

    @Override
    public void refreshPlayers() {
        try {
            for (final Player p : Bukkit.getServer().getOnlinePlayers())
                PlayerHandler.INSTANCE.addPlayer(new BukkitPlayer(p));
        } catch (final NoSuchMethodError e) {
            try {
                for (final Object o : (Object[]) Bukkit.getServer().getClass().getMethod("getOnlinePlayers").invoke(Bukkit.getServer()))
                    PlayerHandler.INSTANCE.addPlayer(new BukkitPlayer((Player) o));
            } catch (final Exception ex) {
                debugMode.INSTANCE.println("Huh... " +
                        ex.getMessage() + "\n" +
                        Arrays.toString(ex.getStackTrace()), debugMode.printType.ERROR);
            }
        }
    }


    @Override
    public void broadcastMessage(final String message) {
        if (adventure != null) {
            adventure.all().sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
            return;
        }
        getServer().broadcastMessage(message);
    }

    @Override
    public String supporttheMessage(String message, final WorldChatterCore.Players.Player player) {
        if (MainPluginConnector.INSTANCE.getWorldChatter().isPluginEnabled("PlaceholderAPI")) {
            message = PlaceholderAPI.setPlaceholders(getServer().getPlayer(player.getUniqueId()), message);
        }
        if (MainPluginConnector.INSTANCE.getWorldChatter().isPluginEnabled("Multiverse-Core")) {
            message = message.replace("{player_mvworld}", player.getPlace());
        }
        return message;
    }

    @Override
    public void tryToSupportMiniMessage() throws NoSuchMethodError {
        adventure = BukkitAudiences.create(this);
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

}