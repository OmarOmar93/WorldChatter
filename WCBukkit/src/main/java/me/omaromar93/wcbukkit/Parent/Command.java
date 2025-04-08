package me.omaromar93.wcbukkit.Parent;


import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class Command implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        WorldChatterCore.Features.Command.INSTANCE.executeCommand(new BukkitCommandSender(sender), args);
        return true;
    }
}