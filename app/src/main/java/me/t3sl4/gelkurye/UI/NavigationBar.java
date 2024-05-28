package me.t3sl4.gelkurye.UI;

import android.app.Activity;
import android.view.View;

public class NavigationBar {
    public void hideNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void showNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
}