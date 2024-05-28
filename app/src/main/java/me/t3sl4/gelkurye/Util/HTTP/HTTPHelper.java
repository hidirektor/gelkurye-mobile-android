package me.t3sl4.gelkurye.Util.HTTP;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
import org.json.JSONObject;

public final class HTTPHelper {
    private static HTTPHelper instance;
    private final Unchecked<RequestQueue> requestQueue;
    private final Context context;

    private HTTPHelper(final Context context) {
        this.requestQueue = new Unchecked<>(new Sticky<>(() -> Volley.newRequestQueue(context.getApplicationContext())));
        this.context = context;
    }

    public static synchronized HTTPHelper getInstance(final Context context) {
        if (instance == null) {
            instance = new HTTPHelper(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue.value();
    }

    public void makeRequest(final int method, final String endpoint, final JSONObject params, final boolean requiresAuth, final HTTPResponseListener listener, final TokenManager tokenManager) {
        final HttpRequest request = new JsonRequest(
                method, endpoint, params, requiresAuth, listener, tokenManager, context
        );
        request.execute(getRequestQueue());
    }

    public void makeRequestWithoutHeaders(final int method, final String endpoint, final JSONObject params, final HTTPResponseListener listener) {
        makeRequest(method, endpoint, params, false, listener, null);
    }

    public void makeRequestWithoutBody(final int method, final String endpoint, final HTTPResponseListener listener, final TokenManager tokenManager) {
        final HttpRequest request = new StringRequestWrapper(
                method, endpoint, listener, tokenManager, context
        );
        request.execute(getRequestQueue());
    }
}