package me.t3sl4.gelkurye.Screens.Order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.General.Dashboard;
import me.t3sl4.gelkurye.Screens.Hamburger.FAQ;
import me.t3sl4.gelkurye.Screens.User.Earning;
import me.t3sl4.gelkurye.Screens.User.Profile;
import me.t3sl4.gelkurye.Util.Component.Navigation.NavigationManager;
import me.t3sl4.gelkurye.Util.Model.Order.Order;
import me.t3sl4.gelkurye.Util.Model.Order.OrderAdapter;

public class Orders extends AppCompatActivity {
    //Personal Stats:
    private ImageView profilePhotoHamburger;
    private TextView nameSurnameHamburger;

    //Restriction Area:
    private RelativeLayout restrictedHeaderSection;
    private LinearLayout restrictedMidSection;
    private BottomAppBar restrictedBottomSection;

    //Hamburger Menu Definitions:
    private ImageView hamburgerButton;
    private NavigationView hamburgerMenu;

    //Hamburger Menu Buttons
    private Button currentOrderButton;
    private LinearLayout navAllOrdersButton;
    private LinearLayout navSettingsButton;
    private LinearLayout navFaqButton;
    private LinearLayout navSupportButton;

    //Filter Section Buttons:
    private Button completedOrdersButton;
    private Button allOrdersButton;

    //Order List:
    private ListView ordersList;
    private ArrayList<Order> orderListTemp;

    //Navbar Buttons
    private LinearLayout dashboardButton;
    private LinearLayout ordersButton;
    private LinearLayout earningButton;
    private LinearLayout profileButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        componentInitialize();

        navbarButtonClicks();

        hamburgerMenuButtonClicks();

        filterButtonClicks();

        getAllOrders();

        hamburgerEffect();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void hamburgerEffect() {
        restrictedHeaderSection.setOnClickListener(v -> {
            if (hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, Orders.this);
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

        ordersList.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });

        allOrdersButton.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });
    }

    private void componentInitialize() {
        //Restriction Areas:
        restrictedHeaderSection = findViewById(R.id.header);
        restrictedMidSection = findViewById(R.id.filterSection);
        restrictedBottomSection = findViewById(R.id.app_bar);

        //Hamburger Menu:
        hamburgerButton = findViewById(R.id.navigationButton);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);

        //Filter Section Buttons:
        completedOrdersButton = findViewById(R.id.completedOrders);
        allOrdersButton = findViewById(R.id.allOrders);

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

        //Order ListView
        ordersList = findViewById(R.id.ordersListView);
    }

    private void hamburgerMenuButtonClicks() {
        //Hamburger Menu Trigger Listener
        hamburgerButton.setOnClickListener(v -> NavigationManager.showNavigationViewWithAnimation(hamburgerMenu, this));

        //Hamburger Button Clicks
        currentOrderButton.setOnClickListener(v -> {
            //Anlık map takibi ile şuanki ekranı gösterme
        });

        navAllOrdersButton.setOnClickListener(v -> {
            Intent allOrdersIntent = new Intent(Orders.this, Orders.class);
            startActivity(allOrdersIntent);
        });

        navSettingsButton.setOnClickListener(v -> {
            //Ayarlar ekranına yönlendir
        });

        navFaqButton.setOnClickListener(v -> {
            Intent faqIntent = new Intent(Orders.this, FAQ.class);
            startActivity(faqIntent);
        });

        navSupportButton.setOnClickListener(v -> {
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hidirektor"));
            startActivity(githubIntent);
        });
    }

    private void filterButtonClicks() {
        int defaultTextColor = ContextCompat.getColor(this, R.color.editTextTopColor);
        int secondTextColor = ContextCompat.getColor(this, R.color.white);

        Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.allorders);
        Drawable secondDrawable = ContextCompat.getDrawable(this, R.drawable.waitingorders);

        allOrdersButton.setOnClickListener(v -> {
            //Tüm siparişleri listeleme
            allOrdersButton.setTextColor(secondTextColor);
            allOrdersButton.setBackground(secondDrawable);
            completedOrdersButton.setTextColor(defaultTextColor);
            completedOrdersButton.setBackground(defaultDrawable);
        });

        completedOrdersButton.setOnClickListener(v -> {
            //Sadece teslim edilen siparişleri listeleme
            completedOrdersButton.setTextColor(secondTextColor);
            completedOrdersButton.setBackground(secondDrawable);
            allOrdersButton.setTextColor(defaultTextColor);
            allOrdersButton.setBackground(defaultDrawable);
        });
    }

    private void navbarButtonClicks() {
        //Navbar button click listeners
        dashboardButton.setOnClickListener(v -> {
            Intent dashboardIntent = new Intent(Orders.this, Dashboard.class);
            finish();
            startActivity(dashboardIntent);
        });

        ordersButton.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });

        earningButton.setOnClickListener(v -> {
            Intent earningIntent = new Intent(Orders.this, Earning.class);
            finish();
            startActivity(earningIntent);
        });

        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(Orders.this, Profile.class);
            finish();
            startActivity(profileIntent);
        });
    }

    private void getAllOrders() {
        orderListTemp = new ArrayList<>();
        Order tempOrder = new Order("sd", "Zurna Tavuk Dürüm", "Acele Hatay Döner", "14/12/2023 21:03");
        for(int i=0; i<15; i++) {
            orderListTemp.add(tempOrder);
        }

        OrderAdapter adapter = new OrderAdapter(this, orderListTemp);
        ordersList.setAdapter(adapter);
    }
}
