package me.omaromar93.worldchatter.utils.chatting;

import me.omaromar93.worldchatter.WorldChatter;
import me.omaromar93.worldchatter.utils.Others.ConfigSystem;
import me.omaromar93.worldchatter.utils.methods.MethodHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getScheduler;

public final class ChattingSystem {


    public final static HashMap<UUID, BukkitTask> cooldowns = new HashMap<>();

    public static void returnFormattedMessage(final AsyncPlayerChatEvent event) {
        if ((!event.getPlayer().hasPermission("worldchatter.bypass.antispam") && cooldowns.containsKey(event.getPlayer().getUniqueId())) || (ConfigSystem.getConfig().getBoolean("ChatLock") && CommandSystem.Chatlock)) {
            event.setCancelled(true);
            final String spam = ChatColor.translateAlternateColorCodes('&', ConfigSystem.getConfig().getString("SpamMessage"));
            if (cooldowns.containsKey(event.getPlayer().getUniqueId()) && !spam.isEmpty())
                event.getPlayer().sendMessage(spam);
            return;
        }
        if (ConfigSystem.getConfig().getInt("AntiSpam") > 0)
            coolThatPlayerDown(event.getPlayer());
        MethodHandler.runMethodsOnMessage(event.getPlayer(), event.getMessage(), event);
        makeThatMessaageAloneInThatWorld(event);
    }

    public static void coolThatPlayerDown(final Player player) {
        cooldowns.put(player.getUniqueId(), getScheduler().runTaskLater(WorldChatter.INSTANCE, () -> cooldowns.remove(player.getUniqueId()), ConfigSystem.getConfig().getInt("AntiSpam") * 20L));
    }

    public static void makeThatMessaageAloneInThatWorld(final AsyncPlayerChatEvent event) {
        event.getRecipients().removeIf(player -> !player.getWorld().getName().equals(event.getPlayer().getWorld().getName()));
    }
}