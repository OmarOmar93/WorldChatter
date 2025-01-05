package WorldChatterCore.Features;

import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.ConfigSystem;

public final class ChatLock {

    private boolean locked;

    public static ChatLock INSTANCE;
    private boolean global;

    private String lockedMessage, unlockedMessage;

    public ChatLock() {
        INSTANCE = this;
    }

    public void update() {
        global = ConfigSystem.INSTANCE.getPlace().getBoolean("ChatLockMessage.public");
        if (ConfigSystem.INSTANCE.getPlace().getBoolean("ChatLockMessage.enabled") && global) {
            lockedMessage = ConfigSystem.INSTANCE.getPlace().getString("ChatLockMessage.locked");
            unlockedMessage = ConfigSystem.INSTANCE.getPlace().getString("ChatLockMessage.unlocked");
            return;
        }
        lockedMessage = null;
        unlockedMessage = null;

    }


    public void toggleLocked(final CommandSender sender) {
        if (lockedMessage != null && unlockedMessage != null) {
            locked = !locked;
            if (global) {
                MainPluginConnector.INSTANCE.getWorldChatter()
                        .broadcastMessage(ColorSystem.tCC(PlaceHolders.applyPlaceHoldersifPossible((locked ? lockedMessage : unlockedMessage).replace("{sender}", sender.getName()), null)));
            }
            return;
        }
        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "You cannot toggle while it's disabled in the config!");
        if(WCA.INSTANCE != null) for (final WCListener listener : WCA.INSTANCE.getListeners()) {
            listener.chatLockToggle(sender);
        }
    }

    public boolean isLocked() {
        return ConfigSystem.INSTANCE.getPlace().getBoolean("ChatLockMessage.enabled") && locked;
    }
}