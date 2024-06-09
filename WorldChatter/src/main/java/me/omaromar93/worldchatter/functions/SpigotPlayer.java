package me.omaromar93.worldchatter.functions;

import UniversalFunctions.UniLogHandler;
import methods.MoreFormat;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayer implements UniversalFunctions.Player {

    private final Player player;

    public SpigotPlayer(final Player player) {
        this.player = player;
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public void sendMessage(final String message) {
        try {
            player.spigot().sendMessage(MoreFormat.FormatMore(message));
        } catch (final NoSuchMethodError ignored) {
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
        } catch (final Exception ignored) {
            UniLogHandler.INSTANCE.sendMessage(ChatColor.YELLOW + "The sound can't be find try checking the config for more information!");
        }
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getPlace() {
        return player.getWorld().getName();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }


}
