package WorldChatterCore.Features;

import WorldChatterCore.Others.Util;
import WorldChatterCore.Systems.ConfigSystem;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AntiSwear {

    private Pattern curseWordPattern;

    public static AntiSwear INSTANCE;

    private List<String> whitelist;

    public AntiSwear() {
        INSTANCE = this;
    }

    public void update() {
        whitelist = ConfigSystem.INSTANCE.getSecurity().getStringList("AntiSwear.whitelist");
        final List<String> words = new ArrayList<>(Arrays.asList(Objects.requireNonNull(Util.getContentfromURl(
                        ConfigSystem.INSTANCE.getSystem().getString("ASWLocation", "https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/profanity_list.txt")))
                .split("\n")));
        words.addAll(ConfigSystem.INSTANCE.getSecurity().getStringList("AntiSwear.blacklist"));
        final String patternString = buildPattern(words);
        curseWordPattern = Pattern.compile(patternString, Pattern.UNICODE_CHARACTER_CLASS);
    }

    private String buildPattern(final List<String> curseWords) {
        final StringBuilder patternBuilder = new StringBuilder();
        for (String word : curseWords) {
            if (!whitelist.contains(word)) {
                if (patternBuilder.length() > 0) {
                    patternBuilder.append("|");
                }
                final StringBuilder wordPattern = new StringBuilder();
                for (char c : word.toCharArray()) {
                    wordPattern.append("[").append(getAllSupportedVariants(c)).append("][\\W_]*");
                }
                patternBuilder.append("(?i)\\b").append(wordPattern).append("\\b");
            }
        }
        return patternBuilder.toString();
    }

    private String getAllSupportedVariants(final char c) {
        switch (Character.toLowerCase(c)) {
            case 'a':
                return "a𝒶𝓪𝕒𝖺𝐚𝗮𝙖𝚊ⓐ⒜ａΑаᴀ𝔞@4ɐäãåāăąȧảạầấậắặ";
            case 'b':
                return "b𝒷𝓫𝕓𝐛𝗯𝙗𝚋ⓑ⒝ｂ8ßḃɓƀ";
            case 'c':
                return "c𝒸𝓬𝕔𝐜𝗰𝙘𝚌ⓒ⒞ｃ¢©çćĉċčɔ";
            case 'd':
                return "d𝒹𝓭𝕕𝐝𝗱𝙙𝚍ⓓ⒟ｄḋđďɖɗ";
            case 'e':
                return "e𝒆𝓮𝕖𝖊𝐞𝗲𝙚𝚎ⓔ⒠ｅ3€℮èéêëēĕėęěȅȇẹẻếềệể";
            case 'f':
                return "f𝒻𝓯𝕗𝐟𝗳𝙛𝚏ⓕ⒡ｆƒḟ";
            case 'g':
                return "g𝓰𝓭𝕘𝐠𝗴𝙜𝚐ⓖ⒢ｇ6ɡģǧǵḡ";
            case 'h':
                return "h𝒽𝓱𝕙𝐡𝗵𝙝𝚑ⓗ⒣ｈḣḥɦ";
            case 'i':
                return "i𝒾𝓲𝕚𝐢𝗶𝙞𝚒ⓘ⒤ｉ¡1!|ılíìîïĩīĭįɨḭ";
            case 'j':
                return "j𝒿𝓳𝕛𝐣𝗷𝙟𝚓ⓙ⒥ｊĵǰ";
            case 'k':
                return "k𝓀𝓴𝕜𝐤𝗸𝙠𝚔ⓚ⒦ｋḱķǩḳ";
            case 'l':
                return "l𝓁𝓵𝕝𝐥𝗹𝙡𝚕ⓛ⒧ｌ1|7łľļḷḻ";
            case 'm':
                return "m𝓂𝓶𝕞𝐦𝗺𝙢𝚖ⓜ⒨ｍḿṁᵯ";
            case 'n':
                return "n𝓃𝓷𝕟𝐧𝗻𝙣𝚗ⓝ⒩ｎñńňņṅṇ";
            case 'o':
                return "o𝓸𝓸𝕠𝐨𝗼𝙤𝚘ⓞ⒪ｏΟоᴑօ0öòóôõøōŏőơọỏớờợở";
            case 'p':
                return "p𝓅𝓹𝕡𝐩𝗽𝙥𝚙ⓟ⒫ｐṕṗ";
            case 'q':
                return "q𝓆𝓺𝕢𝐪𝗾𝙦𝚚ⓠ⒬ｑɋʠ";
            case 'r':
                return "r𝓇𝓻𝕣𝐫𝗿𝙧𝚛ⓡ⒭ｒŕřŗṙṛ";
            case 's':
                return "s𝓈𝓼𝕤𝐬𝗌𝙨𝚜ⓢ⒮ｓ5$šşśṡṣ";
            case 't':
                return "t𝓉𝓽𝕥𝐭𝗍𝙩𝚝ⓣ⒯ｔ7+ťţṫṭ";
            case 'u':
                return "u𝓊𝓾𝕦𝐮𝗎𝙪𝚞ⓤ⒰ｕüùúûũūŭůűųưụủứ";
            case 'v':
                return "v𝓋𝓿𝕧𝐯𝗏𝙫𝚟ⓥ⒱ｖṽṿʋ";
            case 'w':
                return "w𝓌𝔀𝕨𝐰𝗐𝙬𝚠ⓦ⒲ｗẁẃŵẇẉ";
            case 'x':
                return "x𝓍𝔁𝕩𝐱𝗑𝙭𝚡ⓧ⒳ｘẋẍ";
            case 'y':
                return "y𝓎𝔂𝕪𝐲𝗒𝙮𝚢ⓨ⒴ｙýỳŷÿẏẙỵ";
            case 'z':
                return "z𝓏𝔃𝕫𝐳𝗓𝙯𝚣ⓩ⒵ｚźżžẓẕƶ";
            case '0':
                return "0⓪０𝟢o";
            case '1':
                return "1①１𝟣|i";
            case '2':
                return "2②２𝟤";
            case '3':
                return "3③３𝟥";
            case '4':
                return "4④４𝟦";
            case '5':
                return "5⑤５𝟧";
            case '6':
                return "6⑥６𝟨";
            case '7':
                return "7⑦７𝟩";
            case '8':
                return "8⑧８𝟪";
            case '9':
                return "9⑨９𝟫";
            default:
                return String.valueOf(c);
        }
    }


    public String removeAccents(final String message) {
        return Normalizer.normalize(message, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }

    public boolean containsCurseWord(final String message) {
        String normalizedMessage = removeAccents(message.toLowerCase());
        Matcher matcher = curseWordPattern.matcher(normalizedMessage);
        return matcher.find();
    }

}