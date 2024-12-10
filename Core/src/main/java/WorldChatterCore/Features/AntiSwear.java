package WorldChatterCore.Features;

import WorldChatterCore.Others.Util;
import WorldChatterCore.Systems.ConfigSystem;

import java.text.Normalizer;
import java.util.*;

public final class AntiSwear {

    private final Set<String> curseWords;
    private final Set<String> whitelist;
    private Double similarity;
    private Integer minimum;

    public static AntiSwear INSTANCE;

    public AntiSwear() {
        INSTANCE = this;
        curseWords = new HashSet<>();
        whitelist = new HashSet<>();
    }

    /**
     * Updates the curse words and whitelist by fetching them from the configuration and external source.
     */
    public void update() {
        whitelist.clear();
        curseWords.clear();

        if (ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiSwear.enabled")) {
            // Populate whitelist
            whitelist.addAll(ConfigSystem.INSTANCE.getSecurity().getStringList("AntiSwear.whitelist"));

            // Fetch curse words from an external URL and local configuration
            final String[] words = Objects.requireNonNull(Util.getContentfromURl(
                    ConfigSystem.INSTANCE.getSystem().getString("ASWLocation", "https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/profanity_list.txt"))
            ).split("\n");

            curseWords.addAll(Arrays.asList(words));
            curseWords.addAll(ConfigSystem.INSTANCE.getSecurity().getStringList("AntiSwear.blacklist"));
            curseWords.removeAll(whitelist); // Ensure whitelist overrides blacklist
            similarity = ConfigSystem.INSTANCE.getSecurity().getDouble("AntiSwear.sensitivity", 80);
            minimum = ConfigSystem.INSTANCE.getSecurity().getInt("AntiSwear.minimum", 3);
            return;
        }
        similarity = null;
        minimum = null;
    }

    /**
     * Removes all non-letter characters and normalizes obfuscated curse words.
     *
     * @param message The input string.
     * @return A normalized string containing only letters, lowercase.
     */
    public String preprocessMessage(final String message) {
        // Normalize accents (e.g., "café" → "cafe")
        String normalized = Normalizer.normalize(message, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // Remove diacritical marks

        // Remove all non-letter characters (e.g., "~", "-", "_", ".", " ")
        normalized = normalized.replaceAll("[^a-zA-Z]", ""); // Keep only letters

        return normalized.toLowerCase(); // Convert to lowercase for consistency
    }

    /**
     * Checks if a message contains a curse word.
     *
     * @param message The input message to check.
     * @return True if a curse word is found, otherwise false.
     */
    public boolean containsCurseWord(final String message) {
        final String[] words = preprocessMessage(message.toLowerCase()).split("\\s+");

        for (final String word : words) {
            if (word.length() < minimum) continue; // Skip short words
            if (whitelist.contains(word)) continue; // Skip whitelisted words

            for (final String curse : curseWords) {
                if (isSimilar(word, curse)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines whether two words are similar based on a similarity threshold.
     *
     * @param word  The player's word.
     * @param curse The curse word to compare against.
     * @return True if the words are similar, otherwise false.
     */
    private boolean isSimilar(final String word, final String curse) {
        return Util.calculateSimilarity(word, curse) >= this.similarity; // Only flag if similarity exceeds threshold
    }
}