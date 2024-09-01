package WorldChatterCore.Others;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public final class Util {

    public static String getContentfromURl(final String URL) {
        try {
            return new Scanner(new URL(URL).openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (final IOException ignored) {
            return null;
        }
    }

}
