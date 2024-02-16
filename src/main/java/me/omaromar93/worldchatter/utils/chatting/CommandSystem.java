package me.omaromar93.worldchatter.utils.chatting;

import me.omaromar93.worldchatter.WorldChatter;
import me.omaromar93.worldchatter.utils.API.Addon;
import me.omaromar93.worldchatter.utils.API.WorldChatterAPI;
import me.omaromar93.worldchatter.utils.Others.ConfigSystem;
import me.omaromar93.worldchatter.utils.Others.ThreadsSystem;
import me.omaromar93.worldchatter.utils.Others.UpdaterSystem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandSystem implements CommandExecutor {

    public static boolean Chatlock = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ThreadsSystem.runAsync(() -> {
            if (sender.hasPermission("worldchatter.control") || !(sender instanceof Player)) {
                if (args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "reload":
                            ConfigSystem.updateConfig(sender);
                            sender.sendMessage(ChatColor.GREEN + "Reloaded the WorldChatter's Configuration!");
                            return;
                        case "update":
                            Boolean b = UpdaterSystem.isUpdated();
                            if (b == null) {
                                sender.sendMessage(ChatColor.RED + "Error has occurred while fetching the update.");
                                return;
                            }
                            if (b) {
                                sender.sendMessage(ChatColor.YELLOW + "WorldChatter has released a new update! " + ChatColor.GRAY + "-> " + ChatColor.GREEN + UpdaterSystem.newupdate + ChatColor.BLUE + "\nDownload the update at https://www.spigotmc.org/resources/worldchatter.101226/");
                                return;
                            }
                            sender.sendMessage(ChatColor.YELLOW + "WorldChatter is in it's latest update!");
                            return;
                        case "lock":
                            if (!ConfigSystem.getConfig().getBoolean("ChatLock")) {
                                sender.sendMessage(ChatColor.YELLOW + "The " + ChatColor.BLUE + "'ChatLock' " + ChatColor.YELLOW + "is disabled, toggling it won't do anything");
                                return;
                            }
                            final String s = Chatlock ? ChatColor.YELLOW + "The chat is now " + ChatColor.GREEN + "OPENED" : ChatColor.YELLOW + "The chat is now " + ChatColor.RED + "CLOSED";
                            Chatlock = !Chatlock;
                            for (final WorldChatterAPI api : WorldChatter.INSTANCE.getAPICore().getListeners())
                                api.chatLockToggle(sender, Chatlock);
                            sender.sendMessage(s);
                            return;
                        case "addons":
                            sender.sendMessage("\n" + ChatColor.GRAY + "- " + ChatColor.GREEN + "WorldChatter Addons | " + WorldChatter.INSTANCE.getAPICore().getAddons().size() + ChatColor.GRAY + " -\n");
                            for (final Addon addon : WorldChatter.INSTANCE.getAPICore().getAddons()) {
                                sender.sendMessage(ChatColor.YELLOW + "-> " + addon.getName() + " by " + String.join(",", addon.getAuthors()) + "\n" + ChatColor.GRAY + "- " + addon.getDescription());
                            }
                            return;
                        case "help":
                            sender.sendMessage("\n" + ChatColor.GRAY + "- " + ChatColor.GREEN + "WorldChatter Help List " + ChatColor.GRAY + "-\n"
                                    + ChatColor.BLUE + "- Lock" + ChatColor.WHITE + " Toggles the ability to chat in the server (Lock status: " + (Chatlock ? ChatColor.RED + "Locked" : ChatColor.GREEN + "UnLocked") + ChatColor.GRAY + ")" + "\n"
                                    + ChatColor.BLUE + "- Update" + ChatColor.WHITE + " Checks for any available updates for the plugin" + "\n"
                                    + ChatColor.BLUE + "- reload" + ChatColor.WHITE + " Reloads the plugin's configuration" + "\n"
                                    + ChatColor.BLUE + "- addons" + ChatColor.WHITE + " Check the connected Addons in WorldChatter!" + "\n");
                    }
                    return;
                }
                sender.sendMessage("\n" + ChatColor.GRAY + "- " + ChatColor.GREEN + "WorldChatter Help List " + ChatColor.GRAY + "-\n"
                        + ChatColor.BLUE + "- Lock" + ChatColor.WHITE + " Toggles the ability to chat in the server (Lock status: " + (Chatlock ? ChatColor.RED + "Locked" : ChatColor.GREEN + "UnLocked") + ChatColor.GRAY + ")" + "\n"
                        + ChatColor.BLUE + "- Update" + ChatColor.WHITE + " Checks for any available updates for the plugin" + "\n"
                        + ChatColor.BLUE + "- reload" + ChatColor.WHITE + " Reloads the plugin's configuration" + "\n"
                        + ChatColor.BLUE + "- addons" + ChatColor.WHITE + " Check the connected Addons in WorldChatter!" + "\n");
            }
        });
        return true;
    }
}