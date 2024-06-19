package me.t3sl4.kurye.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.irozon.sneaker.Sneaker;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Locale;

import me.t3sl4.kurye.BuildConfig;
import me.t3sl4.kurye.Model.User.Carrier;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Screens.General.Dashboard;
import me.t3sl4.kurye.UI.Screens.MainActivity;
import me.t3sl4.kurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.kurye.Util.LocalData.SharedPreferencesManager;

public class Utils {
    private static final String PREF_NAME = "MyProfilePref";
    private static final String KEY_PROFILE_GSON = "profileGSON";
    private static final String KEY_NIGHT_MODE = "nightMode";

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

    public static String encodeImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static Bitmap decodeImage(String encodedImage) {
        byte[] decodedImageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.length);
    }

    public static Carrier getFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String profileJson = sharedPreferences.getString(KEY_PROFILE_GSON, "");
        return new Gson().fromJson(profileJson, Carrier.class);
    }

    public static boolean checkPermissions(Context context, String[] requestedPermissions, int requestCode) {
        boolean allPermissionsDenied = true;
        for (String permission : requestedPermissions) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                allPermissionsDenied = false;
                break;
            }
        }

        if (allPermissionsDenied) {
            showCustomDialog(context, requestedPermissions, requestCode);
            return false;
        } else {
            return true;
        }
    }

    private static void showCustomDialog(Context context, String[] requestedPermissions, int requestCode) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.permission, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setView(dialogView);

        Button yesButton = dialogView.findViewById(R.id.authorizeButton);
        Button noButton = dialogView.findViewById(R.id.denyButton);

        AlertDialog alert = alertBuilder.create();

        alertBuilder.setCancelable(true);
        yesButton.setOnClickListener(view -> {
            ActivityCompat.requestPermissions((Activity) context, requestedPermissions, requestCode);
            alert.dismiss();
        });

        noButton.setOnClickListener(view -> {
            Sneaker.with((Activity)context).setTitle("Hata !").setMessage("Lokasyon izni gereklidir. Lütfen yeniden başlatın!").sneakError();
            alert.dismiss();
        });

        alert.show();
    }

    public static Object getData(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String profileJson = sharedPreferences.getString(KEY_PROFILE_GSON, "");
        Carrier profile = new Gson().fromJson(profileJson, Carrier.class);

        try {
            Field field = Carrier.class.getDeclaredField(key);
            field.setAccessible(true);
            return field.get(profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setData(Context context, String key, Object value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String profileJson = sharedPreferences.getString(KEY_PROFILE_GSON, "");
        Carrier profile = new Gson().fromJson(profileJson, Carrier.class);

        try {
            Field field = Carrier.class.getDeclaredField(key);
            field.setAccessible(true);
            field.set(profile, value);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_PROFILE_GSON, new Gson().toJson(profile));
            editor.apply();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setNightMode(Context context, boolean isEnabled) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_NIGHT_MODE, isEnabled);
        editor.apply();
    }

    public static boolean isNightModeEnabled(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_NIGHT_MODE, false);
    }

    public static void applyNightMode(Context context) {
        if (isNightModeEnabled(context)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static void logoutAndNotify(final Context context, final Activity activity) {
        Intent loginIntent = new Intent(activity, MainActivity.class);

        ReqUtil.logoutReq(activity, new HTTPResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                activity.startActivity(loginIntent);
                activity.finish();
            }

            @Override
            public void onError(VolleyError error) {
                /*Sneaker.with(activity)
                        .setTitle("Hata !")
                        .setMessage("Çıkış yapılamadı, lütfen tekrar deneyin.")
                        .sneakError();*/
                activity.startActivity(loginIntent);
                activity.finish();
            }
        });
    }
}
