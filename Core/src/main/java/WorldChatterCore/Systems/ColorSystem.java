package WorldChatterCore.Systems;

import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public final class ColorSystem {


    public static final char COLOR_CHAR = '§';
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + "&" + "[0-9A-FK-ORX]");
    private static final Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
    /**
     * Colour instances keyed by their active character.
     */
    private static final Map<Character, ColorSystem> BY_CHAR = new HashMap<>();
    /**
     * Colour instances keyed by their name.
     */
    private static final Map<String, ColorSystem> BY_NAME = new HashMap<>();
    /**
     * Represents black.
     */
    public static final ColorSystem BLACK = new ColorSystem('0', "black", new Color(0x000000));
    /**
     * Represents dark blue.
     */
    public static final ColorSystem DARK_BLUE = new ColorSystem('1', "dark_blue", new Color(0x0000AA));
    /**
     * Represents dark green.
     */
    public static final ColorSystem DARK_GREEN = new ColorSystem('2', "dark_green", new Color(0x00AA00));
    /**
     * Represents dark blue (aqua).
     */
    public static final ColorSystem DARK_AQUA = new ColorSystem('3', "dark_aqua", new Color(0x00AAAA));
    /**
     * Represents dark red.
     */
    public static final ColorSystem DARK_RED = new ColorSystem('4', "dark_red", new Color(0xAA0000));
    /**
     * Represents dark purple.
     */
    public static final ColorSystem DARK_PURPLE = new ColorSystem('5', "dark_purple", new Color(0xAA00AA));
    /**
     * Represents gold.
     */
    public static final ColorSystem GOLD = new ColorSystem('6', "gold", new Color(0xFFAA00));
    /**
     * Represents gray.
     */
    public static final ColorSystem GRAY = new ColorSystem('7', "gray", new Color(0xAAAAAA));
    /**
     * Represents dark gray.
     */
    public static final ColorSystem DARK_GRAY = new ColorSystem('8', "dark_gray", new Color(0x555555));
    /**
     * Represents blue.
     */
    public static final ColorSystem BLUE = new ColorSystem('9', "blue", new Color(0x5555FF));
    /**
     * Represents green.
     */
    public static final ColorSystem GREEN = new ColorSystem('a', "green", new Color(0x55FF55));
    /**
     * Represents aqua.
     */
    public static final ColorSystem AQUA = new ColorSystem('b', "aqua", new Color(0x55FFFF));
    /**
     * Represents red.
     */
    public static final ColorSystem RED = new ColorSystem('c', "red", new Color(0xFF5555));
    /**
     * Represents light purple.
     */
    public static final ColorSystem LIGHT_PURPLE = new ColorSystem('d', "light_purple", new Color(0xFF55FF));
    /**
     * Represents yellow.
     */
    public static final ColorSystem YELLOW = new ColorSystem('e', "yellow", new Color(0xFFFF55));
    /**
     * Represents white.
     */
    public static final ColorSystem WHITE = new ColorSystem('f', "white", new Color(0xFFFFFF));
    /**
     * Represents magical characters that change around randomly.
     */
    public static final ColorSystem MAGIC = new ColorSystem('k', "obfuscated");
    /**
     * Makes the text bold.
     */
    public static final ColorSystem BOLD = new ColorSystem('l', "bold");
    /**
     * Makes a line appear through the text.
     */
    public static final ColorSystem STRIKETHROUGH = new ColorSystem('m', "strikethrough");
    /**
     * Makes the text appear underlined.
     */
    public static final ColorSystem UNDERLINE = new ColorSystem('n', "underline");
    /**
     * Makes the text italic.
     */
    public static final ColorSystem ITALIC = new ColorSystem('o', "italic");
    /**
     * Resets all previous chat colors or formats.
     */
    public static final ColorSystem RESET = new ColorSystem('r', "reset");
    /**
     * Count used for populating legacy ordinal.
     */
    private static int count = 0;
    /**
     * This colour's colour char prefixed by the {@link #COLOR_CHAR}.
     */
    private final String toString;
    private final String name;
    private final int ordinal;

    private final Color color;

    private ColorSystem(final char code, final String name) {
        this(code, name, null);
    }

    private ColorSystem(final char code, final String name, final Color color) {
        this.name = name;
        this.toString = new String(new char[]
                {
                        COLOR_CHAR, code
                });
        this.ordinal = count++;
        this.color = color;

        BY_CHAR.put(code, this);
        BY_NAME.put(name.toUpperCase(Locale.ROOT), this);
    }

    private ColorSystem(final String name, final String toString, final int rgb) {
        this.name = name;
        this.toString = toString;
        this.ordinal = -1;
        this.color = new Color(rgb);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.toString);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ColorSystem other = (ColorSystem) obj;

        return Objects.equals(this.toString, other.toString);
    }

    @Override
    public String toString() {
        return toString;
    }

    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String tCC(final String message) {
        final char[] b = message.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '&' && ALL_CODES.indexOf(b[i + 1]) > -1) {
                b[i] = ColorSystem.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static String getLastColors(final String input) {
        String result = "";
        int length = input.length();

        for(int index = length - 1; index > -1; --index) {
            final char section = input.charAt(index);
            if (section == 167 && index < length - 1) {
                final char c = input.charAt(index + 1);
                final ColorSystem color = getByChar(c);
                if (color != null) {
                    result = color.toString() + result;
                    if (color.color == null || color.equals(RESET)) {
                        break;
                    }
                }
            }
        }

        return result;
    }



    public static ColorSystem getByChar(final char code) {
        return BY_CHAR.get(code);
    }

    public static ColorSystem of(final Color color) {
        return of("#" + String.format("%08x", color.getRGB()).substring(2));
    }

    public static ColorSystem of(final String string) {
        if (string.length() == 7 && string.charAt(0) == '#') {
            int rgb;
            try {
                rgb = Integer.parseInt(string.substring(1), 16);
            } catch (final NumberFormatException ex) {
                throw new IllegalArgumentException("Illegal hex string " + string);
            }

            final StringBuilder magic = new StringBuilder(COLOR_CHAR + "x");
            for (final char c : string.substring(1).toCharArray()) {
                magic.append(COLOR_CHAR).append(c);
            }

            return new ColorSystem(string, magic.toString(), rgb);
        }

        final ColorSystem defined = BY_NAME.get(string.toUpperCase(Locale.ROOT));
        if (defined != null) {
            return defined;
        }

        throw new IllegalArgumentException("Could not parse ColorSystem " + string);
    }

}
