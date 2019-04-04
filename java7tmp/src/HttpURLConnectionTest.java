import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionTest {

    public static void main(String[] aa) throws Exception {
        HttpURLConnection httpConn = null;
        String targetUrl = "https://hover.to/api/oauth/token?grant_type=refresh_token&client_id=xxxxx&client_secret=xxxxx&redirect_uri=urn:ietf:wg:oauth:2.0:oob&refresh_token=xxxx";
        try {
            URL url = new URL(targetUrl);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setConnectTimeout(5000);
            httpConn.setReadTimeout(5000);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setInstanceFollowRedirects(false);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Charset", "utf-8");
            httpConn.connect();
            System.out.println(httpConn.getResponseCode());
            System.out.println(httpConn.getResponseMessage());
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }
}