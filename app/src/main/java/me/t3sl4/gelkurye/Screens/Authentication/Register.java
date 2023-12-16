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
    private EditText profilePhotoField;
    private EditText nameSurnameField;
    private EditText usernameField;
    private EditText eMailField;
    private EditText phoneField;
    private EditText passwordField;
    private EditText passwordRepeatField;
    private EditText licenseFrontField;
    private EditText licenseBackField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.registerButton);

        profilePhotoField = findViewById(R.id.editTextProfilePhoto);
        nameSurnameField = findViewById(R.id.editTextNameSurname);
        usernameField = findViewById(R.id.editTextUsername);
        eMailField = findViewById(R.id.editTextMail);
        phoneField = findViewById(R.id.editTextPhone);
        passwordField = findViewById(R.id.editTextPassword);
        passwordRepeatField = findViewById(R.id.editTextPasswordRepeat);
        licenseFrontField = findViewById(R.id.editTextLicenseFront);
        licenseBackField = findViewById(R.id.editTextLicenseBack);

        PasswordFieldTouchListener.setChangeablePasswordField(passwordField, getApplicationContext());
        PasswordFieldTouchListener.setChangeablePasswordField(passwordRepeatField, getApplicationContext());
    }
}
