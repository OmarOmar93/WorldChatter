package me.omaromar93.wcbungee.Parent;

import net.md_5.bungee.api.CommandSender;

public final class Command extends net.md_5.bungee.api.plugin.Command {

    public Command() {
        super("worldchatter", "worlcchatter.control", "wc"); // Command name
    }


    @Override
    public void execute(final CommandSender sender, final String[] args) {
        WorldChatterCore.Features.Command.INSTANCE.executeCommand(new BungeeCommandSender(sender), args);
    }
}
