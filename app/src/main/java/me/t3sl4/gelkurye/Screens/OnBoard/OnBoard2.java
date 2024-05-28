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

public class OnBoard2 extends AppCompatActivity {
    private TextView atlaButton;
    private ImageView nextButton;
    private ImageView previousButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboard_2);

        Utils.getInstance().getNavigationBar().hideNavigationBar(this);

        atlaButton = findViewById(R.id.atlaText);
        nextButton = findViewById(R.id.onboardSonraki);
        previousButton = findViewById(R.id.onboardOnceki);

        atlaButton.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard2.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard2.this, OnBoard3.class);
            startActivity(intent);
            finish();
        });

        previousButton.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard2.this, OnBoard1.class);
            startActivity(intent);
            finish();
        });

        SharedPreferencesManager.writeSharedPref("isFirstTime", false, this);
    }
}
