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

public class Step3Fragment extends Fragment {
    private EditText relativeNameSurnameEditText, relativePhoneNumberEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step3, container, false);
        relativeNameSurnameEditText = view.findViewById(R.id.relativeNameSurnameEditText);
        relativePhoneNumberEditText = view.findViewById(R.id.relativePhoneNumberEditText);

        if (getArguments() != null) {
            relativeNameSurnameEditText.setText(getArguments().getString("relativeNameSurname", ""));
            relativePhoneNumberEditText.setText(getArguments().getString("relativePhoneNumber", ""));
        }

        return view;
    }

    public String getRelativeNameSurname() {
        return relativeNameSurnameEditText.getText().toString();
    }

    public String getRelativePhoneNumber() {
        return relativePhoneNumberEditText.getText().toString();
    }
}