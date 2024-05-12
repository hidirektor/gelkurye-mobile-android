package me.t3sl4.gelkurye.Util.Util.Req.Type;

import me.t3sl4.gelkurye.Util.Util.Req.Singleton;
import okhttp3.*;

public class DELETE {

    public static void sendDeleteRequest(String url, Callback callback) {
        OkHttpClient client = Singleton.getInstance();

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        client.newCall(request).enqueue(callback);
    }
}
