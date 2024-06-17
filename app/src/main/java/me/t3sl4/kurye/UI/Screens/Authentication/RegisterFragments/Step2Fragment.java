package me.t3sl4.kurye.UI.Screens.Authentication.RegisterFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.hbb20.CountryCodePicker;
import com.irozon.sneaker.Sneaker;

import java.io.IOException;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.Util.Utils;

public class Step2Fragment extends Fragment {
    private EditText nameSurnameEditText, phoneNumberEditText, addressEditText;
    private CountryCodePicker phoneNumberContryCode;
    private ImageView profilePhotoImageView;

    private String hashedProfilePhoto;

    private ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), data.getData());
                            profilePhotoImageView.setImageBitmap(bitmap);

                            hashedProfilePhoto = Utils.encodeImage(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step2, container, false);
        profilePhotoImageView = view.findViewById(R.id.profilePhotoImageView);
        nameSurnameEditText = view.findViewById(R.id.nameSurnameEditText);
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        phoneNumberContryCode = view.findViewById(R.id.phoneNumberContryCode);
        addressEditText = view.findViewById(R.id.addressEditText);

        profilePhotoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent);
        });

        phoneNumberContryCode.setOnClickListener(v -> {
            Sneaker.with(Step2Fragment.this).setTitle("Hata !").setMessage(getString(R.string.country_error)).sneakError();
        });

        phoneNumberContryCode.setCcpClickable(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String encodedPhoto = getArguments().getString("profilePhoto", "");
            if (encodedPhoto != null && !encodedPhoto.isEmpty()) {
                hashedProfilePhoto = encodedPhoto; // Initialize hashedProfilePhoto
                Glide.with(this)
                        .load(Utils.decodeImage(encodedPhoto))
                        .into(profilePhotoImageView);
            }
            nameSurnameEditText.setText(getArguments().getString("nameSurname", ""));
            phoneNumberEditText.setText(getArguments().getString("phoneNumber", "").substring(2));
            addressEditText.setText(getArguments().getString("address", ""));
        }
    }

    public String getProfilePhoto() {
        return hashedProfilePhoto;
    }

    public String getNameSurname() {
        return nameSurnameEditText.getText().toString();
    }

    public String getPhoneNumber() {
        return phoneNumberContryCode.getSelectedCountryCode() + phoneNumberEditText.getText().toString();
    }

    public String getAddress() {
        return addressEditText.getText().toString();
    }

    public void setProfilePhoto(String encodedPhoto) {
        hashedProfilePhoto = encodedPhoto;
        if (isAdded()) {
            Glide.with(this)
                    .load(Utils.decodeImage(encodedPhoto))
                    .into(profilePhotoImageView);
        }
    }
}