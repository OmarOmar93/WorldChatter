package me.omaromar93.worldchatterbungee;

import API.APICore;
import API.Addon;
import API.WorldChatterAPI;
import Others.ConfigSystem;
import Others.ThreadsSystem;
import Others.UpdaterSystem;
import chatting.ChattingSystem;
import functions.BungeeCommandSender;
import methods.Expression;
import methods.MoreFormat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class CommandSystem extends Command {

    final List<String> cleaner = new ArrayList<>();
    private final List<String> helpMessages = Arrays.asList(
            ChatColor.WHITE + "- " + ChatColor.GREEN + "WorldChatter Help List " + ChatColor.WHITE + "-",
            ChatColor.BLUE + "- wc Lock" + ChatColor.WHITE + " Toggles the ability to chat in the server (Lock status: " + (ChattingSystem.isChatLock() ? ChatColor.RED + "Locked" : ChatColor.GREEN + "UnLocked") + ChatColor.WHITE + ")",
            ChatColor.BLUE + "- wc update" + ChatColor.WHITE + " Checks for any available updates for the plugin",
            ChatColor.BLUE + "- wc reload" + ChatColor.WHITE + " Reloads the plugin's configuration",
            ChatColor.BLUE + "- wc addons" + ChatColor.WHITE + " Check the connected Addons in WorldChatter!",
            ChatColor.BLUE + "- wc clear" + ChatColor.WHITE + " Clears the chat!",
            ChatColor.BLUE + "- wc broadcast [message]" + ChatColor.WHITE + " Broadcast a message to every single world (not for the blacklist tho)",
            ChatColor.BLUE + "- wc version" + ChatColor.WHITE + " Shows the version/Information about WorldChatter!"
    );

    public CommandSystem() {
        super("worldchatter", "worlcchatter.control", "wc"); // Command name
        while (cleaner.size() < 100) {
            cleaner.add("§f                                            \n");
        }
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        ThreadsSystem.runAsync(() -> {
            final UniversalFunctions.CommandSender sender = new BungeeCommandSender(commandSender);
            if (sender.hasPermission("worldchatter.control")) {
                if (args.length > 0) {
                    switch (args[0].toLowerCase()) {
                        case "reload":
                            ConfigSystem.INSTANCE.update();
                            ConfigSystem.INSTANCE.updatePlayerEvent();
                            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                                api.configReload(sender, commandSender);
                            sender.sendMessage(ChatColor.GREEN + "Reloaded the WorldChatter's Configuration!");
                            return;
                        case "update":
                            final Boolean b = UpdaterSystem.isUpdated();
                            if (b == null) {
                                sender.sendMessage(ChatColor.RED + "Error has occurred while fetching the update.");
                                return;
                            }
                            if (b) {
                                sender.sendMessage(ChatColor.YELLOW + "WorldChatter has released a new" + (UpdaterSystem.isDev ? ChatColor.GOLD + " DEVELOPMENT" : "") + ChatColor.YELLOW + " update! " + ChatColor.GRAY + "( " + ChatColor.GOLD + UpdaterSystem.updatetitle + ChatColor.GRAY + " )" + ChatColor.WHITE + " -> " + ChatColor.GREEN + UpdaterSystem.newupdate + ChatColor.BLUE + "\nDownload the update at https://www.spigotmc.org/resources/worldchatter.101226/");
                                return;
                            }
                            sender.sendMessage(ChatColor.YELLOW + "WorldChatter is in it's latest update!");
                            return;
                        case "lock":
                            if (!ConfigSystem.INSTANCE.getSecurity().getBoolean("ChatLock")) {
                                sender.sendMessage(ChatColor.YELLOW + "The " + ChatColor.BLUE + "'ChatLock' " + ChatColor.YELLOW + "is disabled, toggling it won't do anything");
                                return;
                            }
                            final boolean lock = ChattingSystem.toggleChatLock();
                            final String s = Expression.translateColors(!lock ? Objects.requireNonNull(ConfigSystem.INSTANCE.getSecurity().get("ChatLockMessage.unlocked")).toString() : Objects.requireNonNull(ConfigSystem.INSTANCE.getSecurity().get("ChatLockMessage.locked")).toString());
                            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                                api.chatLockToggle(sender, lock, commandSender);
                            if (ConfigSystem.INSTANCE.getSecurity().getBoolean("ChatLockMessage.public"))
                                for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers())
                                    player.sendMessage(MoreFormat.FormatMore(s));
                            sender.sendMessage(ChatColor.YELLOW + "Successfully Toggled the Chat: " + (lock ? ChatColor.RED + "LOCKED" : ChatColor.GREEN + "UNLOCKED"));
                            return;
                        case "addons":
                            sender.sendMessage("\n" + ChatColor.WHITE + "- " + ChatColor.GREEN + "WorldChatter Addons | " + APICore.INSTANCE.getAddons().size() + ChatColor.WHITE + " -\n");
                            for (final Addon addon : APICore.INSTANCE.getAddons()) {
                                sender.sendMessage(ChatColor.YELLOW + "-> " + addon.getName() + " by " + String.join(",", addon.getAuthors()) + "\n" + ChatColor.WHITE + "- " + addon.getDescription());
                            }
                            return;
                        case "broadcast":
                        case "bc":
                            if (args.length > 1) {
                                final StringBuilder builder = new StringBuilder();
                                for (int i = 1; i < args.length; i++) {
                                    builder.append(args[i]).append(" ");
                                }
                                String message = builder.toString();
                                if (ConfigSystem.INSTANCE.getTexts().getBoolean("texts.enabled", false))
                                    message = Expression.replaceIt(sender, message);
                                if (ConfigSystem.INSTANCE.getConfig().getBoolean("ColoredText", false)) {
                                    message = Expression.translateColors(message);
                                }
                                sender.sendMessage(ChatColor.GREEN + "Successfully sent! >>> " + ChatColor.WHITE + message);
                                for (final ServerInfo server : ProxyServer.getInstance().getServers().values()) {
                                    if (!ConfigSystem.INSTANCE.getConfig().getStringList("BlackListPlaces").contains(server.getName())) {
                                        for (final ProxiedPlayer player : server.getPlayers()) {
                                            if (sender.getName().equals(player.getName())) {
                                                continue;
                                            }
                                            final TextComponent textComponent = MoreFormat.FormatMore(message);
                                            player.sendMessage(textComponent);
                                        }
                                    }
                                }
                            }
                            return;
                        case "cc":
                        case "clearchat":
                        case "clear":
                            for (final ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                                player.sendMessage(new TextComponent(String.join(" ", cleaner)));
                                player.sendMessage(MoreFormat.FormatMore(Expression.translateColors(Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("ChatClearMessage")).toString()
                                        .replace("%sender%", sender.getName()))));
                            }
                            sender.sendMessage(Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("ChatClearMessage")).toString());
                            return;
                        case "version":
                        case "info":
                            sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.YELLOW + "WorldChatter" + ChatColor.GRAY + " - " + ChatColor.GREEN + WorldChatterBungee.INSTANCE.getDescription().getVersion());
                            sender.sendMessage(ChatColor.YELLOW + "Created By: OmarOmar93");
                            sender.sendMessage("Update Title: " + ChatColor.GOLD + "The Quality Update");
                            return;
                        case "help":
                            for (String msg : helpMessages) {
                                sender.sendMessage(msg);
                            }
                            return;
                    }
                    sender.sendMessage(ChatColor.RED + "- INVALID ARGUMENT" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Type 'wc help' to check for available list!");
                } else {
                    for (String msg : helpMessages) {
                        sender.sendMessage(msg);
                    }
                }
            } else {
                String message = Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("NoPermissionMessage")).toString();
                message = Expression.translateColors(message);
                commandSender.sendMessage(MoreFormat.FormatMore(message));
            }
        });
    }
}