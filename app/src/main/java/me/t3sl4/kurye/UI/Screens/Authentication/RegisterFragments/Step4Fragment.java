package me.t3sl4.kurye.UI.Screens.Authentication.RegisterFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.io.IOException;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.Util.Utils;

public class Step4Fragment extends Fragment {
    private ImageView licenseFrontFace, licenseBackFace;

    private String hashedLicenseFrontFace, hashedLicenseBackFace;

    private ActivityResultLauncher<Intent> licenseFrontPicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        try {
                            Uri imageUri = data.getData();
                            Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().getContentResolver(), imageUri));
                            licenseFrontFace.setImageBitmap(bitmap);

                            hashedLicenseFrontFace = Utils.encodeImage(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


    private ActivityResultLauncher<Intent> licenseBackPicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        try {
                            Uri imageUri = data.getData();
                            Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireActivity().getContentResolver(), imageUri));
                            licenseBackFace.setImageBitmap(bitmap);

                            hashedLicenseBackFace = Utils.encodeImage(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step4, container, false);
        licenseFrontFace = view.findViewById(R.id.licenseFrontFaceImageView);
        licenseBackFace = view.findViewById(R.id.licenseBackFaceImageView);

        licenseFrontFace.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            licenseFrontPicker.launch(intent);
        });

        licenseBackFace.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            licenseBackPicker.launch(intent);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String encodedFrontPhoto = getArguments().getString("licenseFront", "");
            String encodedBackPhoto = getArguments().getString("licenseBack", "");

            if (encodedFrontPhoto != null && !encodedFrontPhoto.isEmpty()) {
                hashedLicenseFrontFace = encodedFrontPhoto; // Initialize hashedLicenseFrontFace
                Glide.with(this)
                        .load(Utils.decodeImage(encodedFrontPhoto))
                        .into(licenseFrontFace);
            }

            if (encodedBackPhoto != null && !encodedBackPhoto.isEmpty()) {
                hashedLicenseBackFace = encodedBackPhoto; // Initialize hashedLicenseBackFace
                Glide.with(this)
                        .load(Utils.decodeImage(encodedBackPhoto))
                        .into(licenseBackFace);
            }
        }
    }

    public String getLicenseFront() {
        return hashedLicenseFrontFace;
    }

    public String getLicenseBack() {
        return hashedLicenseBackFace;
    }

    public void setLicenseFront(String encodedPhoto) {
        hashedLicenseFrontFace = encodedPhoto;
        Glide.with(this)
                .load(Utils.decodeImage(encodedPhoto))
                .centerCrop()
                .into(licenseFrontFace);
    }

    public void setLicenseBack(String encodedPhoto) {
        hashedLicenseBackFace = encodedPhoto;
        Glide.with(this)
                .load(Utils.decodeImage(encodedPhoto))
                .centerCrop()
                .into(licenseBackFace);
    }
}