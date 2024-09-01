package WorldChatterCore.Features;

import WorldChatterCore.Systems.ConfigSystem;

public final class AntiCaps {

    public static AntiCaps INSTANCE;
    private int minCapsPerWord;
    private int minWordsToBlock;

    public AntiCaps() {
        INSTANCE = this;
    }

    public void update() {
        minCapsPerWord = ConfigSystem.INSTANCE.getSecurity().getInt("AntiCaps.maxletters", 3);
        minWordsToBlock = ConfigSystem.INSTANCE.getSecurity().getInt("AntiCaps.maximum", 2);
    }

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
