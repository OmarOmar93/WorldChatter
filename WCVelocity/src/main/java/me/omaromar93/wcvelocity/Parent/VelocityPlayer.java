package me.omaromar93.wcvelocity.Parent;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.intellij.lang.annotations.Subst;

import java.util.UUID;

public class VelocityPlayer implements WorldChatterCore.Players.Player {

    private final com.velocitypowered.api.proxy.Player player;


    public VelocityPlayer(final com.velocitypowered.api.proxy.Player player) {
        this.player = player;
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
        player.playSound(Sound.sound(Key.key(soundName), Sound.Source.MASTER, volume, pitch));
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

    @Override
    public String getPlace() {
        return player.getCurrentServer().get().getServerInfo().getName();
    }

    @Override
    public String getDisplayName() {
        return player.getUsername();
    }

}
