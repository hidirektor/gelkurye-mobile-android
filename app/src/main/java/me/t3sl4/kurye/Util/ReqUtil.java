package me.t3sl4.kurye.Util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import me.t3sl4.kurye.Model.User.Carrier;
import me.t3sl4.kurye.Util.HTTP.HTTPHelper;
import me.t3sl4.kurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.kurye.Util.HTTP.TokenManager;

public class ReqUtil {
    private static HTTPHelper httpHelper;
    private static TokenManager tokenManager;

    public interface GeneralCallback {
        void onSuccess();
        void onError();
    }

    public interface ProfileCallback {
        void onSuccess(Carrier profile);
        void onError();
    }

    public static void loginReq(Context context, JSONObject params, GeneralCallback callback) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        if (tokenManager.getAccessToken() != null && tokenManager.getRefreshToken() != null) {
            tokenManager.clearTokens();
        }

        httpHelper.makeRequest(
                Request.Method.POST,
                "auth/login",
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

    public static void registerReq(Context context, JSONObject params, HTTPResponseListener listener) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        httpHelper.makeRequest(
                Request.Method.POST,
                "auth/register",
                params,
                false,
                listener,
                tokenManager
        );
    }

    public static void getProfileReq(Context context, String phoneNumber, ProfileCallback callback) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        JSONObject params = new JSONObject();
        try {
            params.put("phoneNumber", phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpHelper.makeRequest(
                Request.Method.POST,
                "user/getProfile",
                params,
                true,
                new HTTPResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            JSONObject userObject = response.getJSONObject("user");
                            int id = userObject.getInt("id");
                            String userID = userObject.getString("userID");
                            String userName = userObject.getString("userName");
                            String eMail = userObject.getString("eMail");
                            String userType = userObject.getString("userType");
                            String nameSurname = userObject.getString("NameSurname");
                            String phoneNumber = userObject.getString("phoneNumber");
                            String address = userObject.getString("address");
                            String password = userObject.getString("password");
                            String profilePhoto = userObject.getString("profilePhoto");
                            String relativeNameSurname = userObject.getString("relativeNameSurname");
                            String relativePhoneNumber = userObject.getString("relativePhoneNumber");
                            String registeredMerchant = userObject.getString("registeredMerchant");
                            String lastPasswordChange = userObject.getString("lastPasswordChange");
                            String createdAt = userObject.getString("createdAt");

                            JSONObject userDocumentsObject = response.getJSONObject("userDocuments");
                            int userDocumentsId = userDocumentsObject.getInt("id");
                            String userDocumentsUserID = userDocumentsObject.getString("userID");
                            String licenseFrontFace = userDocumentsObject.getString("licenseFrontFace");
                            String licenseBackFace = userDocumentsObject.getString("licenseBackFace");

                            JSONObject userPreferencesObject = response.getJSONObject("userPreferences");
                            int userPreferencesId = userPreferencesObject.getInt("id");
                            String userPreferencesUserID = userPreferencesObject.getString("userID");
                            boolean nightMode = userPreferencesObject.getBoolean("nightMode");
                            boolean selectedLanguage = userPreferencesObject.getBoolean("selectedLanguage");
                            String firstBreakTime = userPreferencesObject.getString("firstBreakTime");
                            String secondBreakTime = userPreferencesObject.getString("secondBreakTime");

                            JSONObject userRatingObject = response.getJSONObject("userRating");
                            int userRatingId = userRatingObject.getInt("id");
                            String userRatingUserID = userRatingObject.getString("userID");
                            int userRating = userRatingObject.getInt("userRating");

                            Carrier profile = new Carrier(id, userID, userName, eMail, userType, nameSurname, phoneNumber, address,
                                    password, profilePhoto, relativeNameSurname, relativePhoneNumber, lastPasswordChange, createdAt,
                                    licenseFrontFace, licenseBackFace, nightMode, selectedLanguage, firstBreakTime, secondBreakTime,
                                    userRating);

                            // Save profile Gson to SharedPreferences
                            profile.saveToSharedPreferences(context);

                            callback.onSuccess(profile);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        callback.onError();
                    }
                },
                tokenManager
        );
    }

    public static void logoutReq(Context context, HTTPResponseListener callback) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        String refreshToken = tokenManager.getRefreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            return;
        }

        JSONObject params = new JSONObject();
        try {
            params.put("token", refreshToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpHelper.makeRequest(
                Request.Method.POST,
                "auth/logout",
                params,
                true,
                new HTTPResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        tokenManager.clearTokens();
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Log.d("Logout", error.toString());
                        callback.onError(error);
                    }
                },
                tokenManager
        );
    }

    public static void updateProfileReq(Context context, GeneralCallback callback) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        Carrier profile = Utils.getFromSharedPreferences(context);

        JSONObject userData = new JSONObject();
        JSONObject userDocumentsData = new JSONObject();
        JSONObject userPreferencesData = new JSONObject();
        JSONObject requestBody = new JSONObject();

        try {
            userData.put("userID", profile.getUserID());
            userData.put("userName", profile.getUserName());
            userData.put("eMail", profile.geteMail());

            userDocumentsData.put("licenseFrontFace", profile.getLicenseFrontFace());
            userDocumentsData.put("licenseBackFace", profile.getLicenseBackFace());

            userPreferencesData.put("nightMode", profile.isNightMode());
            userPreferencesData.put("selectedLanguage", profile.isSelectedLanguage());
            userPreferencesData.put("firstBreakTime", profile.getFirstBreakTime());
            userPreferencesData.put("secondBreakTime", profile.getSecondBreakTime());

            requestBody.put("phoneNumber", profile.getPhoneNumber());
            requestBody.put("userData", userData);
            requestBody.put("userDocumentsData", userDocumentsData);
            requestBody.put("userPreferencesData", userPreferencesData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpHelper.makeRequest(
                Request.Method.POST,
                "user/updateProfile",
                requestBody,
                true,
                new HTTPResponseListener() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        callback.onSuccess();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        callback.onError();
                    }
                },
                tokenManager
        );
    }
}
