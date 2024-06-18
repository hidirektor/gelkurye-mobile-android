package me.t3sl4.kurye.UI.Screens.Authentication.RegisterFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.t3sl4.kurye.R;

public class Step5Fragment extends Fragment {
    private EditText merchantNameEditText, merchantAddressEditText, merchantPhoneNumberEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step5, container, false);
        merchantNameEditText = view.findViewById(R.id.merchantNameEditText);
        merchantAddressEditText = view.findViewById(R.id.merchantAddressEditText);
        merchantPhoneNumberEditText = view.findViewById(R.id.merchantPhoneNumberEditText);

        if (getArguments() != null) {
            merchantNameEditText.setText(getArguments().getString("merchantName", ""));
            merchantAddressEditText.setText(getArguments().getString("merchantAddress", ""));
            merchantPhoneNumberEditText.setText(getArguments().getString("merchantPhoneNumber", ""));
        }

        return view;
    }

    public String getMerchantNameEditText() {
        return merchantNameEditText.getText().toString();
    }

    public String getMerchantAddressEditText() {
        return merchantAddressEditText.getText().toString();
    }

    public String getMerchantPhoneNumberEditText() {
        return merchantPhoneNumberEditText.getText().toString();
    }
}