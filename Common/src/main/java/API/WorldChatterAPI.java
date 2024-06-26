package API;

import UniversalFunctions.ChatEvent;
import UniversalFunctions.CommandSender;

import java.util.List;

public interface WorldChatterAPI {

    /**
     * Detects the message if it contains IP or URL
     *
     * @param event       presents the player event to control the full event
     * @param flags       shows the detections that the message got
     * @param originEvent returns the event if it's either spigot or bungee's chat event
     */
    void messageDetect(final ChatEvent event, final List<String> flags, final Object originEvent);


    /**
     * Fires when the chat is locked or unlocked
     *
     * @param sender              the author of the command
     * @param lockstate           checks if it's locked or unlocked
     * @param originCommandSender returns the Spigot/Bungee original commandSender to use
     */
    void chatLockToggle(  final CommandSender sender,final boolean lockstate,final Object originCommandSender);

    /**
     * Fires when the user checked if there is an update or not
     *
     * @param updatestate gives you if it needs update or not
     * @param isDev       if it's a development build or not
     */
    void updateChecked( final boolean updatestate,final boolean isDev);


    /**
     * Fires when the plugin config gets reloaded
     *
     * @param sender executor of the function (null if the plugin executed it)
     * @param originCommandSender executor of the function but with spigot/bungee's event
     */
    void configReload(final CommandSender sender, final Object originCommandSender);

}