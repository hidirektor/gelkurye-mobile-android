package me.t3sl4.kurye.UI.Screens.User;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.irozon.sneaker.Sneaker;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sigma.niceswitch.NiceSwitch;

import org.json.JSONObject;

import java.util.Objects;

import me.t3sl4.kurye.Model.User.Carrier;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.Authentication.Login;
import me.t3sl4.kurye.UI.Screens.General.Dashboard;
import me.t3sl4.kurye.UI.Screens.Order.Orders;
import me.t3sl4.kurye.Util.HTTP.HTTPResponseListener;
import me.t3sl4.kurye.Util.HTTP.TokenManager;
import me.t3sl4.kurye.Util.LocalData.SharedPreferencesManager;
import me.t3sl4.kurye.Util.ReqUtil;
import me.t3sl4.kurye.Util.Utils;

public class Profile extends AppCompatActivity {
    private Carrier currentProfile;

    //Personal Stats:
    private CircularImageView profilePhotoDashboard;
    private TextView nameSurnameDashboard;
    private TextView phoneNumberDashboard;
    private SimpleRatingBar ratingBarDashboard;

    //Header Buttons
    private ImageView goBackButton;
    private ImageView logoutButton;
    private ImageView editProfileButton;
    private NiceSwitch shiftSwitch;
    private NiceSwitch nightThemeSwitch;

    //Mid Section Buttons
    private LinearLayout earningLinearButton;
    private LinearLayout changePassLinearButton;
    private LinearLayout changeLangLinearButton;
    private LinearLayout contactUsLinearButton;
    private BottomSheetDialog languageDialog;
    private BottomSheetDialog passDialog;

    //Navbar Buttons
    private LinearLayout dashboardButton;
    private LinearLayout ordersButton;
    private LinearLayout earningButton;
    private LinearLayout profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        NavigationBarUtil.hideNavigationBar(this);

        componentInitialize();

        navbarButtonClicks();

        headerButtonClicks();

        midSectionButtonClicks();

        currentProfile = Utils.getFromSharedPreferences(this);
        initializeProfileData();
    }

    private void componentInitialize() {
        //Personal Stats Definitions:
        profilePhotoDashboard = findViewById(R.id.profileImageView);
        ratingBarDashboard = findViewById(R.id.ratingStar);
        nameSurnameDashboard = findViewById(R.id.nameSurnameDashboard);
        phoneNumberDashboard = findViewById(R.id.phoneNumberDashboard);

        //Header Buttons:
        goBackButton = findViewById(R.id.backButtonImage);
        logoutButton = findViewById(R.id.logoutImageView);
        editProfileButton = findViewById(R.id.editProfileImageView);
        shiftSwitch = findViewById(R.id.shiftSwitch);
        nightThemeSwitch = findViewById(R.id.nightThemeSwitch);

        shiftSwitch.setChecked(false);
        nightThemeSwitch.setChecked(false);

        //Linear Buttons:
        earningLinearButton = findViewById(R.id.earningLinearButton);
        changePassLinearButton = findViewById(R.id.changePassLinearButton);
        changeLangLinearButton = findViewById(R.id.changeLangLinearButton);
        contactUsLinearButton = findViewById(R.id.contactUsLinearButton);

        //Navbar Buttons
        dashboardButton = findViewById(R.id.dashboardButton);
        ordersButton = findViewById(R.id.ordersButton);
        earningButton = findViewById(R.id.earningButton);
        profileButton = findViewById(R.id.profileButton);
    }

    private void navbarButtonClicks() {
        //Navbar button click listeners
        dashboardButton.setOnClickListener(v -> {
            Intent dashboardIntent = new Intent(Profile.this, Dashboard.class);
            finish();
            startActivity(dashboardIntent);
        });

        ordersButton.setOnClickListener(v -> {
            Intent ordersIntent = new Intent(Profile.this, Orders.class);
            finish();
            startActivity(ordersIntent);
        });

        earningButton.setOnClickListener(v -> {
            Intent earningIntent = new Intent(Profile.this, Earning.class);
            finish();
            startActivity(earningIntent);
        });

        profileButton.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });
    }

    private void headerButtonClicks() {
        goBackButton.setOnClickListener(v -> {
            finish();
        });

        logoutButton.setOnClickListener(v -> ReqUtil.logoutReq(Profile.this, new HTTPResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Intent loginIntent = new Intent(Profile.this, Login.class);
                finish();
                startActivity(loginIntent);
            }

            @Override
            public void onError(VolleyError error) {
                Sneaker.with(Profile.this).setTitle("Hata !").setMessage("Çıkış yapılamadı, lütfen tekrar deneyin.").sneakError();
            }
        }));

        editProfileButton.setOnClickListener(v -> {
            //Profil Düzenleme Processi
            Intent profileEditIntent = new Intent(Profile.this, EditProfile.class);
            startActivity(profileEditIntent);
        });

        shiftSwitch.setOnCheckedChangedListener(v -> {
            if(shiftSwitch.isChecked()) {
                //Vardiya başlatıldı
            } else {
                //Vardiya sonlandırıldı
            }
        });

        nightThemeSwitch.setOnCheckedChangedListener(v -> {
            if(nightThemeSwitch.isChecked()) {
                //Gece Modu Aktifleştirildi
            } else {
                //Gece Modu Kapatıldı
            }
        });
    }

    private void midSectionButtonClicks() {
        earningLinearButton.setOnClickListener(v -> {
            Intent earningIntent = new Intent(Profile.this, Earning.class);
            finish();
            startActivity(earningIntent);
        });

        changePassLinearButton.setOnClickListener(v -> {
            //Şifre Değiştirme Arayüzüne Aktar
            passDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
            //Şifre Değiştirme Arayüzüne Aktar
            //passDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            passDialog.setContentView(R.layout.profile_changepass);

            //Popup items:
            EditText currentPassword = passDialog.findViewById(R.id.currentPasswordEditText);
            EditText newPass = passDialog.findViewById(R.id.passwordEditText);
            EditText newPassRepeat = passDialog.findViewById(R.id.passwordRepeatEditText);
            Button changePassButton = passDialog.findViewById(R.id.changePassButton);
            ImageView closeLanguagePopup = passDialog.findViewById(R.id.closeLanguagePopup);

            changePassButton.setOnClickListener(View -> {
                //Şifre Değiştirme İşlemi
            });

            closeLanguagePopup.setOnClickListener(View -> {
                passDialog.dismiss();
            });

            passDialog.show();
            Objects.requireNonNull(passDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            passDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            passDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            passDialog.getWindow().setGravity(Gravity.BOTTOM);

            NavigationBarUtil.hideNavigationBarOnDialog(passDialog);
            passDialog.setOnDismissListener(dialog -> {
                NavigationBarUtil.hideNavigationBar(Profile.this);
            });
        });

        changeLangLinearButton.setOnClickListener(v -> {
            languageDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
            //Dil Değiştirme Arayüzüne Aktar
            languageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            languageDialog.setContentView(R.layout.profile_changelanguage);

            //Popup items:
            ImageView closeButton = languageDialog.findViewById(R.id.closeLanguagePopup);
            RadioButton turkishButton = languageDialog.findViewById(R.id.turkishButton);
            RadioButton englishButton = languageDialog.findViewById(R.id.englishButton);
            ImageView closeLanguagePopup = languageDialog.findViewById(R.id.closeLanguagePopup);

            String currentLanguage = SharedPreferencesManager.getSharedPref("language", Profile.this, "en");

            if (Objects.equals(currentLanguage, "tr")) {
                turkishButton.setChecked(true);
            } else {
                englishButton.setChecked(true);
            }

            closeButton.setOnClickListener(view -> {
                languageDialog.dismiss();
            });

            turkishButton.setOnClickListener(view -> {
                //Sistem dilini türkçe yap
                switchLanguage("tr");

                englishButton.setChecked(false);
            });

            englishButton.setOnClickListener(view -> {
                //Sistem dilini ingilizce yap
                switchLanguage("en");

                turkishButton.setChecked(false);
            });

            closeLanguagePopup.setOnClickListener(View -> {
                languageDialog.dismiss();
            });

            languageDialog.show();
            Objects.requireNonNull(languageDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            languageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            languageDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            languageDialog.getWindow().setGravity(Gravity.BOTTOM);

            NavigationBarUtil.hideNavigationBarOnDialog(languageDialog);
            languageDialog.setOnDismissListener(dialog -> {
                NavigationBarUtil.hideNavigationBar(Profile.this);
            });
        });

        contactUsLinearButton.setOnClickListener(v -> {
            contactUs();
        });
    }

    public void contactUs() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+ "905386390476"));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    }

    private void switchLanguage(String nextLang) {
        SharedPreferencesManager.writeSharedPref("language", nextLang, Profile.this);
        Utils.setLocale(Profile.this, nextLang);
        recreate();
    }

    private void initializeProfileData() {
        if(currentProfile != null) {
            nameSurnameDashboard.setText(currentProfile.getNameSurname());
            phoneNumberDashboard.setText(currentProfile.getFormattedPhoneNumber());
            ratingBarDashboard.setRating(currentProfile.getUserRating());

            String encodedProfilePhoto = currentProfile.getProfilePhoto();
            if (encodedProfilePhoto != null && !encodedProfilePhoto.isEmpty()) {
                byte[] byteArray = Base64.decode(encodedProfilePhoto, Base64.DEFAULT);
                if (byteArray != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    Glide.with(this)
                            .load(bitmap)
                            .into(profilePhotoDashboard);
                } else {
                    Log.e("Dashboard", "Decoded profile photo is null");
                }
            } else {
                Log.e("Dashboard", "Encoded profile photo is empty or null");
            }
        }
    }

}
