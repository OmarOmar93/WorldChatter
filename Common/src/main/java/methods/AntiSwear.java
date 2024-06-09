package methods;

import Others.ConfigSystem;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class AntiSwear {

    private static Pattern pattern;
    private static final List<String> list = new ArrayList<>();

    public static boolean hasSwearWords(final String message) {
        return pattern != null && pattern.matcher(message.toLowerCase()).find();
    }

    private static String getRegex() {
        final StringBuilder builder = new StringBuilder();
        String[] letters;
        for (final String word : list) {
            letters = word.toLowerCase().split("");
            for (final String letter : letters) {
                builder.append(letter).append("+");
            }

            builder.append("|");
        }

        return builder.substring(0, builder.length() - 1);
    }

    public static void update() throws IOException {
        list.clear();
        final String urlName = ConfigSystem.INSTANCE.getConfig().get("ASWLocation", "https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/profanity_list.txt").toString();
        final URL url = new URL(urlName);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.length() > 1) {
                String word = line.trim().toLowerCase()
                        .replaceAll("[^a-zA-Z0-9]", " ");
                if (!ConfigSystem.INSTANCE.getSecurity().getStringList("WhitelistSwearWords").contains(word))
                    list.add(word);
            }
        }
        reader.close();
        list.addAll(ConfigSystem.INSTANCE.getSecurity().getStringList("CustomSwearWords"));
        pattern = Pattern.compile(getRegex());
    }
}