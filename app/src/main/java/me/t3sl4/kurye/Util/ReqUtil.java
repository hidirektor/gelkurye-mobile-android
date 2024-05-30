package me.t3sl4.kurye.Util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import me.t3sl4.kurye.Util.HTTP.HTTPHelper;
import me.t3sl4.kurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.kurye.Util.HTTP.TokenManager;

public class ReqUtil {
    private static HTTPHelper httpHelper;
    private static TokenManager tokenManager;

    public interface LoginCallback {
        void onSuccess();
        void onError();
    }

    public static void loginReq(Context context, JSONObject params, LoginCallback callback) {
        String baseURL = Utils.getBaseURL(context);
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        if (tokenManager.getAccessToken() != null && tokenManager.getRefreshToken() != null) {
            tokenManager.clearTokens();
        }

        httpHelper.makeRequest(
                Request.Method.POST,
                baseURL + "/api/v1/auth/login",
                params,
                false,
                new HTTPResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        tokenManager.saveTokensIfNeeded(response);
                        callback.onSuccess();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        callback.onError();
                    }
                },
                null
        );
    }

    public static boolean registerReq(Context context) {
        String baseURL = Utils.getBaseURL(context);

        return false;
    }
}
