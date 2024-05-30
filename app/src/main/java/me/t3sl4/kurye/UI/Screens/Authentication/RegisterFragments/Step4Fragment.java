package me.t3sl4.kurye.UI.Screens.Authentication.RegisterFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.Util.Utils;

public class Step4Fragment extends Fragment {
    private ImageView licenseFrontFace, licenseBackFace;

    private String hashedLicenseFrontFace, hashedLicenseBackFace;

    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    private ActivityResultLauncher<Intent> licenseFrontPicker = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), data.getData());
                            licenseFrontFace.setImageBitmap(bitmap);

                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();

                            hashedLicenseFrontFace = Base64.encodeToString(byteArray, Base64.DEFAULT);
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
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), data.getData());
                            licenseBackFace.setImageBitmap(bitmap);

                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();

                            hashedLicenseBackFace = Base64.encodeToString(byteArray, Base64.DEFAULT);
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

        if (getArguments() != null) {
            Glide.with(this)
                    .load(Utils.decodeImage(getArguments().getString("licenseFront", "")))
                    .into(licenseFrontFace);
            Glide.with(this)
                    .load(Utils.decodeImage(getArguments().getString("licenseFront", "")))
                    .into(licenseBackFace);
        }

        return view;
    }

    public String getLicenseFront() {
        return hashedLicenseFrontFace;
    }

    public String getLicenseBack() {
        return hashedLicenseBackFace;
    }

    public void setLicenseFront(String encodedPhoto) {
        Glide.with(this)
                .load(Utils.decodeImage(encodedPhoto))
                .centerCrop()
                .into(licenseFrontFace);
    }

    public void setLicenseBack(String encodedPhoto) {
        Glide.with(this)
                .load(Utils.decodeImage(encodedPhoto))
                .centerCrop()
                .into(licenseBackFace);
    }

}