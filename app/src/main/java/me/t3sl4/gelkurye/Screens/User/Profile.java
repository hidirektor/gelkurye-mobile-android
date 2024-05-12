package me.t3sl4.gelkurye.Screens.User;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.sigma.niceswitch.NiceSwitch;

import java.util.Objects;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.General.Dashboard;
import me.t3sl4.gelkurye.Screens.Order.Orders;

public class Profile extends AppCompatActivity {
    //Personal Stats:
    private ImageView profilePhotoDashboard;
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
    private Dialog languageDialog;
    private Dialog passDialog;

    //Navbar Buttons
    private LinearLayout dashboardButton;
    private LinearLayout ordersButton;
    private LinearLayout earningButton;
    private LinearLayout profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        componentInitialize();

        navbarButtonClicks();

        headerButtonClicks();

        midSectionButtonClicks();
    }

    private void componentInitialize() {
        //Personal Stats Definitions:
        profilePhotoDashboard = findViewById(R.id.profilePhotoDashboard);
        ratingBarDashboard = findViewById(R.id.ratingStar);
        nameSurnameDashboard = findViewById(R.id.nameSurnameDashboard);
        phoneNumberDashboard = findViewById(R.id.phoneNumberDashboard);

        //Header Buttons:
        goBackButton = findViewById(R.id.backButtonImage);
        logoutButton = findViewById(R.id.logoutImageView);
        editProfileButton = findViewById(R.id.editProfileImageView);
        shiftSwitch = findViewById(R.id.shiftSwitch);
        nightThemeSwitch = findViewById(R.id.nightThemeSwitch);

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

        logoutButton.setOnClickListener(v -> {
            //Çıkış Processi
        });

        editProfileButton.setOnClickListener(v -> {
            //Profil Düzenleme Processi
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
            passDialog = new Dialog(this);
            //Şifre Değiştirme Arayüzüne Aktar
            passDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            passDialog.setContentView(R.layout.popup_changepass);

            //Popup items:
            EditText currentPassword = passDialog.findViewById(R.id.currentPasswordEditText);
            EditText newPass = passDialog.findViewById(R.id.passwordEditText);
            EditText newPassRepeat = passDialog.findViewById(R.id.passwordRepeatEditText);
            Button changePassButton = passDialog.findViewById(R.id.changePassButton);

            changePassButton.setOnClickListener(View -> {
                //Şifre Değiştirme İşlemi
            });

            passDialog.show();
            Objects.requireNonNull(passDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            passDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            passDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            passDialog.getWindow().setGravity(Gravity.BOTTOM);
        });

        changeLangLinearButton.setOnClickListener(v -> {
            languageDialog = new Dialog(this);
            //Dil Değiştirme Arayüzüne Aktar
            languageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            languageDialog.setContentView(R.layout.popup_language_selector);

            //Popup items:
            ImageView closeButton = languageDialog.findViewById(R.id.closeLanguagePopup);
            RadioButton turkishButton = languageDialog.findViewById(R.id.turkishButton);
            RadioButton englishButton = languageDialog.findViewById(R.id.englishButton);

            closeButton.setOnClickListener(view -> {
                languageDialog.dismiss();
            });

            turkishButton.setOnClickListener(view -> {
                //Sistem dilini türkçe yap

                englishButton.setChecked(false);
            });

            englishButton.setOnClickListener(view -> {
                //Sistem dilini ingilizce yap

                turkishButton.setChecked(false);
            });

            languageDialog.show();
            Objects.requireNonNull(languageDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            languageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            languageDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            languageDialog.getWindow().setGravity(Gravity.BOTTOM);
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
}
