package WorldChatterCore.Features;

import WorldChatterCore.Others.Util;
import WorldChatterCore.Systems.ConfigSystem;

import java.util.*;

public class AntiRepeat {
    public static AntiRepeat INSTANCE;
    private final Map<UUID, LinkedList<String>> playerMessages = new LinkedHashMap<>();
    private Integer MESSAGE_LIMIT;
    private Double SENSITIVITY_THRESHOLD;

    public AntiRepeat() {
        INSTANCE = this;
    }

    public void update() {
        if (ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiRepeat.enabled")) {
            MESSAGE_LIMIT = ConfigSystem.INSTANCE.getSecurity().getInt("AntiRepeat.messageLimit");
            SENSITIVITY_THRESHOLD = ConfigSystem.INSTANCE.getSecurity().getDouble("AntiRepeat.sensitivity");
            return;
        }
        MESSAGE_LIMIT = null;
        SENSITIVITY_THRESHOLD = null;
    }

    public boolean isSimilarMessage(final UUID playerId, final String newMessage) {
        // Get or create the player's message list
        playerMessages.putIfAbsent(playerId, new LinkedList<>());
        final LinkedList<String> messages = playerMessages.get(playerId);

        for (final String oldMessage : messages) {
            double similarity = Util.calculateSimilarity(newMessage, oldMessage);
            if (similarity >= SENSITIVITY_THRESHOLD) {
                return true; // Message is too similar
            }
        }

        // Add the new message to the list
        messages.add(newMessage);

        // Maintain the message limit
        if (messages.size() > MESSAGE_LIMIT) {
            messages.removeFirst();
        }

        return false;
    }
}