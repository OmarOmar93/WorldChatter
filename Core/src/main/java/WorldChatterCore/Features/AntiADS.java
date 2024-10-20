package WorldChatterCore.Features;

import java.util.regex.Pattern;

public final class AntiADS {
    private static final Pattern pattern = Pattern.compile("([\\w+]+://)?([\\w-]+\\.)*[\\w-]+[.:]\\w+([/?=&#.]?[\\w-]+)*/?", Pattern.CASE_INSENSITIVE);
    
    public static boolean hasAds(final String message) {
        return pattern.matcher(message).find();
    }
}
