package me.t3sl4.gelkurye.Screens.OnBoard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.MainActivity;
import me.t3sl4.gelkurye.Util.Data.SharedPreferencesManager;
import me.t3sl4.gelkurye.Util.Utils;

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
