package me.t3sl4.gelkurye.Screens.General;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.Hamburger.FAQ;
import me.t3sl4.gelkurye.Screens.Order.Orders;
import me.t3sl4.gelkurye.Util.Component.Button.ButtonManager;
import me.t3sl4.gelkurye.Util.Component.Navigation.NavigationManager;
import me.t3sl4.gelkurye.Util.Order.Order;
import me.t3sl4.gelkurye.Util.Order.OrderAdapter;

public class Dashboard extends AppCompatActivity {
    private ImageView hamburgerButton;
    private NavigationView hamburgerMenu;
    private ImageView profilePhoto;
    private ConstraintLayout restrictedArea4Hamburger;
    private CoordinatorLayout restrictedBottomArea4Hamburger;
    private BottomAppBar restrictedBottomAppbarArea4Hamburger;
    private LinearLayout restrictedMidSection;

    private SimpleRatingBar ratingBar;

    //Navbar Buttons
    private LinearLayout dashboardButton;
    private LinearLayout ordersButton;
    private LinearLayout earningButton;
    private LinearLayout profileButton;

    //Hamburger Menu Buttons
    private Button navCurrentOrderButton;
    private LinearLayout navAllOrdersButton;
    private LinearLayout navSettingsButton;
    private LinearLayout navFaqButton;
    private LinearLayout navSupportButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ratingBar = findViewById(R.id.ratingStar);

        hamburgerButton = findViewById(R.id.navigationButton);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);
        profilePhoto = findViewById(R.id.profilePhotoImageView);
        restrictedArea4Hamburger = findViewById(R.id.mainLayout);
        restrictedBottomArea4Hamburger = findViewById(R.id.bottomBar);
        restrictedBottomAppbarArea4Hamburger = findViewById(R.id.app_bar);
        restrictedMidSection = findViewById(R.id.midSection);

        //Navbar Buttons
        dashboardButton = findViewById(R.id.dashboardButton);
        ordersButton = findViewById(R.id.ordersButton);
        earningButton = findViewById(R.id.earningButton);
        profileButton = findViewById(R.id.profileButton);

        //Hamburger Menu Buttons
        View hamburgerView = hamburgerMenu.getHeaderView(0);
        navCurrentOrderButton = hamburgerView.findViewById(R.id.navCurrentOrder);
        navAllOrdersButton = hamburgerView.findViewById(R.id.navAllOrders);
        navSettingsButton = hamburgerView.findViewById(R.id.navSettings);
        navFaqButton = hamburgerView.findViewById(R.id.navFAQ);
        navSupportButton = hamburgerView.findViewById(R.id.navSupport);

        hamburgerButton.setOnClickListener(v -> NavigationManager.showNavigationViewWithAnimation(hamburgerMenu, this));

        hamburgerEffect();

        //Hamburger Button Clicks
        navFaqButton.setOnClickListener(v -> {
            Intent faqIntent = new Intent(Dashboard.this, FAQ.class);
            startActivity(faqIntent);
        });
        navAllOrdersButton.setOnClickListener(v -> {
            Intent allOrdersIntent = new Intent(Dashboard.this, Orders.class);
            startActivity(allOrdersIntent);
        });
        navSupportButton.setOnClickListener(v -> {
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hidirektor"));
            startActivity(githubIntent);
        });

        //Navbar button click listeners
        dashboardButton.setOnClickListener(v -> {
            //Ekranı yenileme işlemi
        });

        ordersButton.setOnClickListener(v -> {
            Intent ordersIntent = new Intent(Dashboard.this, Orders.class);
            startActivity(ordersIntent);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void hamburgerEffect() {
        restrictedArea4Hamburger.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });

        restrictedBottomArea4Hamburger.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });

        restrictedBottomAppbarArea4Hamburger.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });

        restrictedMidSection.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });
    }
}
