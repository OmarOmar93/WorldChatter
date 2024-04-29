package me.omaromar93.worldchatterbungee;

import Others.ConfigSystem;
import Others.PlayerSystem;
import Others.ThreadsSystem;
import Others.UpdaterSystem;
import UniversalFunctions.UniLogHandler;
import functions.BungeeLog;
import functions.BungeePlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public final class WorldChatterBungee extends Plugin {

    public static WorldChatterBungee INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        new ConfigSystem(getLogger());

        new BroadcastSystem();
        new UniLogHandler(new BungeeLog());

        getProxy().getPluginManager().registerListener(this, new ChatEventHandler());
        getProxy().getPluginManager().registerCommand(this, new CommandSystem());

        ThreadsSystem.runAsync(() -> {
            ConfigSystem.INSTANCE.update();
            getProxy().getPluginManager().registerListener(this, new PlayerEventHandler());
            final Boolean b = UpdaterSystem.isUpdated();
            for (final ProxiedPlayer player : getProxy().getPlayers()) {
                PlayerSystem.INSTANCE.addPlayer(player.getUniqueId(), new BungeePlayer(player));
            }
            if (b == null) {
                UniLogHandler.INSTANCE.sendMessage(ChatColor.RED + "Error has occurred while fetching the update.");
                return;
            }
            if (b) {
                UniLogHandler.INSTANCE.sendMessage(ChatColor.YELLOW + "WorldChatter has released a new update! " + ChatColor.WHITE + "-> " + ChatColor.GREEN + UpdaterSystem.newupdate + ChatColor.BLUE + "\nDownload the update at https://www.spigotmc.org/resources/worldchatter.101226/");
                return;
            }
            UniLogHandler.INSTANCE.sendMessage(ChatColor.YELLOW + "WorldChatter is in it's latest update!");
        });
    }

    @Override
    public void onDisable() {
        UniLogHandler.INSTANCE.sendMessage(ChatColor.BLUE + "Goodbye and thanks for using WorldChatter ^ - ^");
    }
}
