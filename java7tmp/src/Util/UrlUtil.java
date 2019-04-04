package Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by terrence on 2019/02/26.
 */
public class UrlUtil {

    public static void main(String[] a) {
        String b = "/search?Ntp=1&Ntt=Shing&Ntx=mode+matchall#cccc";
        System.out.println(b);
        System.out.println(addParam(b, "Ntt", "aaa*"));
    }



    public static String addParam(String pUrl, String pParamName, String pParamValue) {
        if (StringUtils.isBlank(pUrl) || StringUtils.isBlank(pParamName) || StringUtils.isBlank(pParamValue)) {
            return "";
        }
        StringBuilder urlBuilder = new StringBuilder(100);
        String hash = null;
        int hashIdx = pUrl.indexOf("#");
        if (hashIdx > 0) {
            hash = pUrl.substring(hashIdx + 1, pUrl.length());
            pUrl = pUrl.substring(0, hashIdx);
        }
        String[] urls = pUrl.split("\\?");
        urlBuilder.append(urls[0]);

        if (urls.length < 2) {
            return urlBuilder.toString();
        }

        urlBuilder.append("?");

        String queryString = getValidQueryString(urls[1]);
        String[] paramPairs = queryString.split("&");

        Map<String, String> paramMap = new HashMap<String, String>();
        for (String paramPair : paramPairs) {
            String[] paramEntry = paramPair.split("=");
            paramMap.put(paramEntry[0], paramEntry[1]);
        }
        paramMap.put(pParamName, pParamValue);

        urlBuilder.append(generateQueryString(paramMap));
        if (hash != null) {
            urlBuilder.append("#").append(hash);
        }
        return urlBuilder.toString();
    }



    public static String getValidQueryString(String pQueryString) {

        if (StringUtils.isBlank(pQueryString)) {
            return pQueryString;
        }

        StringBuilder strBuilder = new StringBuilder(60);
        pQueryString = pQueryString.replaceAll("\\?", "&");

        String[] queryparams = pQueryString.split("&");
        Map<String, String> parameterMap = new HashMap<String, String>();
        for (String queryparam : queryparams) {
            String[] paramVaulePair = queryparam.split("=");
            if (paramVaulePair.length >= 2) {
                parameterMap.put(paramVaulePair[0], paramVaulePair[paramVaulePair.length - 1]);
            }
        }

        for (Iterator<Entry<String, String>> it = parameterMap.entrySet().iterator(); it.hasNext(); ) {
            Entry<String, String> entry = it.next();
            strBuilder.append(entry.getKey());
            strBuilder.append("=");
            strBuilder.append(entry.getValue());
            strBuilder.append("&");
        }

        if (strBuilder.length() == 0) {
            return "";
        }

        return strBuilder.substring(0, strBuilder.length() - 1);
    }



    public static String generateQueryString(Map<String, String> pParams) {
        StringBuilder urlBuilder = new StringBuilder(100);

        for (Iterator<Entry<String, String>> it = pParams.entrySet().iterator(); it.hasNext(); ) {
            Entry<String, String> entry = it.next();
            urlBuilder.append(entry.getKey());
            urlBuilder.append("=");
            urlBuilder.append(entry.getValue());
            urlBuilder.append("&");
        }

        urlBuilder.delete(urlBuilder.length() - 1, urlBuilder.length());
        return urlBuilder.toString();
    }
}