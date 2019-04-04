package Util;

/**
 * Created by terrence on 2018/07/23.
 */
public class EllipsizeUtil {

    private final static String NON_THIN = "[^iIl1\\.,']";
    private final static String ELLIPSIS = "...";



    /**
     * This algorithm is copied from stackoverflow below, and do a small NPE fix.
     *
     * @param text                 origin text content
     * @param max                  max output length of text
     * @param includeThinCharacter if true, will use regular expression to regard some of the characters as thin
     *                             characters, these characters will be regard as half length when calculate the output
     *                             text content according to given max length
     * @return
     * @https://stackoverflow.com/questions/3597550/ideal-method-to-truncate-a-string-with-ellipsis
     */
    public static String ellipsize(String text, int max, boolean includeThinCharacter) {

        int ellipsisLn = ELLIPSIS.length();

        // Small NPE fix
        if (text == null || max < ellipsisLn) {
            return null;
        }

        if (textWidth(text, includeThinCharacter) <= max) {
            return text;
        }

        // Start by chopping off at the word before max
        // This is an over-approximation due to thin-characters...
        int end = text.lastIndexOf(' ', max - ellipsisLn);

        // Just one long word. Chop it off.
        if (end == -1) {
            return text.substring(0, max - ellipsisLn) + ELLIPSIS;
        }

        // Step forward as long as textWidth allows.
        int newEnd = end;
        do {
            end = newEnd;
            newEnd = text.indexOf(' ', end + 1);

            // No more spaces.
            if (newEnd == -1) {
                newEnd = text.length();
            }

        } while (textWidth(text.substring(0, newEnd) + ELLIPSIS, includeThinCharacter) < max);

        return text.substring(0, end) + ELLIPSIS;
    }



    private static int textWidth(String str, boolean includeThinCharacter) {
        if (includeThinCharacter) {
            return (str.length() - str.replaceAll(NON_THIN, "").length() / 2);
        }
        return str.length();
    }
}