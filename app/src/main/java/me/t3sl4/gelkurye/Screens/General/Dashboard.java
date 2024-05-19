package me.t3sl4.gelkurye.Screens.General;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.Dash;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.Hamburger.FAQ;
import me.t3sl4.gelkurye.Screens.Order.CurrentOrder;
import me.t3sl4.gelkurye.Screens.Order.Orders;
import me.t3sl4.gelkurye.Screens.User.Earning;
import me.t3sl4.gelkurye.Screens.User.Profile;
import me.t3sl4.gelkurye.Util.Component.Navigation.NavigationManager;

public class Dashboard extends AppCompatActivity {

    //Personal Stats:
    private ImageView profilePhotoDashboard;
    private ImageView profilePhotoHamburger;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        componentInitialize();

        hamburgerMenuButtonClicks();
        navbarButtonClicks();

        hamburgerEffect();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void hamburgerEffect() {
        restrictedHeaderSection.setOnClickListener(v -> {
            if (hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, Dashboard.this);
            }
        });

        restrictedMidSection.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });

        restrictedBottomSection.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
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
    }

    private void hamburgerMenuButtonClicks() {
        //Hamburger Menu Trigger Listener
        hamburgerButton.setOnClickListener(v -> NavigationManager.showNavigationViewWithAnimation(hamburgerMenu, this));

        //Hamburger Button Clicks
        currentOrderButton.setOnClickListener(v -> {
            //Anlık map takibi ile şuanki ekranı gösterme
            Intent currentOrderIntent = new Intent(Dashboard.this, CurrentOrder.class);
            startActivity(currentOrderIntent);
        });

        navAllOrdersButton.setOnClickListener(v -> {
            Intent allOrdersIntent = new Intent(Dashboard.this, Orders.class);
            startActivity(allOrdersIntent);
        });

        navSettingsButton.setOnClickListener(v -> {
            //Ayarlar ekranına yönlendir
        });

        navFaqButton.setOnClickListener(v -> {
            Intent faqIntent = new Intent(Dashboard.this, FAQ.class);
            startActivity(faqIntent);
        });

        navSupportButton.setOnClickListener(v -> {
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hidirektor"));
            startActivity(githubIntent);
        });
    }

    private void navbarButtonClicks() {
        //Navbar button click listeners
        dashboardButton.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });

        ordersButton.setOnClickListener(v -> {
            Intent ordersIntent = new Intent(Dashboard.this, Orders.class);
            startActivity(ordersIntent);
        });

        earningButton.setOnClickListener(v -> {
            Intent earningIntent = new Intent(Dashboard.this, Earning.class);
            startActivity(earningIntent);
        });

        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(Dashboard.this, Profile.class);
            startActivity(profileIntent);
        });
    }
}
