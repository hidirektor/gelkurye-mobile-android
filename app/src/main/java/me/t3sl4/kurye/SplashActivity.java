package me.t3sl4.kurye;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import me.t3sl4.kurye.Model.User.UserModel;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.Carrier.CarrierDashboard;
import me.t3sl4.kurye.UI.Screens.General.Authentication.Login;
import me.t3sl4.kurye.UI.Screens.General.OnBoard.OnBoard1;
import me.t3sl4.kurye.UI.Screens.MainActivity;
import me.t3sl4.kurye.UI.Screens.Merchant.MerchantDashboard;
import me.t3sl4.kurye.Util.LocalData.SharedPreferencesManager;
import me.t3sl4.kurye.Util.ReqUtil;
import me.t3sl4.kurye.Util.Utils;
import me.t3sl4.kurye.kurye.UI.Components.Sneaker.Sneaker;

public class SplashActivity extends AppCompatActivity {
    private final int WAITING_TIME = 2000;
    boolean isFirstTime;

    private UserModel currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        NavigationBarUtil.hideNavigationBar(this);

        isFirstTime = SharedPreferencesManager.getSharedPref("isFirstTime", this, true);

        checkLanguage();
        checkNightMode();

        String accessToken = SharedPreferencesManager.getSharedPref("accessToken", this, "");
        String refreshToken = SharedPreferencesManager.getSharedPref("refreshToken", this, "");

        Log.d("Splash-Access", accessToken);
        Log.d("Splash-Refresh", refreshToken);

        if (isFirstTime) {
            setupOnboarding();
        } else {
            if(!Objects.equals(accessToken, "") && !Objects.equals(refreshToken, "")) {
                currentProfile = Utils.getFromSharedPreferences(this);
                if (currentProfile != null && !currentProfile.toJson().isEmpty()) {
                    refreshProfileData();

                    Intent dashboardIntent = null;
                    if ("CARRIER".equals(currentProfile.getUserType())) {
                        dashboardIntent = new Intent(SplashActivity.this, CarrierDashboard.class);
                    } else if ("MERCHANT".equals(currentProfile.getUserType())) {
                        dashboardIntent = new Intent(SplashActivity.this, MerchantDashboard.class);
                    }
                    startActivity(dashboardIntent);
                    finish();
                } else {
                    redirectToLoginScreen();
                }
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
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, WAITING_TIME);
    }

    private void redirectToLoginScreen() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, Login.class);
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

    private void checkNightMode() {
        Utils.applyNightMode(SplashActivity.this);
    }

    private void refreshProfileData() {
        ReqUtil.getProfileReq(SplashActivity.this, currentProfile.getPhoneNumber(), new ReqUtil.ProfileCallback() {
            @Override
            public void onSuccess(UserModel profile) {
                //TODO: Update profile
            }

            @Override
            public void onError() {
                Sneaker.with(SplashActivity.this).setTitle("Hata !").setMessage("Profil alınamadı!").sneakError();
            }
        });
    }
}
