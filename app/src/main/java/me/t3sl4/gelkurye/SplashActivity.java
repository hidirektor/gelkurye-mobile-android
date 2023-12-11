package me.t3sl4.gelkurye;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.Screens.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private final int WAITING_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        openMainScreen();
    }

    private void openMainScreen() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
        }, WAITING_TIME);
    }
}
