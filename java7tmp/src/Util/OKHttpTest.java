package Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpTest {

    public static void main(String[] args) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://becntest.hover.to/api/v2/users/profile")
                .addHeader("Authorization", "Bearer 0958ccf98f16e72ee55a265270622dada4a77fbd29a32d23f6333530bef80476")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        System.out.println(response);
    }

}
