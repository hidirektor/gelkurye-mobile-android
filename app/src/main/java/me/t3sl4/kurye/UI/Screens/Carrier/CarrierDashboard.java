package me.t3sl4.kurye.UI.Screens.Carrier;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.mikhaellopez.circularimageview.CircularImageView;

import me.t3sl4.kurye.Model.User.UserModel;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.Navigation.NavigationUtil;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.Carrier.Hamburger.FAQ;
import me.t3sl4.kurye.UI.Screens.Carrier.Navbar.Earning;
import me.t3sl4.kurye.UI.Screens.Carrier.Navbar.Orders;
import me.t3sl4.kurye.UI.Screens.Carrier.Navbar.Profile.EditProfile;
import me.t3sl4.kurye.UI.Screens.Carrier.Navbar.Profile.Profile;
import me.t3sl4.kurye.UI.Screens.Carrier.Order.CurrentOrder;
import me.t3sl4.kurye.Util.ReqUtil;
import me.t3sl4.kurye.Util.Utils;
import me.t3sl4.kurye.kurye.UI.Components.Sneaker.Sneaker;

public class CarrierDashboard extends AppCompatActivity {
    private UserModel currentProfile;

    //Personal Stats:
    private CircularImageView profilePhotoDashboard;
    private CircularImageView profilePhotoHamburger;
    private SimpleRatingBar ratingBarDashboard;
    private TextView nameSurnameDashboard;
    private TextView nameSurnameHamburger;
    private TextView phoneNumberDashboard;

    //Hamburger Menu
    private ImageView hamburgerButton;
    private NavigationView hamburgerMenu;

    //Restriction Area
    private RelativeLayout restrictedHeaderSection;
    private ScrollView restrictedMidSection;
    private BottomAppBar restrictedBottomSection;

    //Navbar Buttons
    private LinearLayout dashboardButton;
    private LinearLayout ordersButton;
    private LinearLayout earningButton;
    private LinearLayout profileButton;

    //Hamburger Menu Buttons
    private Button currentOrderButton;
    private LinearLayout navAllOrdersButton;
    private LinearLayout navSettingsButton;
    private LinearLayout navFaqButton;
    private LinearLayout navSupportButton;
    private LinearLayout navLogoutButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrier);

        NavigationBarUtil.hideNavigationBar(this);

        componentInitialize();

        hamburgerMenuButtonClicks();
        navbarButtonClicks();

        hamburgerEffect();

        currentProfile = Utils.getFromSharedPreferences(this);
        refreshProfileData();

        initializeProfileData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void hamburgerEffect() {
        restrictedHeaderSection.setOnClickListener(v -> {
            if (hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationUtil.hideNavigationViewWithAnimation(hamburgerMenu, CarrierDashboard.this);
            }
        });

        restrictedMidSection.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationUtil.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });

        restrictedBottomSection.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationUtil.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });
    }

    private void componentInitialize() {
        //Personal Stats Definitions:
        profilePhotoDashboard = findViewById(R.id.profilePhotoDashboard);
        ratingBarDashboard = findViewById(R.id.ratingStar);
        nameSurnameDashboard = findViewById(R.id.nameSurnameDashboard);
        phoneNumberDashboard = findViewById(R.id.phoneNumberDashboard);

        //RestrictedSections:
        restrictedHeaderSection = findViewById(R.id.header);
        restrictedMidSection = findViewById(R.id.midSection);
        restrictedBottomSection = findViewById(R.id.app_bar);

        //Hamburger Menu:
        hamburgerButton = findViewById(R.id.hamburgerButton);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);

        //Navbar Buttons
        dashboardButton = findViewById(R.id.dashboardButton);
        ordersButton = findViewById(R.id.ordersButton);
        earningButton = findViewById(R.id.earningButton);
        profileButton = findViewById(R.id.profileButton);

        //Hamburger Menu Buttons
        View hamburgerView = hamburgerMenu.getHeaderView(0);
        profilePhotoHamburger = hamburgerView.findViewById(R.id.profilePhotoHamburger);
        nameSurnameHamburger = hamburgerView.findViewById(R.id.nameSurnameHamburger);
        currentOrderButton = hamburgerView.findViewById(R.id.navCurrentOrder);
        navAllOrdersButton = hamburgerView.findViewById(R.id.navAllOrders);
        navSettingsButton = hamburgerView.findViewById(R.id.navSettings);
        navFaqButton = hamburgerView.findViewById(R.id.navFAQ);
        navSupportButton = hamburgerView.findViewById(R.id.navSupport);
        navLogoutButton = hamburgerView.findViewById(R.id.navLogout);
    }

    private void hamburgerMenuButtonClicks() {
        //Hamburger Menu Trigger Listener
        hamburgerButton.setOnClickListener(v -> NavigationUtil.showNavigationViewWithAnimation(hamburgerMenu, this));

        //Hamburger Button Clicks
        currentOrderButton.setOnClickListener(v -> {
            //Anlık map takibi ile şuanki ekranı gösterme
            Intent currentOrderIntent = new Intent(CarrierDashboard.this, CurrentOrder.class);
            startActivity(currentOrderIntent);
        });

        navAllOrdersButton.setOnClickListener(v -> {
            Intent allOrdersIntent = new Intent(CarrierDashboard.this, Orders.class);
            startActivity(allOrdersIntent);
        });

        navSettingsButton.setOnClickListener(v -> {
            //Ayarlar ekranına yönlendir
            Intent settingsIntent = new Intent(CarrierDashboard.this, EditProfile.class);
            startActivity(settingsIntent);
        });

        navFaqButton.setOnClickListener(v -> {
            Intent faqIntent = new Intent(CarrierDashboard.this, FAQ.class);
            startActivity(faqIntent);
        });

        navSupportButton.setOnClickListener(v -> {
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hidirektor"));
            startActivity(githubIntent);
        });

        navLogoutButton.setOnClickListener(v -> {
            Utils.logoutAndNotify(CarrierDashboard.this, CarrierDashboard.this);
        });
    }

    private void navbarButtonClicks() {
        //Navbar button click listeners
        dashboardButton.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });

        ordersButton.setOnClickListener(v -> {
            Intent ordersIntent = new Intent(CarrierDashboard.this, Orders.class);
            startActivity(ordersIntent);
        });

        earningButton.setOnClickListener(v -> {
            Intent earningIntent = new Intent(CarrierDashboard.this, Earning.class);
            startActivity(earningIntent);
        });

        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(CarrierDashboard.this, Profile.class);
            startActivity(profileIntent);
        });
    }

    private void initializeProfileData() {
        if(currentProfile != null) {
            nameSurnameDashboard.setText(currentProfile.getNameSurname());
            nameSurnameHamburger.setText(currentProfile.getNameSurname());
            phoneNumberDashboard.setText(currentProfile.getFormattedPhoneNumber());
            ratingBarDashboard.setRating(currentProfile.getUserRating());

            String encodedProfilePhoto = currentProfile.getProfilePhoto();
            Log.d("base64", encodedProfilePhoto);
            if (!encodedProfilePhoto.isEmpty()) {
                Glide.with(this)
                        .load(Utils.decodeImage(encodedProfilePhoto))
                        .into(profilePhotoDashboard);
                Glide.with(this)
                        .load(Utils.decodeImage(encodedProfilePhoto))
                        .into(profilePhotoHamburger);
            } else {
                Log.e("Dashboard", "Encoded profile photo is empty or null");
            }
        }
    }

    private void refreshProfileData() {
        ReqUtil.getProfileReq(CarrierDashboard.this, currentProfile.getPhoneNumber(), new ReqUtil.ProfileCallback() {
            @Override
            public void onSuccess(UserModel profile) {
                //TODO: Update profile
            }

            @Override
            public void onError() {
                Sneaker.with(CarrierDashboard.this).setTitle("Hata !").setMessage("Profil alınamadı!").sneakError();
            }
        });
    }
}