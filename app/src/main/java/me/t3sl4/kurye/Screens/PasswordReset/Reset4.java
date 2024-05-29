package me.t3sl4.kurye.Screens.PasswordReset;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.Screens.Authentication.Login;
import me.t3sl4.kurye.Util.Utils;

public class Reset4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset_4);

        Utils.getInstance().getNavigationBar().hideNavigationBar(this);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reset4.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
