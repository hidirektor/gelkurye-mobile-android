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

public class Step4Fragment extends Fragment {
    private EditText editTextLicenseFront, editTextLicenseBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment_step4, container, false);
        editTextLicenseFront = view.findViewById(R.id.editTextLicenseFront);
        editTextLicenseBack = view.findViewById(R.id.editTextLicenseBack);

        if (getArguments() != null) {
            editTextLicenseFront.setText(getArguments().getString("licenseFront", ""));
            editTextLicenseBack.setText(getArguments().getString("licenseBack", ""));
        }

        return view;
    }

    public String getLicenseFront() {
        return editTextLicenseFront.getText().toString();
    }

    public String getLicenseBack() {
        return editTextLicenseBack.getText().toString();
    }
}