package me.t3sl4.gelkurye.Screens.Authentication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.PasswordField.PasswordFieldTouchListener;

public class Register extends AppCompatActivity  {

    private Button registerButton;

    private EditText editTextNameSurname;
    private EditText editTextUsername;
    private EditText editTextMail;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private EditText editTextPasswordRepeat;
    private LinearLayout licenseFrontConstraint;
    private LinearLayout licenseBackConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.registerButton);

        editTextNameSurname = findViewById(R.id.editTextNameSurname);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextMail = findViewById(R.id.editTextMail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordRepeat = findViewById(R.id.editTextPasswordRepeat);
        licenseFrontConstraint = findViewById(R.id.licenseFrontConstraint);
        licenseBackConstraint = findViewById(R.id.licenseBackConstraint);

        PasswordFieldTouchListener.setChangeablePasswordField(editTextPassword, getApplicationContext());
        PasswordFieldTouchListener.setChangeablePasswordField(editTextPasswordRepeat, getApplicationContext());
    }
}
