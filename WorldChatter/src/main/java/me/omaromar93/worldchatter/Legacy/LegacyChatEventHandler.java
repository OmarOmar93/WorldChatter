package me.omaromar93.worldchatter.Legacy;

import Others.CacheSystem;
import Others.ConfigSystem;
import UniversalFunctions.ChatEvent;
import UniversalFunctions.LegacyChatColor;
import UniversalFunctions.Player;
import chatting.ChattingSystem;
import me.clip.placeholderapi.PlaceholderAPI;
import me.omaromar93.worldchatter.PAPI.PAPIDependSystem;
import me.omaromar93.worldchatter.functions.SpigotPlayer;
import methods.Expression;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class LegacyChatEventHandler implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(final PlayerChatEvent event) {
        boolean legacy = false;
        ChatEvent chatEvent;
        try {
            chatEvent = new ChatEvent(event, PAPIDependSystem.INSTANCE.isPAPIThere(), false, (Player) CacheSystem.getOrAddCache("player:" + event.getPlayer().getUniqueId(), new SpigotPlayer(event.getPlayer())), event.getFormat(), event.getMessage(), getRecipients(event));
        } catch (final NoClassDefFoundError ignored) {
            legacy = true;
            chatEvent = new ChatEvent(event, PAPIDependSystem.INSTANCE.isPAPIThere(), false, (Player) CacheSystem.getOrAddCache("player:" + event.getPlayer().getUniqueId(), new LegacySpigotPlayer(event.getPlayer())), event.getFormat(), event.getMessage(), getRecipients(event));
        }
        if (!ConfigSystem.INSTANCE.getConfig().getStringList("BlackListPlaces").contains(event.getPlayer().getWorld().getName())) {
            ChattingSystem.returnFormattedMessage(chatEvent, legacy);
            if (!ConfigSystem.INSTANCE.getConfig().getBoolean("GlobalChat")) {
                ChattingSystem.makeThatMessageAloneInThatWorld(chatEvent);
            }
            if (legacy) {
                if (ConfigSystem.INSTANCE.getFormat().getBoolean("ChatFormat", true))
                    event.setFormat(LegacyChatColor.translateAlternateColorCodes('&', (PAPIDependSystem.INSTANCE.isPAPIThere() ? PlaceholderAPI.setPlaceholders(event.getPlayer(), chatEvent.getFormat()) : chatEvent.getFormat()).replace("%", "%%") + "%2$s"));
                if (ConfigSystem.INSTANCE.getConfig().getBoolean("ColoredText", true))
                    event.setMessage(LegacyChatColor.translateAlternateColorCodes('&', (ConfigSystem.INSTANCE.getFormat().getBoolean("NewLine") ? chatEvent.getMessage().replace("\\n", "\n") : event.getMessage())));
                else
                    event.setMessage((ConfigSystem.INSTANCE.getFormat().getBoolean("NewLine") ? chatEvent.getMessage().replace("\\n", "\n") : event.getMessage()));
            } else {
                if (ConfigSystem.INSTANCE.getFormat().getBoolean("ChatFormat", true))
                    if (ConfigSystem.INSTANCE.getConfig().getBoolean("ColoredText", true)) event.setFormat(Expression.translateColors((PAPIDependSystem.INSTANCE.isPAPIThere() ? PlaceholderAPI.setPlaceholders(event.getPlayer(), chatEvent.getFormat()) : chatEvent.getFormat()).replace("%", "%%") + "%2$s"));
                    else event.setFormat((PAPIDependSystem.INSTANCE.isPAPIThere() ? PlaceholderAPI.setPlaceholders(event.getPlayer(), chatEvent.getFormat()) : chatEvent.getFormat()).replace("%", "%%") + "%2$s");
                event.setMessage((ConfigSystem.INSTANCE.getFormat().getBoolean("NewLine") ? chatEvent.getMessage().replace("\\n", "\n") : event.getMessage()));
            }
        } else {
            if (ConfigSystem.INSTANCE.getConfig().getBoolean("solomessage"))
                ChattingSystem.makeThatMessageAloneInThatWorld(chatEvent);
        }
        event.setCancelled(chatEvent.isCancelled());
        editRecipients(chatEvent.getRecipients(), event);
    }

    private List<Player> getRecipients(final PlayerChatEvent event) {
        final List<Player> players = new ArrayList<>();

        for (final org.bukkit.entity.Player player : event.getRecipients()) {
            try {
                players.add((Player) CacheSystem.getOrAddCache("player:" + player.getUniqueId(), new SpigotPlayer(player)));
            } catch (final NoClassDefFoundError ignored) {
                players.add((Player) CacheSystem.getOrAddCache("player:" + player.getUniqueId(), new LegacySpigotPlayer(player)));
            }
        }

        return players;
    }

    private void editRecipients(final List<Player> recipients, final PlayerChatEvent event) {

        final Set<UUID> uuids = recipients.stream()
                .map(Player::getUUID)
                .collect(Collectors.toSet());

        event.getRecipients().removeIf(player -> !uuids.contains(player.getUniqueId()));
    }
}