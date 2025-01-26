package WorldChatterCore.Features;


import WorldChatterCore.API.Addon;
import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.Channels.ChannelManager;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Others.ServerOptions;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;
import WorldChatterCore.Systems.ThreadsSystem;
import WorldChatterCore.Systems.UpdateSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Command {

    public static Command INSTANCE;

    private static final List<String> cleaner = new ArrayList<>();
    static {
        for (int i = 0; i < 100; i++) {
            cleaner.add("§f                                            \n");
        }
    }

    private final List<String> helpMessages = Arrays.asList(
            ColorSystem.WHITE + "- " + ColorSystem.GREEN + "WorldChatter Help List " + ColorSystem.WHITE + "-",
            ColorSystem.BLUE + "- wc Lock" + ColorSystem.WHITE + " Toggles the ability to chat in the server (Lock status: " + (ChatLock.INSTANCE.isLocked() ? ColorSystem.RED + "Locked" : ColorSystem.GREEN + "Unlocked") + ColorSystem.WHITE + ")",
            ColorSystem.BLUE + "- wc update" + ColorSystem.WHITE + " Checks for any available updates for the plugin",
            ColorSystem.BLUE + "- wc reload" + ColorSystem.WHITE + " Reloads the plugin's configuration",
            ColorSystem.BLUE + "- wc addons" + ColorSystem.WHITE + " Check the connected Addons in WorldChatter!",
            ColorSystem.BLUE + "- wc clear" + ColorSystem.WHITE + " Clears the chat!",
            ColorSystem.BLUE + "- wc version" + ColorSystem.WHITE + " Shows the version/Information about WorldChatter!"
    );

    public Command() {
        INSTANCE = this;
    }

    /**
     * Executes the command for all softwares
     * @param sender the command sender
     * @param args the arguments
     */
    public void executeCommand(final CommandSender sender, final String[] args) {
        ThreadsSystem.runAsync(() -> {
            if (sender.hasPermission("worldchatter.control")) {
                if (args.length > 0) {
                    switch (args[0].toLowerCase()) {
                        case "reload":
                        case "r":
                            ConfigSystem.INSTANCE.update();
                            sender.sendMessage(ColorSystem.GREEN + "Reloaded the WorldChatter's Configuration!");
                            if(WCA.INSTANCE != null) for(final WCListener listener: WCA.INSTANCE.getListeners()) {
                                listener.senderConfigReload(sender);
                            }
                            return;
                        case "version":
                        case "info":
                        case "v":
                        case "i":
                            sender.sendMessage(ColorSystem.GRAY + "- " + ColorSystem.YELLOW + "WorldChatter" + ColorSystem.GRAY + " - " + ColorSystem.GREEN + MainPluginConnector.INSTANCE.getWorldChatter().getVersion() + ColorSystem.GRAY + " (" + UpdateSystem.INSTANCE.getCurrentBuild() + ")");
                            sender.sendMessage(ColorSystem.YELLOW + "Created By: OmarOmar93");
                            sender.sendMessage("Update Title: " + ColorSystem.GOLD + "The Channels Update - QOL Part 1");
                            return;
                        case "help":
                        case "commands":
                        case "h":
                        case "c":
                            for (final String msg : helpMessages) {
                                sender.sendMessage(msg);
                            }
                            return;
                        case "lock":
                        case "l":
                            ChatLock.INSTANCE.toggleLocked(sender);
                            return;
                        case "update":
                        case "u":
                            UpdateSystem.INSTANCE.messageCheck(sender);
                            UpdateSystem.INSTANCE.checkForAddonsUpdates(sender);
                            return;
                        case "addons":
                        case "a":
                            if(WCA.INSTANCE != null) {
                                for (final Addon addon : WCA.INSTANCE.getAddons()) {
                                    sender.sendMessage(ColorSystem.GRAY + "-> " + ColorSystem.RESET + addon.getName());
                                    sender.sendMessage(ColorSystem.GREEN + "Author(s): " + ColorSystem.YELLOW + addon.getAuthor());
                                    sender.sendMessage(ColorSystem.GREEN + "Version: " + ColorSystem.YELLOW + addon.getVersion() + ((addon.getBuild() != null) ? ColorSystem.GRAY + " (" + addon.getBuild() + ")" : ""));
                                    sender.sendMessage(ColorSystem.RESET + addon.getDescription());
                                    sender.sendMessage(ColorSystem.GRAY + "----------------------------------------");
                                }
                                return;
                            }
                            sender.sendMessage(ColorSystem.YELLOW + "WorldChatter Add-on Service isn't activated! " + ColorSystem.GRAY + "(No Addons were found)");
                            return;
                        case "clear":
                        case "clearchat":
                        case "cc":
                            final String clear = (MiniMessageConnector.INSTANCE == null ? String.join("\n", cleaner)
                                    + '\n' + ColorSystem.tCC(ConfigSystem.INSTANCE.getMessages().getString("ChatClearMessage"))
                                    : MiniMessageConnector.INSTANCE.returnFormattedString(String.join("\n", cleaner)
                                    + '\n' + ColorSystem.tCC(ConfigSystem.INSTANCE.getMessages().getString("ChatClearMessage"))))
                                    .replace("{sender}", sender.getName() == null ? "SERVER" : sender.getName());
                            if (!sender.isPlayer() || ChannelManager.INSTANCE.isGlobalSending()) {
                                MainPluginConnector.INSTANCE.getWorldChatter().broadcastMessage(clear);
                            } else {
                                for (final Player player : ServerOptions.INSTANCE.getPlayersinPlace(sender.getPlayer().getRawPlace())) {
                                    player.sendMessage(clear);
                                }
                            }
                            sender.sendMessage(ColorSystem.GREEN + "Successfully cleared Chat!");
                            return;
                    }
                    return;
                }
                for (final String msg : helpMessages) {
                    sender.sendMessage(msg);
                }
                return;
            }
            sender.sendMessage(ColorSystem.tCC(ConfigSystem.INSTANCE.getMessages().getString("NoPermissionMessage")));
        });
    }
}