package me.omaromar93.worldchatter.Legacy;

import UniversalFunctions.CommandSender;
import UniversalFunctions.LegacyChatColor;
import org.bukkit.entity.Player;

public class LegacySpigotCommandSender implements CommandSender {

    private final org.bukkit.command.CommandSender sender;

    public LegacySpigotCommandSender(final org.bukkit.command.CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean isConsole() {
        return !(sender instanceof Player);
    }

    @Override
    public void sendMessage(final String message) {
        if(!message.isEmpty()) sender.sendMessage(LegacyChatColor.translateAlternateColorCodes('&',message));
    }

    @Override
    public boolean hasPermission(final String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public String getName() {
        return sender.getName();
    }
}
