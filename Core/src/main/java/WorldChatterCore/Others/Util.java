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

    public static int levenshteinDistance(final String s1, final String s2) {
        final int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
                    );
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public static double calculateSimilarity(String s1, String s2) {
        s1 = s1.toLowerCase().trim();
        s2 = s2.toLowerCase().trim();

        final int distance = levenshteinDistance(s1, s2);
        final int maxLen = Math.max(s1.length(), s2.length());

        return 100.0 * (1 - (double) distance / maxLen);
    }
}
