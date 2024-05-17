package me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.PasswordField.PasswordFieldTouchListener;

public class Step1Fragment extends Fragment {
    private EditText userNameEditText, eMailEditText, passwordEditText, passwordRepeatEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment_step1, container, false);
        userNameEditText = view.findViewById(R.id.userNameEditText);
        eMailEditText = view.findViewById(R.id.eMailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        passwordRepeatEditText = view.findViewById(R.id.passwordRepeatEditText);

        if (getArguments() != null) {
            userNameEditText.setText(getArguments().getString("username", ""));
            eMailEditText.setText(getArguments().getString("email", ""));
            passwordEditText.setText(getArguments().getString("password", ""));
            passwordRepeatEditText.setText(getArguments().getString("passwordRepeat", ""));
        }

        PasswordFieldTouchListener.setChangeablePasswordField(passwordEditText, view.getContext());
        PasswordFieldTouchListener.setChangeablePasswordField(passwordRepeatEditText, view.getContext());

        return view;
    }

    public String getUserName() {
        return userNameEditText.getText().toString();
    }

    public String getEmail() {
        return eMailEditText.getText().toString();
    }

    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    public String getPasswordRepeat() {
        return passwordRepeatEditText.getText().toString();
    }
}