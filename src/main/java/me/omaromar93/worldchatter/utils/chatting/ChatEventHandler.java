package me.omaromar93.worldchatter.utils.chatting;

import me.omaromar93.worldchatter.utils.Others.ConfigSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEventHandler implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!ConfigSystem.getConfig().getList("BlackListWorlds").contains(event.getPlayer().getWorld().getName())) {
            ChattingSystem.returnFormattedMessage(event);
            return;
        }
        if (ConfigSystem.getConfig().getBoolean("solomessage")) ChattingSystem.makeThatMessaageAloneInThatWorld(event);
    }
}