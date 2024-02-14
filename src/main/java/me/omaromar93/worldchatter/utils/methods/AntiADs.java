package me.omaromar93.worldchatter.utils.methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AntiADs {

    public static boolean hasAds(final String message) {
        String antiAdRegex = "\\b(?:\\.com|\\.net|\\.org|\\d+\\.\\d+\\.\\d+\\.\\d+)\\b";
        Pattern pattern = Pattern.compile(antiAdRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }
}
