package me.t3sl4.gelkurye.Screens.Authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.General.Dashboard;
import me.t3sl4.gelkurye.Screens.PasswordReset.Reset1;
import me.t3sl4.gelkurye.Util.Component.PasswordField.PasswordFieldTouchListener;

public class Login extends AppCompatActivity  {
    private EditText userNameField;
    private EditText passwordField;
    private Button loginButton;
    private CheckBox rememberMe;
    private TextView sifremiUnuttumButton;

    private boolean isRemembered = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameField = findViewById(R.id.editTextUsername);
        passwordField = findViewById(R.id.editTextPassword);
        rememberMe = findViewById(R.id.beniHatirlaCheckBox);
        sifremiUnuttumButton = findViewById(R.id.sifremiUnuttumText);
        loginButton = findViewById(R.id.loginButton);

        isRemembered = rememberMe.isChecked();

        if(isRemembered) {
            //TODO
            //sharedpreferences'dan veriyi çek ve directLogin yap
            //kullanıcı adı ya da eposta + şifrenin şifreli hali
        }

        rememberMe.setOnClickListener(v -> {
            isRemembered = true;

            String tempUserName = userNameField.getText().toString();
            String tempPassword = passwordField.getText().toString();

            //TODO
            //directLogin yap.
        });

        PasswordFieldTouchListener.setChangeablePasswordField(passwordField, getApplicationContext());
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Dashboard.class);
            intent.putExtra("loginedUser", userNameField.getText());
            startActivity(intent);
            //TODO
            //remember me kontrol et
            //remember me işaretliyse end pointe girilen şifreyi at ve şifrelenmiş halini
            //shared preferences içinde sakla
        });

        sifremiUnuttumButton.setOnClickListener(v -> {
           Intent intent = new Intent(Login.this, Reset1.class);
           startActivity(intent);
        });
    }
}
