package WorldChatterCore.Features;

import WorldChatterCore.Systems.ConfigSystem;

public final class AntiCaps {

    public static AntiCaps INSTANCE;
    private int minCapsPerWord;
    private int minWordsToBlock;

    public AntiCaps() {
        INSTANCE = this;
    }

    /**
     * This is executed by ConfigSystem's reload function
     */
    public void update() {
        minCapsPerWord = ConfigSystem.INSTANCE.getSecurity().getInt("AntiCaps.maxletters", 3);
        minWordsToBlock = ConfigSystem.INSTANCE.getSecurity().getInt("AntiCaps.maximum", 2);
    }

    /**
     * Check if the message is detected or not
     * @param message the player's message
     * @return if it is capped or not
     */
    public boolean hasAlotOfCaps(final String message) {
        final String[] words = message.split(" ");
        int cappedWordsCount = 0;

        for (final String word : words) {
            int capsCount = 0;
            for (int i = 0; i < word.length(); i++) {
                if (Character.isUpperCase(word.charAt(i))) {
                    capsCount++;
                }
            }

            if (capsCount >= minCapsPerWord) {
                cappedWordsCount++;
            }

            if (cappedWordsCount >= minWordsToBlock) {
                return true;
            }
        }

        return false;
    }
}
