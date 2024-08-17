package WorldChatterCore.Features;

import WorldChatterCore.API.Addon;
import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Players.Player;
import WorldChatterCore.Players.PlayerHandler;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;
import WorldChatterCore.Systems.ThreadsSystem;
import WorldChatterCore.Systems.UpdateSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {

    public static Command INSTANCE;

    private final List<String> cleaner = new ArrayList<>();

    private final List<String> helpMessages = Arrays.asList(
            ColorSystem.WHITE + "- " + ColorSystem.GREEN + "WorldChatter Help List " + ColorSystem.WHITE + "-",
            ColorSystem.BLUE + "- wc Lock" + ColorSystem.WHITE + " Toggles the ability to chat in the server (Lock status: " + (ChatLock.INSTANCE.isLocked() ? ColorSystem.RED + "Locked" : ColorSystem.GREEN + "UnLocked") + ColorSystem.WHITE + ")",
            ColorSystem.BLUE + "- wc update" + ColorSystem.WHITE + " Checks for any available updates for the plugin",
            ColorSystem.BLUE + "- wc reload" + ColorSystem.WHITE + " Reloads the plugin's configuration",
            ColorSystem.BLUE + "- wc addons" + ColorSystem.WHITE + " Check the connected Addons in WorldChatter!",
            ColorSystem.BLUE + "- wc clear" + ColorSystem.WHITE + " Clears the chat!",
            ColorSystem.BLUE + "- wc broadcast [message]" + ColorSystem.WHITE + " Broadcast a message to every single place",
            ColorSystem.BLUE + "- wc version" + ColorSystem.WHITE + " Shows the version/Information about WorldChatter!"
    );


    public Command() {
        INSTANCE = this;
        for (int i = 0; i < 100; i++) {
            cleaner.add("§f                                            \n");
        }
    }


    public void executeCommand(final CommandSender sender, final String[] args) {
        ThreadsSystem.runAsync(() -> {
            if (sender.hasPermission("worldchatter.control")) {
                if (args.length > 0) {
                    switch (args[0].toLowerCase()) {
                        case "reload":
                        case "r":
                            ConfigSystem.INSTANCE.update();
                            sender.sendMessage(ColorSystem.GREEN + "Reloaded the WorldChatter's Configuration!");
                            for(WCListener listener: WCA.INSTANCE.getListeners()) {
                                listener.senderConfigReload(sender);
                            }
                            return;
                        case "version":
                        case "info":
                        case "v":
                        case "i":
                            sender.sendMessage(ColorSystem.GRAY + "- " + ColorSystem.YELLOW + "WorldChatter PREVIEW" + ColorSystem.GRAY + " - " + ColorSystem.GREEN + MainPluginConnector.INSTANCE.getWorldChatter().getVersion());
                            sender.sendMessage(ColorSystem.YELLOW + "Created By: OmarOmar93");
                            sender.sendMessage("Update Title: " + ColorSystem.GOLD + "WorldChatter 3");
                            return;
                        case "help":
                        case "commands":
                        case "h":
                            for (String msg : helpMessages) {
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
                            return;
                        case "addons":
                        case "a":
                            for(Addon addon: WCA.INSTANCE.getAddons()) {
                                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GRAY + "-> " + ColorSystem.RESET + addon.getName());
                                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GREEN + "Author(s): " + ColorSystem.YELLOW + addon.getAuthor());
                                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GREEN + "Version: " + ColorSystem.YELLOW + addon.getVersion());
                                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.RESET + addon.getDescription());
                                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GRAY + "----------------------------------------");
                            }
                            return;
                        case "clear":
                        case "clearchat":
                        case "cc":
                            if (!sender.isPlayer() || ConfigSystem.INSTANCE.getPlace().getBoolean("GlobalChat"))
                                MainPluginConnector.INSTANCE.getWorldChatter().broadcastMessage(String.join("\n", cleaner)
                                        + '\n' + ColorSystem.tCC(ConfigSystem.INSTANCE.getMessages().getString("ChatClearMessage")
                                        .replace("%sender%", sender.getName())));
                            else {
                                for (Player player : PlayerHandler.INSTANCE.getPlayersFromPlace(sender.getPlayer().getPlace()).values()) {
                                    player.sendMessage(String.join("\n", cleaner)
                                            + '\n' + ColorSystem.tCC(ConfigSystem.INSTANCE.getMessages().getString("ChatClearMessage")
                                            .replace("%sender%", sender.getName())));
                                }
                            }
                            sender.sendMessage(ColorSystem.GREEN + "Successfully cleared Chat!");
                            return;
                    }
                    return;
                }
                for (String msg : helpMessages) {
                    sender.sendMessage(msg);
                }
                return;
            }
            sender.sendMessage(ColorSystem.tCC(ConfigSystem.INSTANCE.getMessages().getString("NoPermissionMessage")));
        });
    }
}