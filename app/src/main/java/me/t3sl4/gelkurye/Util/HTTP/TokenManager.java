package me.t3sl4.gelkurye.Util.HTTP;

import android.content.Context;

import org.json.JSONObject;

import me.t3sl4.gelkurye.LocalData.SharedPreferencesManager;

public final class TokenManager {
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private final Context context;

    public TokenManager(final Context context) {
        this.context = context.getApplicationContext();
    }

    public void saveTokensIfNeeded(final JSONObject response) {
        try {
            if (response.has(ACCESS_TOKEN) && response.has(REFRESH_TOKEN)) {
                saveTokens(response.getString(ACCESS_TOKEN), response.getString(REFRESH_TOKEN));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void saveTokens(final String accessToken, final String refreshToken) {
        SharedPreferencesManager.writeSharedPref(ACCESS_TOKEN, accessToken, context);
        SharedPreferencesManager.writeSharedPref(REFRESH_TOKEN, refreshToken, context);
    }

    public void clearTokens() {
        SharedPreferencesManager.writeSharedPref(ACCESS_TOKEN, null, context);
        SharedPreferencesManager.writeSharedPref(REFRESH_TOKEN, null, context);
    }

    public String getAccessToken() {
        return SharedPreferencesManager.getSharedPref(ACCESS_TOKEN, context, null);
    }

    public String getRefreshToken() {
        return SharedPreferencesManager.getSharedPref(REFRESH_TOKEN, context, null);
    }
}
