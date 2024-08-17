package me.omaromar93.wcspigot.Parent;


import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        WorldChatterCore.Features.Command.INSTANCE.executeCommand(new SpigotCommandSender(sender), args);
        return true;
    }
}