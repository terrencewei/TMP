package Util;

import java.lang.reflect.Field;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;

/**
 * 
 * json utils, with Google GSON utils
 * 
 * @author: terrencewei
 * @version: 1.0, Feb 8, 2017
 */
public class BCJsonUtil {

    private static String     SPACE = "    ";
    private static final Gson GSON  = new GsonBuilder().create();



    /**
     * 
     * convert object to json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return GSON.toJson(JsonNull.INSTANCE);
        }
        return GSON.toJson(obj);
    }



    /**
     * 
     * parse json to object
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }



    /**
     * format the json string
     *
     * @param json
     * @return
     */
    public static String formatJson(Object pObj) {
        return formatJson(toJson(pObj));
    }



    /**
     * format the json string
     *
     * @param json
     * @return
     */
    public static String formatJson(String json) {
        if (json == null || json.length() <= 0) {
            return "null";
        }
        StringBuffer result = new StringBuffer();

        int length = json.length();
        int number = 0;
        char key = 0;

        for (int i = 0; i < length; i++) {
            key = json.charAt(i);

            if ((key == '[') || (key == '{')) {
                if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }

                result.append(key);

                result.append('\n');

                number++;
                result.append(indent(number));

                continue;
            }

            if ((key == ']') || (key == '}')) {
                result.append('\n');

                number--;
                result.append(indent(number));

                result.append(key);

                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }

                continue;
            }

            if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }

            result.append(key);
        }

        return result.toString();
    }



    private static String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(SPACE);
        }
        return result.toString();
    }

    /**
     * 
     * ATG class fields naming strategy
     * 
     * for example, ATG use: "String mDisplayName;"
     * 
     * instead of: "String displayName;"
     * 
     * @author: terrencewei
     * @version: 1.0, Feb 8, 2017
     */
    private static class ATGFieldNamingStrategy implements FieldNamingStrategy {
        @Override
        public String translateName(Field f) {
            String fieldName = f.getName();
            if (fieldName != null && fieldName.startsWith("m")) {
                fieldName = fieldName.replaceFirst("m", "");
                Character firstChar = fieldName.charAt(0);
                Character lowerCaseFirstChar = Character.toLowerCase(firstChar);
                return fieldName.replaceFirst(firstChar.toString(), lowerCaseFirstChar.toString());
            }
            return fieldName;
        }
    }
}
