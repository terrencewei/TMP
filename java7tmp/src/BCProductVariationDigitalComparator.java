
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Define product variation comparator,which compare by digital in string.
 * @author: peteryue
 * @version: 1.0, Oct 24, 2018
 */
public class BCProductVariationDigitalComparator implements Comparator<String> {

    public static final Pattern DIGITAL_STRING = Pattern.compile("^(\\d+)");



    @Override
    public int compare(String pO1, String pO2) {
        if (StringUtils.isBlank(pO1)) {
            return 1;
        }
        Integer strDigital01 = null;
        Integer strDigital02 = null;
        Matcher matcher = DIGITAL_STRING.matcher("");
        matcher.reset(pO1);
        if (matcher.find()) {
            strDigital01 = Integer.parseInt(matcher.group(1));
        }
        matcher.reset(pO2);
        if (matcher.find()) {
            strDigital02 = Integer.parseInt(matcher.group(1));
        }
        if (strDigital01 == null) {
            return 1;
        }
        return strDigital01.compareTo(strDigital02);
    }

}
