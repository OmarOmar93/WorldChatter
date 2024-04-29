package methods;

import java.util.regex.Pattern;

public final class AntiADs {

    private final static Pattern pattern = Pattern.compile("([\\w+]+:\\/\\/)?([\\w\\d-]+\\.)*[\\w-]+[\\.\\:]\\w+([\\/\\?\\=\\&\\#\\.]?[\\w-]+)*\\/?", Pattern.CASE_INSENSITIVE);

    @SuppressWarnings("ALL")
    public static boolean hasAds(final String message) {
        return pattern.matcher(message).find();
    }
}