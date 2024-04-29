package me.omaromar93.worldchatter;

import API.APICore;
import API.Addon;
import API.WorldChatterAPI;
import Others.ConfigSystem;
import Others.ThreadsSystem;
import Others.UpdaterSystem;
import chatting.ChattingSystem;
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

public final class CommandSystem implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        ThreadsSystem.runAsync(() -> {
            final UniversalFunctions.CommandSender sender = new SpigotCommandSender(commandSender);
            if (args.length > 0) {
                if (sender.hasPermission("worldchatter.control")) {
                    switch (args[0].toLowerCase()) {
                        case "reload":
                            ConfigSystem.INSTANCE.update();
                            sender.sendMessage(ChatColor.GREEN + "Reloaded the WorldChatter's Configuration!");
                            return;
                        case "update":
                            Boolean b = UpdaterSystem.isUpdated();
                            if (b == null) {
                                sender.sendMessage(ChatColor.RED + "Error has occurred while fetching the update.");
                                return;
                            }
                            if (b) {
                                sender.sendMessage(ChatColor.YELLOW + "WorldChatter has released a new update! " + ChatColor.WHITE + "-> " + ChatColor.GREEN + UpdaterSystem.newupdate + ChatColor.BLUE + "\nDownload the update at https://www.spigotmc.org/resources/worldchatter.101226/");
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
                            final String s = !lock ? ChatColor.YELLOW + "The chat is now " + ChatColor.GREEN + "OPENED" : ChatColor.YELLOW + "The chat is now " + ChatColor.RED + "CLOSED";
                            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                                api.chatLockToggle(sender, lock);
                            sender.sendMessage(s);
                            return;
                        case "addons":
                            sender.sendMessage("\n" + ChatColor.WHITE + "- " + ChatColor.GREEN + "WorldChatter Addons | " + APICore.INSTANCE.getAddons().size() + ChatColor.WHITE + " -\n");
                            for (final Addon addon : APICore.INSTANCE.getAddons()) {
                                sender.sendMessage(ChatColor.YELLOW + "-> " + addon.getName() + " by " + String.join(",", addon.getAuthors()) + "\n" + ChatColor.WHITE + "- " + addon.getDescription());
                            }
                            return;
                        case "bc":
                        case "broadcast":
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
                                for (World world : Bukkit.getWorlds()) {
                                    if (!ConfigSystem.INSTANCE.getConfig().getStringList("BlackListPlaces").contains(world.getName())) {
                                        for (Player player : world.getPlayers()) {
                                            if (sender.getName().equals(player.getName())) {
                                                continue;
                                            }
                                            player.spigot().sendMessage(MoreFormat.FormatMore(message));
                                        }
                                    }
                                }
                            }
                            return;
                        case "help":
                            sender.sendMessage("\n" + ChatColor.WHITE + "- " + ChatColor.GREEN + "WorldChatter Help List " + ChatColor.WHITE + "-\n"
                                    + ChatColor.BLUE + "- wc Lock" + ChatColor.WHITE + " Toggles the ability to chat in the server (Lock status: " + (ChattingSystem.isChatLock() ? ChatColor.RED + "Locked" : ChatColor.GREEN + "UnLocked") + ChatColor.WHITE + ")" + "\n"
                                    + ChatColor.BLUE + "- wc update" + ChatColor.WHITE + " Checks for any available updates for the plugin" + "\n"
                                    + ChatColor.BLUE + "- wc reload" + ChatColor.WHITE + " Reloads the plugin's configuration" + "\n"
                                    + ChatColor.BLUE + "- wc addons" + ChatColor.WHITE + " Check the connected Addons in WorldChatter!" + "\n"
                                    + ChatColor.BLUE + "- wc broadcast" + ChatColor.WHITE + " Broadcast a message to every single world (not for the blacklist tho)" + "\n");
                            return;
                    }
                    sender.sendMessage(ChatColor.RED + "- INVALID ARGUMENT" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Type 'wc help' to check for available list!");
                }
            }
        });
        return true;
    }
}