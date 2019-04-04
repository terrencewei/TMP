package randomstring;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

/**
 * Created by terrence on 2018/08/30.
 */
public class ApacheCommonTextRandomStringGenerator {

    private static final RandomStringGenerator GENERATOR = new RandomStringGenerator.Builder().withinRange('0', 'z')
            .filteredBy(CharacterPredicates.ARABIC_NUMERALS, CharacterPredicates.ASCII_LOWERCASE_LETTERS)
            .usingRandom(new SecureRandomProvider()).build();



    public static String generate(int length) {
        return GENERATOR.generate(length);
    }

}