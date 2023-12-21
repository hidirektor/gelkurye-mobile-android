package me.t3sl4.gelkurye.Util.Util.Req;

import me.t3sl4.gelkurye.Util.Util.Singleton;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GET {

    public static void sendGetRequest(String url, Callback callback) {
        OkHttpClient client = Singleton.getInstance();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
