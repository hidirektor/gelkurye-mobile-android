package me.t3sl4.gelkurye.Util.Util.Req.Type;

import me.t3sl4.gelkurye.Util.Util.Req.Singleton;
import okhttp3.*;
import org.json.JSONObject;


public class POST {

    public static void sendPostRequest(String url, JSONObject requestBody, Callback callback) {
        OkHttpClient client = Singleton.getInstance();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void sendMultipartPostRequest(String url, MultipartBody requestBody, Callback callback) {
        OkHttpClient client = Singleton.getInstance();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
