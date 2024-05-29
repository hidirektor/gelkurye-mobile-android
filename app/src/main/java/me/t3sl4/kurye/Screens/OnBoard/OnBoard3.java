package me.t3sl4.kurye.Screens.OnBoard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.Screens.MainActivity;
import me.t3sl4.kurye.LocalData.SharedPreferencesManager;
import me.t3sl4.kurye.Util.Utils;

public class OnBoard3 extends AppCompatActivity {
    private ImageView nextButton;
    private ImageView previousButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboard_3);

        Utils.getInstance().getNavigationBar().hideNavigationBar(this);

        nextButton = findViewById(R.id.onboardSonraki);
        previousButton = findViewById(R.id.onboardOnceki);

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard3.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        previousButton.setOnClickListener(v -> {
            Intent intent = new Intent(OnBoard3.this, OnBoard2.class);
            startActivity(intent);
            finish();
        });

        SharedPreferencesManager.writeSharedPref("isFirstTime", false, this);
    }
}
