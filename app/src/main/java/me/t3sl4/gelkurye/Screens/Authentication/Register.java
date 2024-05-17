package me.t3sl4.gelkurye.Screens.Authentication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kofigyan.stateprogressbar.StateProgressBar;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.PasswordField.PasswordFieldTouchListener;

public class Register extends AppCompatActivity  {
    String[] stateNames = {"Hesap\nBilgileri", "Kişisel\nBilgiler", "Yakınınız", "Yasal"};

    //State Bar:
    private StateProgressBar registerStateBar;

    //Section One:
    private EditText userNameEditText;
    private EditText eMailEditText;
    private EditText passwordEditText;
    private EditText passwordRepeatEditText;

    //Section Two:
    private EditText hashedProfilePhoto;
    private EditText nameSurnameEditText;
    private EditText phoneNumberEditText;
    private EditText addressEditText;

    //Section Three:
    private EditText relativeNameSurnameEditText;
    private EditText relativePhoneNumberEditText;

    //Section Four:
    private EditText hashedLicenseFrontFaceEditText;
    private EditText hashedLicenseBackFaceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeComponents();

        //PasswordFieldTouchListener.setChangeablePasswordField(passwordField, getApplicationContext());
        //PasswordFieldTouchListener.setChangeablePasswordField(passwordRepeatField, getApplicationContext());
    }

    private void initializeComponents() {
        //State Bar:
        registerStateBar = findViewById(R.id.registerStateBar);
        registerStateBar.setStateDescriptionData(stateNames);

        registerStateBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);

        //Section One:
        userNameEditText = findViewById(R.id.userNameEditText);
        eMailEditText = findViewById(R.id.eMailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordRepeatEditText = findViewById(R.id.passwordRepeatEditText);

        //Section Two:

    }
}
