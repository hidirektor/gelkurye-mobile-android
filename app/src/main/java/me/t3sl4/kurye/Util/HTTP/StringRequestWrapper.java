package me.t3sl4.kurye.Util.HTTP;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.t3sl4.kurye.Util.Utils;

public final class StringRequestWrapper implements HttpRequest {
    private final int method;
    private final String endpoint;
    private final HTTPResponseListener listener;
    private final TokenManager tokenManager;
    private final Context context;

    public StringRequestWrapper(final int method, final String endpoint, final HTTPResponseListener listener, final TokenManager tokenManager, final Context context) {
        this.method = method;
        this.endpoint = endpoint;
        this.listener = listener;
        this.tokenManager = tokenManager;
        this.context = context;
    }

    @Override
    public void execute(final RequestQueue requestQueue) {
        final String url = Utils.getBaseURL(context) + endpoint;
        final StringRequest stringRequest = new StringRequest(method, url,
                response -> {
                    try {
                        listener.onSuccess(new JSONObject());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, listener::onError) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                if (tokenManager != null) {
                    final String accessToken = tokenManager.getAccessToken();
                    if (accessToken != null) {
                        headers.put("Authorization", "Bearer " + accessToken);
                    }
                }
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
}
