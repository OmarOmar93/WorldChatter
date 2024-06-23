package me.omaromar93.worldchatterbungee;

import API.APICore;
import API.WorldChatterAPI;
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
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners()) api.configReload(null, null);
            getProxy().getPluginManager().registerListener(this, new PlayerEventHandler());
            ConfigSystem.INSTANCE.updatePlayerEvent();
            final Boolean b = UpdaterSystem.isUpdated();
            if (b == null) {
                UniLogHandler.INSTANCE.sendMessage(ChatColor.RED + "Error has occurred while fetching the update.");
                return;
            }
            if (b) {
                UniLogHandler.INSTANCE.sendMessage(ChatColor.YELLOW + "WorldChatter has released a new" + (UpdaterSystem.isDev ? ChatColor.GOLD + " DEVELOPMENT" : "") + ChatColor.YELLOW + " update! " + ChatColor.GRAY + "( " + ChatColor.GOLD + UpdaterSystem.updatetitle + ChatColor.GRAY + " )" + ChatColor.WHITE + " -> " + ChatColor.GREEN + UpdaterSystem.newupdate + ChatColor.BLUE + "\nDownload the update at https://www.spigotmc.org/resources/worldchatter.101226/");
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
