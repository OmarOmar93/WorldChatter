package UniversalFunctions;

import org.jetbrains.annotations.NotNull;

public class LegacyChatColor {

    @NotNull
    public static String translateAlternateColorCodes(final char altColorChar, @NotNull final String textToTranslate) {
        final char[] b = textToTranslate.toCharArray();

        for(int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }


}
