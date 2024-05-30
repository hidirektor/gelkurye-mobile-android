package me.t3sl4.kurye.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;
import com.irozon.sneaker.Sneaker;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import me.t3sl4.kurye.BuildConfig;
import me.t3sl4.kurye.Model.User.Carrier;
import me.t3sl4.kurye.SplashActivity;
import me.t3sl4.kurye.UI.Screens.General.Dashboard;
import me.t3sl4.kurye.Util.LocalData.SharedPreferencesManager;

public class Utils {
    private static final String PREF_NAME = "MyProfilePref";
    private static final String KEY_PROFILE_GSON = "profileGSON";

    public static String getBaseURL() {
        return BuildConfig.BASE_URL;
    }

    public static void setLocale(Context context, String newLanguage) {
        Resources activityRes = context.getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(newLanguage);
        activityConf.setLocale(newLocale);
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = context.getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());
    }

    public static void loadNewTranslations(Context context) {
        String currentLanguage = SharedPreferencesManager.getSharedPref("language", context, "en");

        setLocale(context, currentLanguage);
    }

    public static String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    public static Bitmap decodeImage(String input) {
        byte[] decodedByte = Base64.decode(input, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

        return bitmap;
    }

    public static Carrier getFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String profileJson = sharedPreferences.getString(KEY_PROFILE_GSON, "");
        return new Gson().fromJson(profileJson, Carrier.class);
    }
}
