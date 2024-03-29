package me.omaromar93.worldchatter.utils.methods;

import me.omaromar93.worldchatter.utils.Others.ConfigSystem;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class AntiSwear {

    static final List<String> list = new ArrayList<>();
    private static Pattern pattern;

    public static boolean hasSwearWords(final String message) {
        return pattern.matcher(message.toLowerCase()).find();
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
        final URL url = new URL(ConfigSystem.getConfig().getString("ASWLocation"));
        final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.length() > 1) list.add(line.trim().toLowerCase()
                    .replaceAll("[^a-zA-Z0-9]", " "));
        }
        reader.close();
        list.addAll(ConfigSystem.getConfig().getStringList("CustomSwearWords"));
        pattern = Pattern.compile(getRegex());
        reader.close();
    }
}