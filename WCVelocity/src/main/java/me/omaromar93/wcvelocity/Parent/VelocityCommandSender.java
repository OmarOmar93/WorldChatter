package me.omaromar93.wcvelocity.Parent;

import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.PlayerHandler;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class VelocityCommandSender implements CommandSender {

    final CommandSource sender;

    public VelocityCommandSender(final CommandSource sender) {
        this.sender = sender;
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    @Override
    public void sendMessage(final String message) {
        if (!message.isEmpty()) sender.sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
    }

    @Override
    public boolean hasPermission(final String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public String getName() {
        return sender instanceof Player ? ((Player) sender).getUsername() : null;
    }

    @Override
    public WorldChatterCore.Players.Player getPlayer() {
        return PlayerHandler.INSTANCE.getPlayerUUID(((Player) sender).getUniqueId());
    }
}
