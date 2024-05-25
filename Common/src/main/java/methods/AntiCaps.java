package methods;

import Others.ConfigSystem;

public class AntiCaps {

    private final static int minCapsPerWord = ConfigSystem.INSTANCE.getSecurity().getInt("AntiCaps.maxletters");
    private final static int minWordsToBlock = ConfigSystem.INSTANCE.getSecurity().getInt("AntiCaps.maximum");

    public static boolean hasAlotOfCaps(final String message) {
        final String[] words = message.split("\\s+");
        int cappedWordsCount = 0;

        for (final String word : words) {
            int capsCount = 0;
            for (int i = 0; i < word.length(); i++) {
                if (Character.isUpperCase(word.charAt(i))) {
                    capsCount++;
                    if (capsCount >= minCapsPerWord) {
                        cappedWordsCount++;
                        break;
                    }
                }
            }

            if (cappedWordsCount >= minWordsToBlock) {
                return true;
            }
        }

        return false;
    }
}
