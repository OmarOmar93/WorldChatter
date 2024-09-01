package me.omaromar93.wcvelocity.Parent;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.proxy.connection.client.ConnectedPlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.intellij.lang.annotations.Subst;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public final class VelocityPlayer implements WorldChatterCore.Players.Player {

    private final String firstServer;
    private final com.velocitypowered.api.proxy.Player player;

    public VelocityPlayer(final com.velocitypowered.api.proxy.Player player, final String firstServer) {
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
    public void playSound(@Subst("") final String soundName, final float volume, final float pitch) {
        ((ConnectedPlayer) player).getConnection().write(new NamedSo);
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
    public String getDisplayName() {
        return player.getUsername();
    }

}
