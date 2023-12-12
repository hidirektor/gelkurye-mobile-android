package me.t3sl4.gelkurye.Screens.OnBoard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.MainActivity;
import me.t3sl4.gelkurye.Util.SharedPreferencesManager;

public class OnBoard2 extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_2);

        TextView atlaButton2 = findViewById(R.id.atlaText);
        ImageView nextButton2 = findViewById(R.id.onboardSonraki);
        ImageView previousButton2 = findViewById(R.id.onboardOnceki);

        atlaButton2.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard2.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        nextButton2.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard2.this, OnBoard3.class);
            startActivity(intent);
            finish();
        });

        previousButton2.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard2.this, OnBoard1.class);
            startActivity(intent);
            finish();
        });

        SharedPreferencesManager.writeSharedPref("isFirstTime", false, this);
    }
}
