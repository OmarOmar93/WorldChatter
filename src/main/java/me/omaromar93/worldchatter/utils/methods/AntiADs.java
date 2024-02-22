package me.omaromar93.worldchatter.utils.methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AntiADs {

    static Pattern pattern;

    @SuppressWarnings("ALL")
    public static boolean hasAds(final String message) {
        String antiAdRegex = "([\\w+]+:\\/\\/)?([\\w\\d-]+\\.)*[\\w-]+[\\.\\:]\\w+([\\/\\?\\=\\&\\#\\.]?[\\w-]+)*\\/?";
        pattern = Pattern.compile(antiAdRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }
}