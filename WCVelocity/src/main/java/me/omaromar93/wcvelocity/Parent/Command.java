package me.omaromar93.wcvelocity.Parent;

import com.velocitypowered.api.command.SimpleCommand;

public class Command implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        WorldChatterCore.Features.Command.INSTANCE.executeCommand(new VelocityCommandSender(invocation.source()), invocation.arguments());
    }
}
