package randomstring;

/**
 * Created by terrence on 2018/08/30.
 */

import java.util.Random;

/**
 * From https://stackoverflow.com/a/157202/8268335
 *
 * example:
 *
 * System.out.println(RandomString2.generate(100));
 */
public class RandomString2 {

    private static final String AB  = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static       Random rnd = new Random();



    private static String generate(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

}