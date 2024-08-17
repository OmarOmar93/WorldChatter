package WorldChatterCore.Systems;

import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.Features.*;
import WorldChatterCore.Players.Player;

import java.util.List;

public class FeatureSystem {

    private final FeatureIterator featureIterator = new FeatureIterator();
    private final PlaceBlacklist worldBlacklist = new PlaceBlacklist();

    public static FeatureSystem INSTANCE;
    private String reason = null;


    public FeatureSystem(final Player player, final String message) {
        INSTANCE = this;
        executeMessage(player, message);
    }


    /*
    what it should do WITH PERM CHECKS
    - MESSAGE -
    - format the text replacement ✅
    - format the message colored ✅
    - format the usermention in the message ✅
    - format the new line in the message ✅
    - FORMAT -
    - add the format that is before the message with Chat Formatter ✅
    - replace the PlaceHolder with Built-in or PlaceHolderAPI's PlaceHolders ✅
    - add the MiniMessage format into the format ✅
    - Colors are automatic ✅
     */
    private void executeMessage(final Player player, final String message) {
        if (!messageApproved(player, message)) {
            if (reason != null) player.sendMessage(
                    PlaceHolders.applyPlaceHoldersifPossible(ColorSystem.tCC(reason),player));
            return;
        }
        FeatureIterator.INSTANCE.initalizeTheMessage(
                ChatFormatter.INSTANCE.createFormat(player),
                featureIterator.preparetheMessage(message, player), player
        );
    }


    /*
    what it should do. WITH PERM CHECKS
    - check if the chat is locked ✅
    - check if that place is from the blacklist ✅
    - check if it's still in the cooldown (anti-spam) ✅
    - check if it has the following (anti-Ads,anti-caps,anti-swear) ✅
     */
    private boolean messageApproved(final Player player, final String message) {
        if (!ChatLock.INSTANCE.isLocked() || player.hasPermission("worldchatter.bypass.chatlock")) {
            if (ConfigSystem.INSTANCE.getPlace().getBoolean("BlackList.enabled")
                    && worldBlacklist.isPlaceBlackListed(player.getPlace())) {
                return false;
            }
            if (AntiSpam.INSTANCE.getTimeLeft(player) != null && !player.hasPermission("worldchatter.bypass.antispam")) {
                reason = PlaceHolders.applyPlaceHoldersifPossible(
                        ConfigSystem.INSTANCE.getMessages().getString("SpamMessage")
                                .replace("%duration%", AntiSpam.INSTANCE.getTimeLeft(player))
                        , player);
                return false;
            } else {
                AntiSpam.INSTANCE.coolThatPlayerDown(player);
            }
            if (ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiADS")
                    || ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiCaps.enabled")
                    || ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiSwear.enabled")) {
                final List<String> flags = featureIterator.securityCheck(player, message);
                if (!flags.isEmpty()) {
                    Notifications.INSTANCE.alertStaffandPlayer(String.join(", ", flags), player, message);
                    for(WCListener listener: WCA.INSTANCE.getListeners()) {
                        listener.messageDetect(flags, player, message);
                    }
                    return false;
                }
            }
            return true;
        }
        reason = ConfigSystem.INSTANCE.getPlace().getString("ChatLockMessage.currently");
        return false;
    }
}