package Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by terrencewei on 2017/09/13.
 */
public class HttpRequestor {
    public static void main(String[] args) throws Exception {
        System.out.println(HttpRequestor.test2("https://becntest.hover.to/api/v2/jobs/6/measurements.pdf"));
    }

    public static void test(String url) throws Exception {

        URL localURL = new URL(url);

        HttpURLConnection httpURLConnection = (HttpURLConnection) localURL.openConnection();

        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("GET");

        // headers
        httpURLConnection.setRequestProperty("Authorization",
                "Bearer 105a4e1d1a6b7b746e3ca643f9c6497e6fd98675b81e577c73f6f4b1a25dc07c");

        httpURLConnection.connect();

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            if (httpURLConnection.getResponseCode() >= 300) {
                System.out.println(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
                inputStream = httpURLConnection.getErrorStream();
            } else {
                inputStream = httpURLConnection.getInputStream();
            }
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
            // out.close();

        }

        System.out.println("Raw response:");
        System.out.println(resultBuffer.toString());

    }



    public static String test2(String url) throws Exception {

        URL localURL = new URL(url);

        HttpURLConnection httpURLConnection = (HttpURLConnection) localURL.openConnection();

        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("GET");

        // key!!
        httpURLConnection.setInstanceFollowRedirects(false);

        // headers
        httpURLConnection.setRequestProperty("Authorization",
                "Bearer 105a4e1d1a6b7b746e3ca643f9c6497e6fd98675b81e577c73f6f4b1a25dc07c");

        httpURLConnection.connect();

        try {
            if (httpURLConnection.getResponseCode() == 302) {
                String location = httpURLConnection.getHeaderField("Location");
                return location;
            }

        } finally {

            httpURLConnection.disconnect();

        }
        return null;
    }

}
