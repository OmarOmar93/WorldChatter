package me.omaromar93.wcvelocity.Parent;

import com.velocitypowered.api.command.SimpleCommand;

public final class Command implements SimpleCommand {
    @Override
    public void execute(final Invocation invocation) {
        WorldChatterCore.Features.Command.INSTANCE.executeCommand(new VelocityCommandSender(invocation.source()), invocation.arguments());
    }
}
