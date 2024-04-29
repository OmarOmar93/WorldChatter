package me.omaromar93.worldchatter.functions;

import UniversalFunctions.CommandSender;
import methods.MoreFormat;
import org.bukkit.entity.Player;

public class SpigotCommandSender implements CommandSender {

    private final org.bukkit.command.CommandSender sender;

    public SpigotCommandSender(final org.bukkit.command.CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean isConsole() {
        return !(sender instanceof Player);
    }

    @Override
    public void sendMessage(String message) {
        sender.spigot().sendMessage(MoreFormat.FormatMore(message));
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public String getName() {
        return sender.getName();
    }
}
