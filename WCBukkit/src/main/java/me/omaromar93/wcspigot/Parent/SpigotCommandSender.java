package me.omaromar93.wcspigot.Parent;

import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Features.MiniMessageConnector;
import WorldChatterCore.Players.PlayerHandler;
import me.omaromar93.wcspigot.WCBukkit;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public final class SpigotCommandSender implements CommandSender {

    private final org.bukkit.command.CommandSender sender;


    public SpigotCommandSender(final org.bukkit.command.CommandSender sender) {
        this.sender = sender;
    }


    @Override
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    @Override
    public void sendMessage(final String message) {
        if (!message.isEmpty()) {
            if (WCBukkit.adventure != null) {
                WCBukkit.adventure.sender(
                                sender)
                        .sendMessage(MiniMessage.miniMessage().deserialize(MiniMessageConnector.INSTANCE.returnFormattedString(message)));
                return;
            }
            sender.sendMessage(message);
        }
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
    public WorldChatterCore.Players.Player getPlayer() {
        return PlayerHandler.INSTANCE.getPlayerUUID(((Player) sender).getUniqueId());
    }

}