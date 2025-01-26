package me.omaromar93.wcvelocity.Parent;

import WorldChatterCore.Packets.Injectable;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import com.velocitypowered.proxy.connection.client.ConnectedPlayer;
import com.velocitypowered.proxy.network.Connections;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public final class VelocityPlayer extends Injectable implements WorldChatterCore.Players.Player {

    private final String firstServer;
    private final com.velocitypowered.api.proxy.Player player;

    public VelocityPlayer(final com.velocitypowered.api.proxy.Player player, final String firstServer) {
        super(((ConnectedPlayer) player).getConnection().getChannel(), Connections.HANDLER, player.getUsername(), player.getProtocolVersion().getProtocol());

        this.player = player;
        this.firstServer = firstServer;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public void sendMessage(final String message) {
        if (!message.isEmpty()) player.sendMessage(MiniMessage.miniMessage().deserialize(message));
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
        return player.getUsername();
    }

    @Override
    public Player getPlayer() {
        return PlayerHandler.INSTANCE.getPlayerUUID(player.getUniqueId());
    }

    public com.velocitypowered.api.proxy.Player getVelocityPlayer() {
        return player;
    }

    @Override
    public String getPlace() {
        return player.getCurrentServer().isEmpty() && firstServer != null
                ? firstServer
                : Stream.of(player.getCurrentServer().orElse(null))
                .filter(Objects::nonNull)
                .map(serverConnection -> serverConnection.getServerInfo().getName())
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getRawPlace() {
        return getPlace();
    }

    @Override
    public String getDisplayName() {
        return player.getUsername();
    }

    @Override
    public void kick(String reason) {
        player.disconnect(MiniMessage.miniMessage().deserialize(reason));
    }
}
