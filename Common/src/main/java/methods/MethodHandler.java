package methods;

import API.APICore;
import API.WorldChatterAPI;
import Others.ConfigSystem;
import Others.PlayerSystem;
import Others.SoundSystem;
import Others.ThreadsSystem;
import UniversalFunctions.ChatEvent;
import UniversalFunctions.Player;
import UniversalFunctions.UniLogHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class MethodHandler {

    public static void runMethodsOnMessage(final Player player, @NotNull String message, final ChatEvent event) {
        if (ConfigSystem.INSTANCE.getTexts().getBoolean("texts.enabled", true))
            message = Expression.replaceIt(player, message);
        final List<String> list = detectMethods(message, player);
        if (list.contains("AntiSwear") || list.contains("AntiADS")) {
            event.setCancelled(true);
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners()) {
                api.messageDetect(event, list, event.getOriginEvent());
            }
            ThreadsSystem.runAsync(() -> sendToConsoleAndStaff(event, list));
            return;
        }
        if (ConfigSystem.INSTANCE.getConfig().getBoolean("ColoredText", true)) {
            message = Expression.translateColors(message);
        }
        if (ConfigSystem.INSTANCE.getFormat().getBoolean("ChatFormat", true)) {
            if (event.isProxy()) {
                event.setMessage(Expression.translateColors(Expression.formatChat(player, event.PAPI()) + message));
                return;
            } else {
                event.setFormat(Expression.formatChat(player, event.PAPI()));
                event.setFormat(Expression.translateColors(event.getFormat()));
            }
        }
        event.setMessage(message);
    }

    public static void sendToConsoleAndStaff(final ChatEvent event, final List<String> list) {
        String message = ConfigSystem.INSTANCE.getMessages().get("DetectedMessage", "").toString()
                .replace("%player%", event.getPlayer().getName())
                .replace("%flags%", String.join(", ", list))
                .replace("%message%", event.getMessage());
        message = Expression.translateColors(message);
        UniLogHandler.INSTANCE.sendMessage(message);
        for (final Player player : PlayerSystem.INSTANCE.getPlayers()) {
            if (player.hasPermission("worldchatter.control")) {
                player.sendMessage(message);
                SoundSystem.playSoundToPlayer(player, true);
            }
        }
        String playermessage = ConfigSystem.INSTANCE.getMessages().get("DetectedPlayerMessage", "").toString()
                .replace("%player%", event.getPlayer().getName())
                .replace("%flags%", String.join(", ", list));
        playermessage = Expression.translateColors(playermessage);
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
        return detections;
    }
}