package me.t3sl4.kurye.UI.Screens.Merchant.Navbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.irozon.sneaker.Sneaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import me.t3sl4.kurye.Model.User.UserModel;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Components.NiceSwitch.NiceSwitch;
import me.t3sl4.kurye.UI.Screens.Merchant.MerchantDashboard;
import me.t3sl4.kurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.kurye.Util.ReqUtil;
import me.t3sl4.kurye.Util.Utils;

public class Marketplace extends AppCompatActivity {
    private UserModel currentProfile;

    private ImageView closeButton;

    private NiceSwitch synchTrendyol;
    private NiceSwitch synchGetirYemek;
    private NiceSwitch synchYemekSepeti;

    private LinearLayout findTrendyol;
    private LinearLayout findGetirYemek;
    private LinearLayout findYemekSepeti;

    private Button trendyolInfoButton;
    private Button getirYemekInfoButton;
    private Button yemekSepetiInfoButton;

    private LinearLayout trendyolLinearLayout;
    private ImageView trendyolCloseButton;
    private EditText trendyolSupplierIDEditText;
    private EditText trendyolAPIKeyEditText;
    private EditText trendyolAPISecretKeyEditText;

    private LinearLayout getirYemekLinearLayout;
    private ImageView getirYemekCloseButton;
    private EditText getirYemekMerchantTokenEditText;

    private LinearLayout yemekSepetiLinearLayout;
    private ImageView yemekSepetiCloseButton;
    private EditText yemekSepetiUsernameEditText;
    private EditText yemekSepetiPasswordEditText;

    //Navbar Buttons
    private LinearLayout dashboardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketplace);

        NavigationBarUtil.hideNavigationBar(this);

        componentInitialize();

        currentProfile = Utils.getFromSharedPreferences(this);

        checkMarketPlaces();

        setupSwitchListeners();

        infoComponentClicks();

        popupButtonClicks();

        navbarButtonClicks();

        generalButtonClicks();

        refreshProfileData();
    }

    private void componentInitialize() {
        closeButton = findViewById(R.id.backButtonImage);

        synchTrendyol = findViewById(R.id.trendyolYemekSwitch);
        synchGetirYemek = findViewById(R.id.getirYemekSwitch);
        synchYemekSepeti = findViewById(R.id.yemeksepetiSwitch);

        findTrendyol = findViewById(R.id.findTrendyolButton);
        findGetirYemek = findViewById(R.id.findGetirYemekButton);
        findYemekSepeti = findViewById(R.id.findYemekSepetiButton);

        trendyolInfoButton = findViewById(R.id.trendyolInfo);
        getirYemekInfoButton = findViewById(R.id.getirYemekInfo);
        yemekSepetiInfoButton = findViewById(R.id.yemekSepetiInfo);

        trendyolLinearLayout = findViewById(R.id.trendyolLinearLayout);
        trendyolCloseButton = findViewById(R.id.trendyolCloseImageView);
        trendyolSupplierIDEditText = findViewById(R.id.supplierIDEditText);
        trendyolAPIKeyEditText = findViewById(R.id.apiKeyEditText);
        trendyolAPISecretKeyEditText = findViewById(R.id.secretKeyEditText);

        getirYemekLinearLayout = findViewById(R.id.getirYemekLinearLayout);
        getirYemekCloseButton = findViewById(R.id.getirYemekCloseImageView);
        getirYemekMerchantTokenEditText = findViewById(R.id.getirYemekMerchantTokenEditText);

        yemekSepetiLinearLayout = findViewById(R.id.yemekSepetiLinearLayout);
        yemekSepetiCloseButton = findViewById(R.id.yemekSepetiCloseImageView);
        yemekSepetiUsernameEditText = findViewById(R.id.yemekSepetiUsernameEditText);
        yemekSepetiPasswordEditText = findViewById(R.id.yemekSepetiPasswordEditText);

        //Navbar Buttons
        dashboardButton = findViewById(R.id.dashboardButton);
    }

    private void checkMarketPlaces() {
        if(currentProfile.getTrendyolAPIKey().isBlank() || currentProfile.getTrendyolAPIKey().contains("null")) {
            synchTrendyol.setChecked(false);
        } else {
            synchTrendyol.setChecked(true);
        }

        if (currentProfile.getGetirYemekMerchantToken().isBlank() || currentProfile.getGetirYemekMerchantToken().contains("null")) {
            synchGetirYemek.setChecked(false);
        } else {
            synchGetirYemek.setChecked(true);
        }

        if(currentProfile.getYemekSepetiUsername().isBlank() || currentProfile.getYemekSepetiUsername().contains("null")) {
            synchYemekSepeti.setChecked(false);
        } else {
            synchYemekSepeti.setChecked(true);
        }
    }

    private void setupSwitchListeners() {
        synchTrendyol.setOnCheckedChangedListener(v -> {
            if (synchTrendyol.isChecked()) {
                showTrendyolBottomSheetDialog();
            }
        });

        synchGetirYemek.setOnCheckedChangedListener(v -> {
            if (synchGetirYemek.isChecked()) {
                showGetirYemekBottomSheetDialog();
            }
        });

        synchYemekSepeti.setOnCheckedChangedListener(v -> {
            if (synchYemekSepeti.isChecked()) {
                showYemekSepetiBottomSheetDialog();
            }
        });
    }

    private void generalButtonClicks() {
        closeButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void infoComponentClicks() {
        int defaultTextColor = ContextCompat.getColor(this, R.color.editTextTopColor);
        int secondTextColor = ContextCompat.getColor(this, R.color.white);

        Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.allorders);
        Drawable secondDrawable = ContextCompat.getDrawable(this, R.drawable.background_waitingorders);

        trendyolInfoButton.setOnClickListener(v -> {
            setButtonStatus(defaultTextColor, defaultDrawable);
            trendyolInfoButton.setTextColor(secondTextColor);
            trendyolInfoButton.setBackground(secondDrawable);

            trendyolLinearLayout.setVisibility(LinearLayout.VISIBLE);
            trendyolSupplierIDEditText.setText(currentProfile.getTrendyolSupplierID());
            trendyolAPIKeyEditText.setText(currentProfile.getTrendyolAPIKey());
            trendyolAPISecretKeyEditText.setText(currentProfile.getTrendyolAPISecretKey());
        });

        getirYemekInfoButton.setOnClickListener(v -> {
            setButtonStatus(defaultTextColor, defaultDrawable);
            getirYemekInfoButton.setTextColor(secondTextColor);
            getirYemekInfoButton.setBackground(secondDrawable);

            getirYemekLinearLayout.setVisibility(LinearLayout.VISIBLE);
            getirYemekMerchantTokenEditText.setText(currentProfile.getGetirYemekMerchantToken());
        });

        yemekSepetiInfoButton.setOnClickListener(v -> {
            setButtonStatus(defaultTextColor, defaultDrawable);
            yemekSepetiInfoButton.setTextColor(secondTextColor);
            yemekSepetiInfoButton.setBackground(secondDrawable);

            yemekSepetiLinearLayout.setVisibility(LinearLayout.VISIBLE);
            yemekSepetiUsernameEditText.setText(currentProfile.getYemekSepetiUsername());
            yemekSepetiPasswordEditText.setText(currentProfile.getYemekSepetiPassword());
        });

        trendyolCloseButton.setOnClickListener(v -> {
            setButtonStatus(defaultTextColor, defaultDrawable);
        });

        getirYemekCloseButton.setOnClickListener(v -> {
            setButtonStatus(defaultTextColor, defaultDrawable);
        });

        yemekSepetiCloseButton.setOnClickListener(v -> {
            setButtonStatus(defaultTextColor, defaultDrawable);
        });
    }

    private void navbarButtonClicks() {
        dashboardButton.setOnClickListener(v -> {
            Intent dashboardIntent = new Intent(Marketplace.this, MerchantDashboard.class);
            startActivity(dashboardIntent);
            finish();
        });
    }

    private void setButtonStatus(int defaultTextColor, Drawable defaultDrawable) {
        trendyolInfoButton.setTextColor(defaultTextColor);
        trendyolInfoButton.setBackground(defaultDrawable);
        trendyolLinearLayout.setVisibility(LinearLayout.GONE);

        getirYemekInfoButton.setTextColor(defaultTextColor);
        getirYemekInfoButton.setBackground(defaultDrawable);
        getirYemekLinearLayout.setVisibility(LinearLayout.GONE);

        yemekSepetiInfoButton.setTextColor(defaultTextColor);
        yemekSepetiInfoButton.setBackground(defaultDrawable);
        yemekSepetiLinearLayout.setVisibility(LinearLayout.GONE);
    }

    private void showTrendyolBottomSheetDialog() {
        AtomicBoolean buttonClicked = new AtomicBoolean(false);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.synch_trendyol);

        EditText supplierIDEditText = bottomSheetDialog.findViewById(R.id.currentPasswordEditText);
        EditText apiKeyEditText = bottomSheetDialog.findViewById(R.id.passwordEditText);
        EditText apiSecretKeyEditText = bottomSheetDialog.findViewById(R.id.passwordRepeatEditText);
        Button synchButton = bottomSheetDialog.findViewById(R.id.synchTrendyolButton);

        synchButton.setOnClickListener(v -> {
            buttonClicked.set(true);
            String supplierID = supplierIDEditText.getText().toString();
            String apiKey = apiKeyEditText.getText().toString();
            String apiSecretKey = apiSecretKeyEditText.getText().toString();

            JSONObject marketplaceAPI = new JSONObject();
            try {
                marketplaceAPI.put("trendyolSupplierID", supplierID);
                marketplaceAPI.put("trendyolAPIKey", apiKey);
                marketplaceAPI.put("trendyolAPISecretKey", apiSecretKey);
                marketplaceAPI.put("getirYemekMerchantToken", JSONObject.NULL);
                marketplaceAPI.put("yemekSepetiUsername", JSONObject.NULL);
                marketplaceAPI.put("yemekSepetiPassword", JSONObject.NULL);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            updateMerchantAPI(marketplaceAPI, bottomSheetDialog, "Trendyol");
        });

        bottomSheetDialog.show();
        Objects.requireNonNull(bottomSheetDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        NavigationBarUtil.hideNavigationBarOnDialog(bottomSheetDialog);
        bottomSheetDialog.setOnDismissListener(dialog -> {
            NavigationBarUtil.hideNavigationBar(Marketplace.this);
            if(!buttonClicked.get()) {
                synchGetirYemek.setChecked(false);
            }
        });
    }

    private void showGetirYemekBottomSheetDialog() {
        AtomicBoolean buttonClicked = new AtomicBoolean(false);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.synch_getiryemek);

        EditText merchantTokenEditText = bottomSheetDialog.findViewById(R.id.passwordEditText);
        Button synchButton = bottomSheetDialog.findViewById(R.id.synchGetirYemekButton);

        synchButton.setOnClickListener(v -> {
            buttonClicked.set(true);
            String merchantToken = merchantTokenEditText.getText().toString();

            JSONObject marketplaceAPI = new JSONObject();
            try {
                marketplaceAPI.put("trendyolSupplierID", JSONObject.NULL);
                marketplaceAPI.put("trendyolAPIKey", JSONObject.NULL);
                marketplaceAPI.put("trendyolAPISecretKey", JSONObject.NULL);
                marketplaceAPI.put("getirYemekMerchantToken", merchantToken);
                marketplaceAPI.put("yemekSepetiUsername", JSONObject.NULL);
                marketplaceAPI.put("yemekSepetiPassword", JSONObject.NULL);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            updateMerchantAPI(marketplaceAPI, bottomSheetDialog, "Getir Yemek");
        });

        bottomSheetDialog.show();
        Objects.requireNonNull(bottomSheetDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        NavigationBarUtil.hideNavigationBarOnDialog(bottomSheetDialog);
        bottomSheetDialog.setOnDismissListener(dialog -> {
            NavigationBarUtil.hideNavigationBar(Marketplace.this);
            if(!buttonClicked.get()) {
                synchGetirYemek.setChecked(false);
            }
        });
    }

    private void showYemekSepetiBottomSheetDialog() {
        AtomicBoolean buttonClicked = new AtomicBoolean(false);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.synch_yemeksepeti);

        EditText usernameEditText = bottomSheetDialog.findViewById(R.id.passwordEditText);
        EditText passwordEditText = bottomSheetDialog.findViewById(R.id.passwordRepeatEditText);
        Button synchButton = bottomSheetDialog.findViewById(R.id.synchYemekSepetiButton);

        synchButton.setOnClickListener(v -> {
            buttonClicked.set(true);
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            JSONObject marketplaceAPI = new JSONObject();
            try {
                marketplaceAPI.put("trendyolSupplierID", JSONObject.NULL);
                marketplaceAPI.put("trendyolAPIKey", JSONObject.NULL);
                marketplaceAPI.put("trendyolAPISecretKey", JSONObject.NULL);
                marketplaceAPI.put("getirYemekMerchantToken", JSONObject.NULL);
                marketplaceAPI.put("yemekSepetiUsername", username);
                marketplaceAPI.put("yemekSepetiPassword", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            updateMerchantAPI(marketplaceAPI, bottomSheetDialog, "Yemek Sepeti");
        });

        bottomSheetDialog.show();
        Objects.requireNonNull(bottomSheetDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        NavigationBarUtil.hideNavigationBarOnDialog(bottomSheetDialog);
        bottomSheetDialog.setOnDismissListener(dialog -> {
            NavigationBarUtil.hideNavigationBar(Marketplace.this);
            if(!buttonClicked.get()) {
                synchGetirYemek.setChecked(false);
            }
        });
    }

    private void showTrendyolFindBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.find_api_trendyol);

        ImageView closeDialog = bottomSheetDialog.findViewById(R.id.closePopup);

        closeDialog.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
        Objects.requireNonNull(bottomSheetDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        NavigationBarUtil.hideNavigationBarOnDialog(bottomSheetDialog);
        bottomSheetDialog.setOnDismissListener(dialog -> {
            NavigationBarUtil.hideNavigationBar(Marketplace.this);
        });
    }

    private void showGetirYemekFindBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.find_api_getiryemek);

        ImageView closeDialog = bottomSheetDialog.findViewById(R.id.closePopup);

        closeDialog.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
        Objects.requireNonNull(bottomSheetDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        NavigationBarUtil.hideNavigationBarOnDialog(bottomSheetDialog);
        bottomSheetDialog.setOnDismissListener(dialog -> {
            NavigationBarUtil.hideNavigationBar(Marketplace.this);
        });
    }

    private void showYemekSepetiFindBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.find_api_yemeksepeti);

        ImageView closeDialog = bottomSheetDialog.findViewById(R.id.closePopup);

        closeDialog.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
        Objects.requireNonNull(bottomSheetDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        NavigationBarUtil.hideNavigationBarOnDialog(bottomSheetDialog);
        bottomSheetDialog.setOnDismissListener(dialog -> {
            NavigationBarUtil.hideNavigationBar(Marketplace.this);
        });
    }

    private void popupButtonClicks() {
        findTrendyol.setOnClickListener(v -> {
            showTrendyolFindBottomSheetDialog();
        });

        findGetirYemek.setOnClickListener(v -> {
            showGetirYemekFindBottomSheetDialog();
        });

        findYemekSepeti.setOnClickListener(v -> {
            showYemekSepetiFindBottomSheetDialog();
        });
    }

    private void updateMerchantAPI(JSONObject marketplaceAPI, BottomSheetDialog bottomSheetDialog, String marketplaceName) {
        String phoneNumber = currentProfile.getPhoneNumber();
        String userID = currentProfile.getUserID();

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("phoneNumber", phoneNumber);
            requestBody.put("userID", userID);
            requestBody.put("marketplace-API", marketplaceAPI);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ReqUtil.updateMerchantAPIReq(this, phoneNumber, userID, marketplaceAPI, new HTTPResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Sneaker.with(Marketplace.this)
                        .setTitle("Başarılı")
                        .setMessage("API bilgileri güncellendi.")
                        .setDuration(3000)
                        .sneakSuccess();
            }

            @Override
            public void onError(VolleyError error) {
                Sneaker.with(Marketplace.this)
                        .setTitle("Hata !")
                        .setMessage("API bilgileri güncellenemedi.")
                        .setDuration(3000)
                        .sneakError();

                if(marketplaceName.equals("Trendyol")) {
                    synchTrendyol.setChecked(false);
                } else if(marketplaceName.equals("Getir Yemek")) {
                    synchGetirYemek.setChecked(false);
                } else if(marketplaceName.equals("Yemek Sepeti")) {
                    synchYemekSepeti.setChecked(false);
                }
            }
        });

        bottomSheetDialog.dismiss();
    }

    private void refreshProfileData() {
        ReqUtil.getProfileReq(Marketplace.this, currentProfile.getPhoneNumber(), new ReqUtil.ProfileCallback() {
            @Override
            public void onSuccess(UserModel profile) {
                //TODO: Update profile
            }

            @Override
            public void onError() {
                Sneaker.with(Marketplace.this).setTitle("Hata !").setMessage("Profil alınamadı!").sneakError();
            }
        });
    }
}