package me.t3sl4.gelkurye.Screens.Authentication.RegisterFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Image.ImageUtil;

public class Step4Fragment extends Fragment {
    private ImageView licenseFrontFace, licenseBackFace;

    private String hashedLicenseFrontFace, hashedLicenseBackFace;

    private static final int PICK_FRONT = 1, PICK_BACK = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment_step4, container, false);
        licenseFrontFace = view.findViewById(R.id.licenseFrontFaceImageView);
        licenseBackFace = view.findViewById(R.id.licenseBackFaceImageView);

        licenseFrontFace.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_FRONT);
        });

        licenseBackFace.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_BACK);
        });

        if (getArguments() != null) {
            licenseFrontFace.setImageDrawable(ImageUtil.decodeImage(getArguments().getString("licenseFront", "")));
            licenseBackFace.setImageDrawable(ImageUtil.decodeImage(getArguments().getString("licenseBack", "")));
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
        licenseFrontFace.setImageDrawable(ImageUtil.decodeImage(encodedPhoto));
        licenseFrontFace.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void setLicenseBack(String encodedPhoto) {
        licenseBackFace.setImageDrawable(ImageUtil.decodeImage(encodedPhoto));
        licenseBackFace.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FRONT && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                licenseFrontFace.setImageBitmap(bitmap);
                licenseFrontFace.setScaleType(ImageView.ScaleType.CENTER_CROP);

                hashedLicenseFrontFace = ImageUtil.encodeImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(requestCode == PICK_BACK && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                licenseBackFace.setImageBitmap(bitmap);
                licenseBackFace.setScaleType(ImageView.ScaleType.CENTER_CROP);

                hashedLicenseBackFace = ImageUtil.encodeImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}