package me.t3sl4.gelkurye.Util.Util.Req.Type;

import me.t3sl4.gelkurye.Util.Util.Req.Singleton;
import okhttp3.*;
import org.json.JSONObject;

public class PUT {

    public static void sendPutRequest(String url, JSONObject requestBody, Callback callback) {
        OkHttpClient client = Singleton.getInstance();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
