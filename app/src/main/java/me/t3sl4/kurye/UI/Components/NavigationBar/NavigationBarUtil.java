package me.t3sl4.kurye.UI.Components.NavigationBar;

import android.app.Activity;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class NavigationBarUtil {
    public static void hideNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(activity.getWindow(), decorView);
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    public static void showNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(activity.getWindow(), decorView);
        windowInsetsController.show(WindowInsetsCompat.Type.navigationBars());
    }

    public static void hideNavigationBarOnDialog(BottomSheetDialog dialog) {
        if (dialog != null && dialog.getWindow() != null) {
            View decorView = dialog.getWindow().getDecorView();
            WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(dialog.getWindow(), decorView);
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
            windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }
    }
}