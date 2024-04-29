package me.omaromar93.worldchatter;

import Others.CacheSystem;
import Others.ConfigSystem;
import UniversalFunctions.ChatEvent;
import UniversalFunctions.Player;
import chatting.ChattingSystem;
import me.clip.placeholderapi.PlaceholderAPI;
import me.omaromar93.worldchatter.PAPI.PAPIDependSystem;
import me.omaromar93.worldchatter.functions.SpigotPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChatEventHandler implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        final ChatEvent chatEvent = new ChatEvent(event, PAPIDependSystem.INSTANCE.isPAPIThere(), false, (Player) CacheSystem.getOrAddCache("player:" + event.getPlayer().getUniqueId(), new SpigotPlayer(event.getPlayer())), event.getFormat(), event.getMessage(), getRecipients(event));
        if (!ConfigSystem.INSTANCE.getConfig().getStringList("BlackListPlaces").contains(event.getPlayer().getWorld().getName())) {
            ChattingSystem.returnFormattedMessage(chatEvent);
            if (!ConfigSystem.INSTANCE.getConfig().getBoolean("GlobalChat")) {
                ChattingSystem.makeThatMessageAloneInThatWorld(chatEvent);
            }
            event.setFormat((PAPIDependSystem.INSTANCE.isPAPIThere() ? PlaceholderAPI.setPlaceholders(event.getPlayer(), chatEvent.getFormat()) : chatEvent.getFormat()).replace("%", "%%") + "%2$s");
            event.setMessage((ConfigSystem.INSTANCE.getFormat().getBoolean("NewLine") ? chatEvent.getMessage().replace("\\n", "\n") : event.getMessage()));
        } else {
            if (ConfigSystem.INSTANCE.getConfig().getBoolean("solomessage"))
                ChattingSystem.makeThatMessageAloneInThatWorld(chatEvent);
        }
        event.setCancelled(chatEvent.isCancelled());
        editRecipients(chatEvent.getRecipients(), event);
    }

    private List<Player> getRecipients(final AsyncPlayerChatEvent event) {
        final List<Player> players = new ArrayList<>();

        for (final org.bukkit.entity.Player player : event.getRecipients()) {
            players.add((Player) CacheSystem.getOrAddCache("player:" + player.getUniqueId(), new SpigotPlayer(player)));
        }

        return players;
    }

    private void editRecipients(final List<Player> recipients, final AsyncPlayerChatEvent event) {

        final Set<UUID> uuids = recipients.stream()
                .map(Player::getUUID)
                .collect(Collectors.toSet());

        event.getRecipients().removeIf(player -> !uuids.contains(player.getUniqueId()));
    }
}