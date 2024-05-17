package me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Util.Image.ImageUtil;

public class Step2Fragment extends Fragment {
    private EditText nameSurnameEditText, phoneNumberEditText, addressEditText;
    private ImageView profilePhotoImageView;
    private static final int PICK_IMAGE = 1;

    private String hashedProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment_step2, container, false);
        profilePhotoImageView = view.findViewById(R.id.profilePhotoImageView);
        nameSurnameEditText = view.findViewById(R.id.nameSurnameEditText);
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        addressEditText = view.findViewById(R.id.addressEditText);

        profilePhotoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        if (getArguments() != null) {
            nameSurnameEditText.setText(getArguments().getString("nameSurname", ""));
            phoneNumberEditText.setText(getArguments().getString("phoneNumber", ""));
            addressEditText.setText(getArguments().getString("address", ""));
        }

        return view;
    }

    public String getProfilePhoto() {
        return hashedProfilePhoto;
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

    public void setProfilePhoto(String encodedPhoto) {
        profilePhotoImageView.setImageDrawable(ImageUtil.decodeImage(encodedPhoto));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                profilePhotoImageView.setImageBitmap(bitmap);

                hashedProfilePhoto = ImageUtil.encodeImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}