package WorldChatterCore.Features;

import java.util.regex.Pattern;

public class AntiADS {
    private final static Pattern pattern = Pattern.compile("([\\w+]+://)?([\\w-]+\\.)*[\\w-]+[.:]\\w+([/?=&#.]?[\\w-]+)*/?", Pattern.CASE_INSENSITIVE);

    @SuppressWarnings("ALL")
    public static boolean hasAds(final String message) {
        return pattern.matcher(message).find();
    }
}
