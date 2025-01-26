package WorldChatterCore.Features;

import java.util.regex.Pattern;

public final class AntiADS {
    private static final Pattern pattern = Pattern.compile("([\\w+]+://)?([\\w-]+\\.)*[\\w-]+[.:]\\w+([/?=&#.]?[\\w-]+)*/?", Pattern.CASE_INSENSITIVE);

    /**
     * Check if the function has any links or Numeric IPs
     * @param message the player's message
     * @return
     */
    public static boolean hasAds(final String message) {
        return pattern.matcher(message).find();
    }
}
