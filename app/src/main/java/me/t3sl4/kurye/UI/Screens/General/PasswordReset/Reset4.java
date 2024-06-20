package me.t3sl4.kurye.UI.Screens.General.PasswordReset;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.General.Authentication.Login;

public class Reset4 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_reset_4);

        NavigationBarUtil.hideNavigationBar(this);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reset4.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
