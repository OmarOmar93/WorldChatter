package me.omaromar93.wcbungee.Parent;

import WorldChatterCore.Packets.Injectable;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import me.omaromar93.wcbungee.WCBungee;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.netty.PipelineUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public final class BungeePlayer extends Injectable implements Player {

    private final String firstServer;
    private final ProxiedPlayer player;

    public BungeePlayer(final ProxiedPlayer player, final String firstServer) {
        super(((UserConnection) player).getCh().getHandle(), PipelineUtils.PACKET_DECODER, player.getName(), player.getPendingConnection().getVersion());

        this.player = player;
        this.firstServer = firstServer;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public void sendMessage(final String message) {
        if (!message.isEmpty())
            WCBungee.adventure.player(player).sendMessage(MiniMessage.miniMessage().deserialize(message));
    }

    @Override
    public boolean hasPermission(final String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void playSound(final String soundName, final float volume, final float pitch) {
        sendSoundPacket(soundName, volume, pitch);
    }

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public Player getPlayer() {
        return PlayerHandler.INSTANCE.getPlayerUUID(player.getUniqueId());
    }

    @Override
    public String getPlace() {
        return player.getServer() == null && firstServer != null
                ? firstServer
                : Stream.of(player.getServer())
                .filter(Objects::nonNull)
                .map(server -> server.getInfo().getName())
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }
}