package WorldChatterCore.Features;

import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

public class ChatLock {

    private boolean locked;
    private boolean enabled;
    private boolean global;

    public static ChatLock INSTANCE;

    private String lockedMessage, unlockedMessage;

    public ChatLock() {
        INSTANCE = this;
    }

    public void update() {
        enabled = ConfigSystem.INSTANCE.getPlace().getBoolean("ChatLockMessage.enabled");
        global = ConfigSystem.INSTANCE.getPlace().getBoolean("ChatLockMessage.public");
        if (enabled && global) {
            lockedMessage = ConfigSystem.INSTANCE.getPlace().getString("ChatLockMessage.locked");
            unlockedMessage = ConfigSystem.INSTANCE.getPlace().getString("ChatLockMessage.unlocked");
        }
    }


    public void toggleLocked(final CommandSender sender) {
        if (enabled) {
            locked = !locked;
            if (global) {
                MainPluginConnector.INSTANCE.getWorldChatter()
                        .broadcastMessage(PlaceHolders.applyPlaceHoldersifPossible(ColorSystem.tCC(locked ? lockedMessage : unlockedMessage).replace("%sender%", sender.getName()), null));
            }
            return;
        }
        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "You cannot toggle while it's disabled in the config!");
        for (WCListener listener : WCA.INSTANCE.getListeners()) {
            listener.chatLockToggle(sender);
        }
    }

    public boolean isLocked() {
        return ConfigSystem.INSTANCE.getPlace().getBoolean("ChatLockMessage.enabled") && locked;
    }
}