package me.t3sl4.gelkurye;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.Screens.MainActivity;
import me.t3sl4.gelkurye.Screens.OnBoard.OnBoard1;
import me.t3sl4.gelkurye.Screens.User.Profile;
import me.t3sl4.gelkurye.Util.Util.Data.SharedPreferencesManager;
import me.t3sl4.gelkurye.Util.Util.Language.LanguageConverter;

public class SplashActivity extends AppCompatActivity {
    private final int WAITING_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean isFirstTime = SharedPreferencesManager.getSharedPref("isFirstTime", this, false);

        checkLanguage();

        if (isFirstTime) {
            setupOnboarding();
        } else {
            redirectToMainActivity();
        }
    }

    private void setupOnboarding() {
        Intent intent = new Intent(SplashActivity.this, OnBoard1.class);
        startActivity(intent);
        finish();
        SharedPreferencesManager.writeSharedPref("isFirstTime", false, this);
    }

    private void redirectToMainActivity() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, WAITING_TIME);
    }

    private void checkLanguage() {
        String currentLanguage = SharedPreferencesManager.getSharedPref("language", SplashActivity.this, "en");

        if(currentLanguage == null || currentLanguage.isEmpty()) {
            SharedPreferencesManager.writeSharedPref("language", "tr", SplashActivity.this);
            currentLanguage = "tr";
        }

        LanguageConverter.setLocale(SplashActivity.this, currentLanguage);
    }
}
