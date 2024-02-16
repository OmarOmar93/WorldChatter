package me.omaromar93.worldchatter.utils.API;

import org.bukkit.command.CommandSender;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public interface WorldChatterAPI {

    /**
     * Detects the message if it contains IP or URL
     * @param event presents the player event to control the full event
     * @param flags shows the detections that the message got
     */
    void messageDetect(final AsyncPlayerChatEvent event, final List<String> flags);


    /**
     * Fires when the chat is locked or unlocked
     * @param sender    the author of the command
     * @param lockstate checks if it's locked or unlocked
     */
    void chatLockToggle(final CommandSender sender, boolean lockstate);

    /**
     * Fires when the user checked if there is an update or not
     * @param updatestate gives you if it needs update or not
     */
    void updateChecked(boolean updatestate);


    /**
     * Fires when the plugin config gets reloaded
     * @param sender executor of the function (null if the plugin executed it)
     */
    void configReload(CommandSender sender);

}
