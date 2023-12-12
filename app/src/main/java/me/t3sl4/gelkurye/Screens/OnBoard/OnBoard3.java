package me.t3sl4.gelkurye.Screens.OnBoard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.MainActivity;
import me.t3sl4.gelkurye.Util.SharedPreferencesManager;

public class OnBoard3 extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_3);

        ImageView nextButton3 = findViewById(R.id.onboardSonraki);
        ImageView previousButton3 = findViewById(R.id.onboardOnceki);

        nextButton3.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard3.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        previousButton3.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard3.this, OnBoard2.class);
            startActivity(intent);
            finish();
        });

        SharedPreferencesManager.writeSharedPref("isFirstTime", false, this);
    }
}
