package me.t3sl4.kurye.UI.Screens.User.Merchant;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

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
import me.t3sl4.kurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.kurye.Util.ReqUtil;
import me.t3sl4.kurye.Util.Utils;

public class Marketplace extends AppCompatActivity {
    private UserModel currentProfile;

    private NiceSwitch synchTrendyol;
    private NiceSwitch synchGetirYemek;
    private NiceSwitch synchYemekSepeti;

    private LinearLayout findTrendyol;
    private LinearLayout findGetirYemek;
    private LinearLayout findYemekSepeti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketplace);

        NavigationBarUtil.hideNavigationBar(this);

        componentInitialize();

        currentProfile = Utils.getFromSharedPreferences(this);
        refreshProfileData();

        checkMarketPlaces();

        setupSwitchListeners();
    }

    private void componentInitialize() {
        synchTrendyol = findViewById(R.id.trendyolYemekSwitch);
        synchGetirYemek = findViewById(R.id.getirYemekSwitch);
        synchYemekSepeti = findViewById(R.id.yemeksepetiSwitch);

        findTrendyol = findViewById(R.id.findTrendyolButton);
        findGetirYemek = findViewById(R.id.findGetirYemekButton);
        findYemekSepeti = findViewById(R.id.findYemekSepetiButton);
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