package me.omaromar93.worldchatterbungee;

import Others.ConfigSystem;
import Others.ThreadsSystem;
import chatting.BroadcastSystemConnector;
import chatting.BroadcastSystemInterface;
import methods.Expression;
import methods.MoreFormat;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BroadcastSystem implements BroadcastSystemInterface {

    private ScheduledTask broadcastTasker;
    private final Random rand = new Random();
    private HashMap<String, HashMap<String, Object>> worldsSection;

    public BroadcastSystem() {
        BroadcastSystemConnector.INSTANCE.setBroadcastSystem(this);
    }

    @Override
    public void update() {
        if (broadcastTasker != null) broadcastTasker.cancel();
        System.gc();
        ThreadsSystem.runAsync(() -> {
            if (ConfigSystem.INSTANCE.getBroadcast().getBoolean("broadcast.enabled")) {
                worldsSection = ConfigSystem.INSTANCE.getBroadcast().getConfigurationSection("broadcast.places");
                final List<ServerInfo> servers = new ArrayList<>();
                final List<String> list = ConfigSystem.INSTANCE.getConfig().getStringList("BlackListPlaces");
                for (final Object server : worldsSection.keySet()) {
                    if (!list.contains(server.toString()))
                        servers.add(ProxyServer.getInstance().getServerInfo(server.toString()));
                }
                broadcastTasker = ProxyServer.getInstance().getScheduler().schedule(WorldChatterBungee.INSTANCE, () -> {
                    if (rand.nextInt(100) < 80) {
                        final ServerInfo server = servers.get(getrandomint(servers.size()));
                        final List<String> messages = (List<String>) worldsSection.get(server.getName());
                        if (ConfigSystem.INSTANCE.getBroadcast().getBoolean("broadcast.shufflemessages")) {
                            for (final ProxiedPlayer player : server.getPlayers()) {
                                player.sendMessage(MoreFormat.FormatMore(Expression.translateColors(messages.get(getrandomint(messages.size())))));
                            }
                            return;
                        }
                        final TextComponent textComponent = MoreFormat.FormatMore(Expression.translateColors(messages.get(getrandomint(messages.size()))));
                        for (final ProxiedPlayer player : server.getPlayers()) {
                            player.sendMessage(textComponent);
                        }
                        return;
                    }
                    final List<String> messages = ConfigSystem.INSTANCE.getBroadcast().getStringList("broadcast.commonmessages");
                    if (ConfigSystem.INSTANCE.getBroadcast().getBoolean("broadcast.shufflemessages")) {
                        for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                            player.sendMessage(MoreFormat.FormatMore(Expression.translateColors(messages.get(getrandomint(messages.size())))));
                        }
                        return;
                    }
                    final TextComponent textComponent = MoreFormat.FormatMore(Expression.translateColors(messages.get(getrandomint(messages.size()))));
                    for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                        player.sendMessage(textComponent);
                    }
                }, 0, ConfigSystem.INSTANCE.getBroadcast().getInt("broadcast.messagecycle"), TimeUnit.SECONDS);
            }
        });
    }

    private int getrandomint(final int bound) {
        return bound > 1 ? rand.nextInt(bound) : 0;
    }
}