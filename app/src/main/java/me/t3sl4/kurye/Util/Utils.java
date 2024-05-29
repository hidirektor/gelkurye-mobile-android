package me.t3sl4.kurye.Util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import me.t3sl4.kurye.UI.NavigationBar;

public class Utils {

    private static Utils instance;
    private NavigationBar navigationBar;
    private ImageUtil imageUtil;
    private LanguageConverter languageConverter;

    private Utils() {
        navigationBar = new NavigationBar();
        imageUtil = new ImageUtil();
        languageConverter = new LanguageConverter();
    }

    public static synchronized Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public NavigationBar getNavigationBar() {
        return navigationBar;
    }

    public ImageUtil getImageUtil() {
        return imageUtil;
    }

    public LanguageConverter getLanguageConverter() {
        return languageConverter;
    }

    public static String getBaseURL(Context context) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return ai.metaData.getString("BASE_URL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
