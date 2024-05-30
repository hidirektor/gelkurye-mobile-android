package me.t3sl4.kurye;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.MainActivity;
import me.t3sl4.kurye.UI.Screens.OnBoard.OnBoard1;
import me.t3sl4.kurye.Util.LocalData.SharedPreferencesManager;
import me.t3sl4.kurye.Util.Utils;

public class SplashActivity extends AppCompatActivity {
    private final int WAITING_TIME = 2000;
    boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        NavigationBarUtil.hideNavigationBar(this);

        isFirstTime = SharedPreferencesManager.getSharedPref("isFirstTime", this, true);

        checkLanguage();

        String accessToken = SharedPreferencesManager.getSharedPref("accessToken", this, "");
        String refreshToken = SharedPreferencesManager.getSharedPref("refreshToken", this, "");

        if (isFirstTime) {
            setupOnboarding();
        } else {
            if(accessToken != null && refreshToken != null) {
                //token ile kullanıcı verisi alınıp
            } else {
                redirectToSelectionScreen();
            }
        }
    }

    private void setupOnboarding() {
        Intent intent = new Intent(SplashActivity.this, OnBoard1.class);
        startActivity(intent);
        finish();
        SharedPreferencesManager.writeSharedPref("isFirstTime", false, this);
    }

    private void redirectToSelectionScreen() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, WAITING_TIME);
    }

    private void checkLanguage() {
        String currentLanguage;

        if(isFirstTime) {
            currentLanguage = "tr";
        } else {
            currentLanguage = SharedPreferencesManager.getSharedPref("language", SplashActivity.this, "tr");
        }

        Utils.setLocale(SplashActivity.this, currentLanguage);
    }
}
