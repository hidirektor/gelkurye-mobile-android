package me.t3sl4.kurye.UI.Screens.Merchant;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

import me.t3sl4.kurye.Model.User.UserModel;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.Navigation.NavigationUtil;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.Carrier.Hamburger.FAQ;
import me.t3sl4.kurye.UI.Screens.Carrier.Order.CurrentOrder;
import me.t3sl4.kurye.UI.Screens.Carrier.Navbar.Orders;
import me.t3sl4.kurye.UI.Screens.Merchant.Navbar.Marketplace;
import me.t3sl4.kurye.UI.Screens.Carrier.Navbar.Profile.EditProfile;
import me.t3sl4.kurye.Util.ReqUtil;
import me.t3sl4.kurye.Util.Utils;

public class MerchantDashboard extends AppCompatActivity {
    private UserModel currentProfile;

    //Personal Stats:
    private CircularImageView profilePhotoDashboard;
    private CircularImageView profilePhotoHamburger;
    private TextView merchantNameDashboard;
    private TextView nameSurnameDashboard;
    private TextView nameSurnameHamburger;
    private TextView phoneNumberDashboard;
    private TextView merchantAddressDashboard;

    //Hamburger Menu
    private ImageView hamburgerButton;
    private NavigationView hamburgerMenu;

    //Restriction Area
    private RelativeLayout restrictedHeaderSection;
    private BottomAppBar restrictedBottomSection;

    //Navbar Buttons
    private LinearLayout dashboardButton;
    private LinearLayout marketplaceButton;
    private LinearLayout earningButton;
    private LinearLayout profileButton;

    //Hamburger Menu Buttons
    private Button currentOrderButton;
    private LinearLayout navAllOrdersButton;
    private LinearLayout navSettingsButton;
    private LinearLayout navFaqButton;
    private LinearLayout navSupportButton;
    private LinearLayout navLogoutButton;

    //Orders List View
    private ListView ordersList;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant);

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
            if(hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationUtil.hideNavigationViewWithAnimation(hamburgerMenu, MerchantDashboard.this);
            }
        });

        ordersList.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationUtil.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });

        restrictedBottomSection.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationUtil.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });
    }

    private void componentInitialize() {
        //Personal Stats Definitions:
        merchantNameDashboard = findViewById(R.id.merchantNameDashboard);
        profilePhotoDashboard = findViewById(R.id.profilePhotoDashboard);
        nameSurnameDashboard = findViewById(R.id.nameSurnameDashboard);
        phoneNumberDashboard = findViewById(R.id.phoneNumberDashboard);
        merchantAddressDashboard = findViewById(R.id.merchantAddressDashboard);

        //RestrictedSections:
        restrictedHeaderSection = findViewById(R.id.header);
        restrictedBottomSection = findViewById(R.id.app_bar);

        //Hamburger Menu:
        hamburgerButton = findViewById(R.id.hamburgerButton);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);

        //Navbar Buttons
        dashboardButton = findViewById(R.id.dashboardButton);
        marketplaceButton = findViewById(R.id.marketplaceButton);
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

        //Orders Section
        ordersList = findViewById(R.id.ordersListView);
    }

    private void hamburgerMenuButtonClicks() {
        //Hamburger Menu Trigger Listener
        hamburgerButton.setOnClickListener(v -> NavigationUtil.showNavigationViewWithAnimation(hamburgerMenu, this));

        //Hamburger Button Clicks
        currentOrderButton.setOnClickListener(v -> {
            //Anlık map takibi ile şuanki ekranı gösterme
            Intent currentOrderIntent = new Intent(MerchantDashboard.this, CurrentOrder.class);
            startActivity(currentOrderIntent);
        });

        navAllOrdersButton.setOnClickListener(v -> {
            Intent allOrdersIntent = new Intent(MerchantDashboard.this, Orders.class);
            startActivity(allOrdersIntent);
        });

        navSettingsButton.setOnClickListener(v -> {
            //Ayarlar ekranına yönlendir
            Intent settingsIntent = new Intent(MerchantDashboard.this, EditProfile.class);
            startActivity(settingsIntent);
        });

        navFaqButton.setOnClickListener(v -> {
            Intent faqIntent = new Intent(MerchantDashboard.this, FAQ.class);
            startActivity(faqIntent);
        });

        navSupportButton.setOnClickListener(v -> {
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hidirektor"));
            startActivity(githubIntent);
        });

        navLogoutButton.setOnClickListener(v -> {
            Utils.logoutAndNotify(MerchantDashboard.this, MerchantDashboard.this);
        });
    }

    private void navbarButtonClicks() {
        //Navbar button click listeners
        dashboardButton.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });

        marketplaceButton.setOnClickListener(v -> {
            Intent marketplaceIntent = new Intent(MerchantDashboard.this, Marketplace.class);
            startActivity(marketplaceIntent);
        });

        earningButton.setOnClickListener(v -> {
            //Intent earningIntent = new Intent(MerchantDashboard.this, Earning.class);
            //startActivity(earningIntent);
        });

        profileButton.setOnClickListener(v -> {
            //Intent profileIntent = new Intent(MerchantDashboard.this, Profile.class);
            //startActivity(profileIntent);
        });
    }

    private void initializeProfileData() {
        if(currentProfile != null) {
            merchantNameDashboard.setText(currentProfile.getMerchantName());
            nameSurnameDashboard.setText(currentProfile.getNameSurname());
            nameSurnameHamburger.setText(currentProfile.getNameSurname());
            phoneNumberDashboard.setText(currentProfile.getFormattedPhoneNumber());
            merchantAddressDashboard.setText(currentProfile.getMerchantAddress());

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
        ReqUtil.getProfileReq(MerchantDashboard.this, currentProfile.getPhoneNumber(), new ReqUtil.ProfileCallback() {
            @Override
            public void onSuccess(UserModel profile) {
                //TODO: Update profile
            }

            @Override
            public void onError() {
                //Sneaker.with(MerchantDashboard.this).setTitle("Hata !").setMessage("Profil alınamadı!").sneakError();
            }
        });
    }
}