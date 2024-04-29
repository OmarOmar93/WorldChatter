package Others;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class OtherFunctions {
    public static String getUrlAsString(final String url) throws IOException {
        URL url2 = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));
        String line;
        if ((line = in.readLine()) != null)
            return line;
        in.close();
        return url;
    }
}
