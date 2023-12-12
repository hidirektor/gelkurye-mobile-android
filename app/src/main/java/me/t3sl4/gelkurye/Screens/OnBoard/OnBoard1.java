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

public class OnBoard1 extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard_1);

        TextView atlaButton1 = findViewById(R.id.atlaText);
        ImageView nextButton1 = findViewById(R.id.onboardSonraki);

        atlaButton1.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard1.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        nextButton1.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard1.this, OnBoard2.class);
            startActivity(intent);
            finish();
        });

        SharedPreferencesManager.writeSharedPref("isFirstTime", false, this);
    }
}
