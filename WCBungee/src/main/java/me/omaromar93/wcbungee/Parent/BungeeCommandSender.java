package me.omaromar93.wcbungee.Parent;

import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import me.omaromar93.wcbungee.WCBungee;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public final class BungeeCommandSender implements CommandSender {

    net.md_5.bungee.api.CommandSender sender;

    public BungeeCommandSender(final net.md_5.bungee.api.CommandSender sender) {
        this.sender = sender;
    }


    @Override
    public boolean isPlayer() {
        return sender instanceof ProxiedPlayer;
    }

    @Override
    public void sendMessage(final String message) {
        if (!message.isEmpty())
            WCBungee.adventure.sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
    }

    @Override
    public boolean hasPermission(final String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public String getName() {
        return sender.getName();
    }

    @Override
    public Player getPlayer() {
        return PlayerHandler.INSTANCE.getPlayerUUID(((ProxiedPlayer) sender).getUniqueId());
    }
}
