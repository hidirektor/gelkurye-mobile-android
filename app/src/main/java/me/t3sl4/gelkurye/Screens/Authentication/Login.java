package me.t3sl4.gelkurye.Screens.Authentication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.PasswordFieldManager;

public class Login extends AppCompatActivity  {
    private EditText editTextTextUsername;
    private EditText editTextTextPassword;

    private boolean isPasswordVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextTextUsername = findViewById(R.id.editTextTextUsername);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);

        editTextTextPassword.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextTextPassword.getRight() - editTextTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        PasswordFieldManager.togglePasswordVisibility(editTextTextPassword, getApplicationContext());
                        isPasswordVisible = !isPasswordVisible;
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
