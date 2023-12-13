package me.t3sl4.gelkurye.Screens.Authentication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.PasswordFieldTouchListener;

public class Login extends AppCompatActivity  {
    private EditText editTextUsername;
    private EditText editTextPassword;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        PasswordFieldTouchListener.setChangeablePasswordField(editTextPassword, getApplicationContext());
    }
}
