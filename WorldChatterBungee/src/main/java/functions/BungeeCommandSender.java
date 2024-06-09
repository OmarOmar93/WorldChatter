package functions;

import methods.MoreFormat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Objects;

public class BungeeCommandSender implements UniversalFunctions.CommandSender {

    CommandSender sender;

    public BungeeCommandSender(final CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean isConsole() {
        return !(sender instanceof ProxiedPlayer);
    }

    @Override
    public void sendMessage(final String message) {
        sender.sendMessage(new TextComponent(Objects.requireNonNull(MoreFormat.FormatMore(message))));
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
