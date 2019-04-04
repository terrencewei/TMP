import org.apache.commons.lang3.StringUtils;

public class HttpsURLConnectionTest {
    public static void main(String[] aa) throws Exception {
        test("");
        test(" ");
        test("true");
        test("false");
        test("TRUE");
        test("FALSE");
        test(null);
    }



    public static void test(String a) {
        System.out.println(t(a) == Boolean.parseBoolean(a));
    }



    public static boolean t(String showHoverAttrs) {
        if (StringUtils.isNotBlank(showHoverAttrs) && "true".equalsIgnoreCase(showHoverAttrs)) {
            return true;
        }
        return false;

    }
}
