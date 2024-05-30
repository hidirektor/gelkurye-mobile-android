package me.t3sl4.kurye.Util.HTTP;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.t3sl4.kurye.Util.Utils;

public final class JsonRequest implements HttpRequest {
    private final int method;
    private final String endpoint;
    private final JSONObject params;
    private final boolean requiresAuth;
    private final HTTPResponseListener listener;
    private final TokenManager tokenManager;
    private final Context context;

    public JsonRequest(final int method, final String endpoint, final JSONObject params, final boolean requiresAuth, final HTTPResponseListener listener, final TokenManager tokenManager, final Context context) {
        this.method = method;
        this.endpoint = endpoint;
        this.params = params;
        this.requiresAuth = requiresAuth;
        this.listener = listener;
        this.tokenManager = tokenManager;
        this.context = context;
    }

    @Override
    public void execute(final RequestQueue requestQueue) {
        final String url = Utils.getBaseURL(context) + endpoint;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, params,
                response -> {
                    try {
                        listener.onSuccess(response);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    if (tokenManager != null) {
                        tokenManager.saveTokensIfNeeded(response);
                    }
                }, listener::onError) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                if (requiresAuth && tokenManager != null) {
                    final String accessToken = tokenManager.getAccessToken();
                    if (accessToken != null) {
                        headers.put("Authorization", "Bearer " + accessToken);
                    }
                }
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
