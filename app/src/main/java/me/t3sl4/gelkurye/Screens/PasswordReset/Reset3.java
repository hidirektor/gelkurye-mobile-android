package me.t3sl4.gelkurye.Screens.PasswordReset;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.EditText.EditTextManager;

public class Reset3 extends AppCompatActivity {
    private EditText passwordField;
    private EditText passwordRepeatField;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_3);

        passwordField = findViewById(R.id.editTextPassword);
        passwordRepeatField = findViewById(R.id.editTextPasswordRepeat);
        resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(v -> {
            Intent intent = new Intent(Reset3.this, Reset4.class);

            if(EditTextManager.checkNull(passwordField, null) && EditTextManager.checkNull(passwordRepeatField, null)) {
                //TODO
                //şifre sıfırlama endpointine istek at
                startActivity(intent);
                finish();
            }
        });
    }
}
