package chatting;

import Others.ConfigSystem;
import Others.SoundSystem;
import UniversalFunctions.ChatEvent;
import UniversalFunctions.LegacyChatColor;
import UniversalFunctions.Player;
import methods.Expression;
import methods.MethodHandler;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public final class ChattingSystem {

    private static boolean ChatLock = false;
    public static final HashMap<UUID, Timer> cooldowns = new HashMap<>();

    public static final HashMap<UUID, Long> durations = new HashMap<>();

    public static boolean toggleChatLock() {
        return ChatLock = !ChatLock;
    }

    public static boolean isChatLock() {
        return ChatLock;
    }

    public static void returnFormattedMessage(final ChatEvent event, final Boolean leagcy) {
        if ((!event.getPlayer().hasPermission("worldchatter.bypass.antispam") && cooldowns.containsKey(event.getPlayer().getUUID())) || (ConfigSystem.INSTANCE.getSecurity().getBoolean("ChatLock") && ChatLock && !event.getPlayer().hasPermission("worldchatter.bypass.chatlock"))) {
            event.setCancelled(true);
            String spam;
            if (!leagcy)
                spam = Expression.translateColors(ConfigSystem.INSTANCE.getMessages().get("SpamMessage", "").toString()
                        .replace("%player_name%", event.getPlayer().getName())
                        .replace("%duration%", getTimeLeft(event.getPlayer())));
            else
                spam = LegacyChatColor.translateAlternateColorCodes('&', ConfigSystem.INSTANCE.getMessages().get("SpamMessage", "").toString()
                        .replace("%player_name%", event.getPlayer().getName())
                        .replace("%duration%", getTimeLeft(event.getPlayer())));
            if (cooldowns.containsKey(event.getPlayer().getUUID()) && !spam.isEmpty()) {
                event.getPlayer().sendMessage(spam);
                SoundSystem.playSoundToPlayer(event.getPlayer(), false);
            }
            return;
        }
        if (ConfigSystem.INSTANCE.getSecurity().getInt("AntiSpam") > 0)
            coolThatPlayerDown(event.getPlayer());
        MethodHandler.runMethodsOnMessage(event.getPlayer(), event.getMessage(), event, leagcy);
    }

    private static String getTimeLeft(final Player player) {
        final long l = durations.get(player.getUUID()) - System.currentTimeMillis();
        return String.valueOf(l/1000);
    }

    private static void coolThatPlayerDown(final Player player) {
        final Timer timer = new Timer();
        long endtime = ConfigSystem.INSTANCE.getSecurity().getInt("AntiSpam") * 1000L;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cooldowns.remove(player.getUUID());
                durations.remove(player.getUUID());
            }
        }, endtime);
        durations.put(player.getUUID(), System.currentTimeMillis() + endtime);
        cooldowns.put(player.getUUID(), timer);
    }


    public static void makeThatMessageAloneInThatWorld(final ChatEvent event) {
        event.getRecipients().removeIf(player -> !player.getPlace().equals(event.getPlayer().getPlace()));
    }
}