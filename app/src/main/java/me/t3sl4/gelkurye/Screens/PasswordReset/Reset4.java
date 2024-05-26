package me.t3sl4.gelkurye.Screens.PasswordReset;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.Authentication.Login;
import me.t3sl4.gelkurye.Util.Utils;

public class Reset4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_4);

        Utils.getInstance().getNavigationBar().hideNavigationBar(this);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reset4.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
