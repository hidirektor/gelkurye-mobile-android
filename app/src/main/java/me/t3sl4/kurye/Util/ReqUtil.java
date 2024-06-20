package me.t3sl4.kurye.Util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import me.t3sl4.kurye.Model.User.UserModel;
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
        void onSuccess(UserModel profile);
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

                            JSONObject userPreferencesObject = response.getJSONObject("userPreferences");
                            boolean nightMode = userPreferencesObject.getBoolean("nightMode");
                            String selectedLanguage = userPreferencesObject.getString("selectedLanguage");
                            String firstBreakTime = userPreferencesObject.getString("firstBreakTime");
                            String secondBreakTime = userPreferencesObject.getString("secondBreakTime");

                            UserModel profile;

                            if(userType.equals("CARRIER")) {
                                JSONObject userDocumentsObject = response.getJSONObject("userDocuments");
                                String licenseFrontFace = userDocumentsObject.getString("licenseFrontFace");
                                String licenseBackFace = userDocumentsObject.getString("licenseBackFace");

                                JSONObject userRatingObject = response.getJSONObject("userRating");
                                int userRating = userRatingObject.getInt("userRating");

                                profile = new UserModel(id, userID, userName, eMail, userType, nameSurname, phoneNumber, address,
                                        password, profilePhoto, relativeNameSurname, relativePhoneNumber, lastPasswordChange, createdAt,
                                        licenseFrontFace, licenseBackFace, nightMode, selectedLanguage, firstBreakTime, secondBreakTime,
                                        userRating);
                            } else {
                                JSONObject userMerchantObject = response.getJSONObject("Merchant");
                                String merchantID = userMerchantObject.getString("merchantID");
                                String merchantName = userMerchantObject.getString("merchantName");
                                String merchantAddress = userMerchantObject.getString("merchantAddress");
                                String merchantPhoneNumber = userMerchantObject.getString("contactNumber");

                                JSONObject userMerchantsAPIObject = response.optJSONObject("MerchantAPI");
                                String trendyolSupplierID = null;
                                String trendyolAPIKey = null;
                                String trendyolAPISecretKey = null;
                                String getirYemekMerchantToken = null;
                                String yemekSepetiUsername = null;
                                String yemekSepetiPassword = null;

                                if (userMerchantsAPIObject != null) {
                                    trendyolSupplierID = userMerchantsAPIObject.optString("trendyolSupplierID", null);
                                    trendyolAPIKey = userMerchantsAPIObject.optString("trendyolAPIKey", null);
                                    trendyolAPISecretKey = userMerchantsAPIObject.optString("trendyolAPISecretKey", null);
                                    getirYemekMerchantToken = userMerchantsAPIObject.optString("getirYemekMerchantToken", null);
                                    yemekSepetiUsername = userMerchantsAPIObject.optString("yemekSepetiUsername", null);
                                    yemekSepetiPassword = userMerchantsAPIObject.optString("yemekSepetiPassword", null);
                                }

                                profile = new UserModel(id, userID, userName, eMail, userType, nameSurname, phoneNumber, address,
                                        password, profilePhoto, relativeNameSurname, relativePhoneNumber, lastPasswordChange, createdAt,
                                        nightMode, selectedLanguage, firstBreakTime, secondBreakTime);

                                profile.setMerchantID(merchantID);
                                profile.setMerchantName(merchantName);
                                profile.setMerchantAddress(merchantAddress);
                                profile.setMerchantPhoneNumber(merchantPhoneNumber);

                                profile.setTrendyolSupplierID(trendyolSupplierID);
                                profile.setTrendyolAPIKey(trendyolAPIKey);
                                profile.setTrendyolAPISecretKey(trendyolAPISecretKey);
                                profile.setGetirYemekMerchantToken(getirYemekMerchantToken);
                                profile.setYemekSepetiUsername(yemekSepetiUsername);
                                profile.setYemekSepetiPassword(yemekSepetiPassword);
                            }

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
                        Log.d("getProfile-Error", error.toString());
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
                        tokenManager.clearTokens();
                        callback.onError(error);
                    }
                },
                tokenManager
        );
    }

    public static void updateProfileReq(Context context, GeneralCallback callback) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        UserModel profile = Utils.getFromSharedPreferences(context);

        JSONObject userData = new JSONObject();
        JSONObject userDocumentsData = new JSONObject();
        JSONObject userPreferencesData = new JSONObject();
        JSONObject requestBody = new JSONObject();

        try {
            userData.put("userName", profile.getUserName());
            userData.put("eMail", profile.geteMail());
            userData.put("userType", profile.getUserType());
            userData.put("NameSurname", profile.getNameSurname());
            userData.put("address", profile.getAddress());
            userData.put("password", profile.getPassword());
            userData.put("profilePhoto", profile.getProfilePhoto());
            userData.put("relativeNameSurname", profile.getRelativeNameSurname());
            userData.put("relativePhoneNumber", profile.getRelativePhoneNumber());
            userData.put("registeredMerchant", "");
            userData.put("lastPasswordChange", profile.getLastPasswordChange());

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

    public static void updatePreferencesReq(Context context, String phoneNumber, JSONObject preferencesData, GeneralCallback callback) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("phoneNumber", phoneNumber);
            requestBody.put("preferencesData", preferencesData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpHelper.makeRequest(
                Request.Method.POST,
                "user/updatePreferences",
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

    public static void getMerchantAPIReq(Context context, String phoneNumber, String userID, HTTPResponseListener listener) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        JSONObject params = new JSONObject();
        try {
            params.put("phoneNumber", phoneNumber);
            params.put("userID", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpHelper.makeRequest(
                Request.Method.POST,
                "merchant/getMerchantAPI",
                params,
                true,
                listener,
                tokenManager
        );
    }

    public static void updateMerchantAPIReq(Context context, String phoneNumber, String userID, JSONObject marketplaceAPI, HTTPResponseListener listener) {
        httpHelper = HTTPHelper.getInstance(context);
        tokenManager = new TokenManager(context);

        JSONObject params = new JSONObject();
        try {
            params.put("phoneNumber", phoneNumber);
            params.put("userID", userID);
            params.put("marketplace-API", marketplaceAPI);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        httpHelper.makeRequest(
                Request.Method.POST,
                "merchant/updateMerchantAPI",
                params,
                true,
                listener,
                tokenManager
        );
    }
}
