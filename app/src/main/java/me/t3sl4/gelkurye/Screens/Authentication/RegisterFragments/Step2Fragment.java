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

public class Step2Fragment extends Fragment {
    private EditText editTextProfilePhoto, nameSurnameEditText, phoneNumberEditText, addressEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment_step2, container, false);
        editTextProfilePhoto = view.findViewById(R.id.editTextProfilePhoto);
        nameSurnameEditText = view.findViewById(R.id.nameSurnameEditText);
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        addressEditText = view.findViewById(R.id.addressEditText);

        if (getArguments() != null) {
            editTextProfilePhoto.setText(getArguments().getString("profilePhoto", ""));
            nameSurnameEditText.setText(getArguments().getString("nameSurname", ""));
            phoneNumberEditText.setText(getArguments().getString("phoneNumber", ""));
            addressEditText.setText(getArguments().getString("address", ""));
        }

        return view;
    }

    public String getProfilePhoto() {
        return editTextProfilePhoto.getText().toString();
    }

    public String getNameSurname() {
        return nameSurnameEditText.getText().toString();
    }

    public String getPhoneNumber() {
        return phoneNumberEditText.getText().toString();
    }

    public String getAddress() {
        return addressEditText.getText().toString();
    }
}