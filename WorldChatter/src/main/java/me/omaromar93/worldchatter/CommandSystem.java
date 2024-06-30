package me.omaromar93.worldchatter;

import API.APICore;
import API.Addon;
import API.WorldChatterAPI;
import Others.ConfigSystem;
import Others.PlayerSystem;
import Others.ThreadsSystem;
import Others.UpdaterSystem;
import UniversalFunctions.LegacyChatColor;
import chatting.ChattingSystem;
import me.omaromar93.worldchatter.Legacy.LegacySpigotCommandSender;
import me.omaromar93.worldchatter.functions.SpigotCommandSender;
import methods.Expression;
import methods.MoreFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class CommandSystem implements CommandExecutor {

    private final List<String> cleaner = new ArrayList<>();

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
        for (int i = 0; i < 100; i++) {
            cleaner.add("§f                                            \n");
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        ThreadsSystem.runAsync(() -> {
            UniversalFunctions.CommandSender sender;
            try {
                sender = new SpigotCommandSender(commandSender);
            } catch (final NoClassDefFoundError ignored) {
                sender = new LegacySpigotCommandSender(commandSender);
            }

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
                            String s;
                            try {
                                s = Expression.translateColors(!lock ? Objects.requireNonNull(ConfigSystem.INSTANCE.getSecurity().get("ChatLockMessage.unlocked")).toString() : Objects.requireNonNull(ConfigSystem.INSTANCE.getSecurity().get("ChatLockMessage.locked")).toString())
                                        .replace("%sender%", sender.getName());
                            } catch (final NoClassDefFoundError ignored) {
                                s = LegacyChatColor.translateAlternateColorCodes('&', !lock ? Objects.requireNonNull(ConfigSystem.INSTANCE.getSecurity().get("ChatLockMessage.unlocked")).toString() : Objects.requireNonNull(ConfigSystem.INSTANCE.getSecurity().get("ChatLockMessage.locked")).toString())
                                        .replace("%sender%", sender.getName());
                            }
                            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                                api.chatLockToggle(sender, lock, commandSender);
                            if (ConfigSystem.INSTANCE.getSecurity().getBoolean("ChatLockMessage.public")) {
                                for (final UniversalFunctions.Player player : PlayerSystem.INSTANCE.getPlayers()) {
                                    if (!player.getName().equals(sender.getName()))
                                        player.sendMessage(s);
                                }
                            }
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
                                    try {
                                        message = Expression.translateColors(message);
                                    } catch (final NoClassDefFoundError ignored) {
                                        message = LegacyChatColor.translateAlternateColorCodes('&', message);
                                    }
                                }
                                sender.sendMessage(ChatColor.GREEN + "Successfully sent! >>> " + ChatColor.WHITE + message);
                                for (World world : Bukkit.getWorlds()) {
                                    if (!ConfigSystem.INSTANCE.getConfig().getStringList("BlackListPlaces").contains(world.getName())) {
                                        for (Player player : world.getPlayers()) {
                                            if (sender.getName().equals(player.getName())) {
                                                continue;
                                            }
                                            try {
                                                player.spigot().sendMessage(Objects.requireNonNull(MoreFormat.FormatMore(message)));
                                            } catch (final NoSuchMethodError ignored) {
                                                player.sendMessage(message);
                                            }
                                        }
                                    }
                                }
                            }
                            return;
                        case "cc":
                        case "clearchat":
                        case "clear":
                            String message;
                            try {
                                message = Expression.translateColors(Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("ChatClearMessage")).toString().replace("%sender%", sender.getName()));
                            } catch (final NoClassDefFoundError ignored) {
                                message = LegacyChatColor.translateAlternateColorCodes('&', (Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get("ChatClearMessage")).toString().replace("%sender%", sender.getName())));
                            }
                            for (final UniversalFunctions.Player player : PlayerSystem.INSTANCE.getPlayers()) {
                                player.sendMessage(String.join(" ", cleaner));
                                player.sendMessage(message);
                            }
                            sender.sendMessage(ChatColor.YELLOW + "Successfully Cleared the Chat!");
                            return;
                        case "version":
                        case "info":
                            sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.YELLOW + "WorldChatter" + ChatColor.GRAY + " - " + ChatColor.GREEN + WorldChatter.INSTANCE.getDescription().getVersion());
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
                try {
                    message = Expression.translateColors(message);
                } catch (final NoClassDefFoundError ignored) {
                    message = LegacyChatColor.translateAlternateColorCodes('&', message);
                }
                sender.sendMessage(message);
            }
        });
        return true;
    }
}