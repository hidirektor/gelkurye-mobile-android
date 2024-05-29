package me.t3sl4.kurye.Screens.OnBoard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.Screens.MainActivity;
import me.t3sl4.kurye.LocalData.SharedPreferencesManager;
import me.t3sl4.kurye.Util.Utils;

public class OnBoard1 extends AppCompatActivity {
    private TextView atlaButton;
    private ImageView nextButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboard_1);

        Utils.getInstance().getNavigationBar().hideNavigationBar(this);

        atlaButton = findViewById(R.id.atlaText);
        nextButton = findViewById(R.id.onboardSonraki);

        atlaButton.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard1.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard1.this, OnBoard2.class);
            startActivity(intent);
            finish();
        });

        SharedPreferencesManager.writeSharedPref("isFirstTime", false, this);
    }
}
