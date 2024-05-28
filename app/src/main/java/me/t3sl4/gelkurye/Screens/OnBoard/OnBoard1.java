package me.t3sl4.gelkurye.Screens.OnBoard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.MainActivity;
import me.t3sl4.gelkurye.LocalData.SharedPreferencesManager;
import me.t3sl4.gelkurye.Util.Utils;

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
