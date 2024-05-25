package methods;

import API.APICore;
import API.WorldChatterAPI;
import Others.ConfigSystem;
import Others.PlayerSystem;
import Others.SoundSystem;
import Others.ThreadsSystem;
import UniversalFunctions.ChatEvent;
import UniversalFunctions.LegacyChatColor;
import UniversalFunctions.Player;
import UniversalFunctions.UniLogHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class MethodHandler {

    public static void runMethodsOnMessage(final Player player, @NotNull String message, final ChatEvent event, final Boolean legacy) {
        if (ConfigSystem.INSTANCE.getTexts().getBoolean("texts.enabled", true))
            message = Expression.replaceIt(player, message);
        final List<String> list = detectMethods(message, player);
        if (!list.isEmpty()) {
            event.setCancelled(true);
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners()) {
                api.messageDetect(event, list, event.getOriginEvent());
            }
            System.gc();
            ThreadsSystem.runAsync(() -> sendToConsoleAndStaff(event, list, legacy));
            return;
        }
        if(ConfigSystem.INSTANCE.getFormat().getBoolean("UserMention.enabled")) message = UserMention.mentionUsers(message,player);
        if (ConfigSystem.INSTANCE.getFormat().getBoolean("ChatFormat", true)) {
            if (event.isProxy()) {
                event.setMessage(Expression.translateColors(Expression.formatChat(player, event.PAPI()) + message));
                return;
            } else {
                event.setFormat(Expression.formatChat(player, event.PAPI()));
                if (!legacy) event.setFormat(Expression.translateColors(event.getFormat()));
                else event.setFormat(event.getFormat());
            }
        }
        if (ConfigSystem.INSTANCE.getConfig().getBoolean("ColoredText", true) && !legacy) {
            message = Expression.translateColors(message);
        }
        event.setMessage(message);
    }

    public static void sendToConsoleAndStaff(final ChatEvent event, final List<String> list, final boolean legacy) {
        String message = ConfigSystem.INSTANCE.getMessages().get("DetectedMessage", "").toString()
                .replace("%player_name%", event.getPlayer().getName())
                .replace("%flags%", String.join(", ", list))
                .replace("%message%", event.getMessage());
        if (!legacy) message = Expression.translateColors(message);
        else message = LegacyChatColor.translateAlternateColorCodes('&', message);
        UniLogHandler.INSTANCE.sendMessage(message);
        for (final Player player : PlayerSystem.INSTANCE.getPlayers()) {
            if (player.hasPermission("worldchatter.control")) {
                player.sendMessage(message);
                SoundSystem.playSoundToPlayer(player, true);
            }
        }
        String playermessage = ConfigSystem.INSTANCE.getMessages().get("DetectedPlayerMessage", "").toString()
                .replace("%player_name%", event.getPlayer().getName())
                .replace("%flags%", String.join(", ", list));
        if (!legacy) playermessage = Expression.translateColors(playermessage);
        else playermessage = LegacyChatColor.translateAlternateColorCodes('&', playermessage);
        if (!playermessage.isEmpty()) {
            event.getPlayer().sendMessage(playermessage);
            SoundSystem.playSoundToPlayer(event.getPlayer(), false);
        }
    }

    public static List<String> detectMethods(final String message, final Player player) {
        final ArrayList<String> detections = new ArrayList<>();
        if (ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiSwear", true) && AntiSwear.hasSwearWords(message) && !player.hasPermission("worldchatter.bypass.antiswear"))
            detections.add("AntiSwear");
        if (ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiADS", true) && AntiADs.hasAds(message) && !player.hasPermission("worldchatter.bypass.antiads"))
            detections.add("AntiADS");
        if (ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiCaps.enabled", true) && AntiCaps.hasAlotOfCaps(message) && !player.hasPermission("worldchatter.bypass.anticaps"))
            detections.add("AntiCaps");
        return detections;
    }
}