package me.omaromar93.wcbungee.Parent;

import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import me.omaromar93.wcbungee.WCBungee;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.intellij.lang.annotations.Subst;

import java.util.UUID;

public class BungeePlayer implements Player {

    private final ProxiedPlayer player;


    public BungeePlayer(final ProxiedPlayer player) {
        this.player = player;
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
    public void playSound(@Subst("") final String soundName, final float volume, final float pitch) {
        WCBungee.adventure.player(player).playSound(Sound.sound(Key.key(soundName), Sound.Source.MASTER, volume,pitch));
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
        return player.getServer().getInfo().getName();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }
}