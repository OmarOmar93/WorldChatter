package WorldChatterCore.Features;

import WorldChatterCore.Others.Util;
import WorldChatterCore.Systems.ConfigSystem;

import java.text.Normalizer;
import java.util.*;

public final class AntiSwear {

    private final Set<String> curseWords;
    private final Set<String> whitelist;

    public static AntiSwear INSTANCE;

    public AntiSwear() {
        INSTANCE = this;
        curseWords = new HashSet<>();
        whitelist = new HashSet<>();
    }

    public void update() {
        whitelist.clear();
        curseWords.clear();

        if (ConfigSystem.INSTANCE.getSecurity().getBoolean("AntiSwear.enabled")) {
            whitelist.addAll(ConfigSystem.INSTANCE.getSecurity().getStringList("AntiSwear.whitelist"));

            final String[] words = Objects.requireNonNull(Util.getContentfromURl(
                            ConfigSystem.INSTANCE.getSystem().getString("ASWLocation", "https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/profanity_list.txt")))
                    .split("\n");

            curseWords.addAll(Arrays.asList(words));
            curseWords.addAll(ConfigSystem.INSTANCE.getSecurity().getStringList("AntiSwear.blacklist"));
            curseWords.removeAll(whitelist);
        }
    }

    private boolean isSimilar(String word, String curse) {
        if (word.length() < curse.length() - 1 || word.length() > curse.length() + 1) {
            return false;
        }

        int differences = 0;
        int i = 0, j = 0;
        while (i < word.length() && j < curse.length()) {
            if (Character.toLowerCase(word.charAt(i)) != Character.toLowerCase(curse.charAt(j))) {
                differences++;
                if (word.length() > curse.length()) i++;
                else if (word.length() < curse.length()) j++;
                else {
                    i++;
                    j++;
                }
            } else {
                i++;
                j++;
            }
            if (differences > 1) return false;
        }

        differences += word.length() - i + curse.length() - j;
        return differences <= 1;
    }

    public String removeAccents(final String message) {
        return Normalizer.normalize(message, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }

    public boolean containsCurseWord(final String message) {
        final String[] words = removeAccents(message.toLowerCase()).split("\\s+");
        for (String word : words) {
            for (String curse : curseWords) {
                if (isSimilar(word, curse)) {
                    return true;
                }
            }
        }
        return false;
    }
}