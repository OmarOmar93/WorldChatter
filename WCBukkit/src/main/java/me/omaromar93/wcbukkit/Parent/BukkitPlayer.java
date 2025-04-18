package me.omaromar93.wcbukkit.Parent;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import me.omaromar93.wcbukkit.WCBukkit;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;

import java.util.UUID;

public final class BukkitPlayer implements WorldChatterCore.Players.Player {

    private final org.bukkit.entity.Player player;

    public BukkitPlayer(final org.bukkit.entity.Player player) {
        this.player = player;
    }


    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public void sendMessage(final String message) {
        if (!message.isEmpty()) {
            if (WCBukkit.adventure != null) {
                WCBukkit.adventure.player(player.getUniqueId()).sendMessage(MiniMessage.miniMessage().deserialize(message));
                return;
            }
            player.sendMessage(message);
        }
    }

    @Override
    public boolean hasPermission(final String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void playSound(final String soundName, final float volume, final float pitch) {
        try {
            player.playSound(player.getLocation(), Sound.valueOf(soundName), volume, pitch);
        } catch (final NoClassDefFoundError | Exception ignored) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.YELLOW + "The sound can't be find try checking the config for more information!");
        }
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
         return WCBukkit.mvcore != null ? WCBukkit.mvcore.getMVWorldManager().getMVWorld(player.getWorld().getName()).getAlias() : player.getWorld().getName();
    }

    @Override
    public String getRawPlace() {
        return player.getWorld().getName();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    @Override
    public String getIP() {
        return player.getAddress().getHostName();
    }

    @Override
    public void kick(String reason) {
        if (!reason.isEmpty()) {
            Bukkit.getScheduler().runTask(WCBukkit.INSTANCE, () -> player.kickPlayer(reason));
        }
    }

}
