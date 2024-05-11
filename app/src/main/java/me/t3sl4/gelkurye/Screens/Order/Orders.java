package me.t3sl4.gelkurye.Screens.Order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Screens.Hamburger.FAQ;
import me.t3sl4.gelkurye.Screens.User.Earning;
import me.t3sl4.gelkurye.Screens.User.Profile;
import me.t3sl4.gelkurye.Util.Component.Navigation.NavigationManager;
import me.t3sl4.gelkurye.Util.Model.Order.Order;
import me.t3sl4.gelkurye.Util.Model.Order.OrderAdapter;

public class Orders extends AppCompatActivity {
    private ImageView hamburgerButton;
    private NavigationView hamburgerMenu;
    private ConstraintLayout restrictedArea4Hamburger;
    private CoordinatorLayout restrictedBottomArea4Hamburger;
    private BottomAppBar restrictedBottomAppbarArea4Hamburger;


    private ListView ordersList;
    private ArrayList<Order> orderListTemp;

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
        setContentView(R.layout.activity_orders);

        hamburgerButton = findViewById(R.id.navigationButton);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);
        restrictedArea4Hamburger = findViewById(R.id.mainLayout);
        restrictedBottomArea4Hamburger = findViewById(R.id.bottomBar);
        restrictedBottomAppbarArea4Hamburger = findViewById(R.id.app_bar);

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

        //ListView
        ordersList = findViewById(R.id.ordersListView);

        orderListTemp = new ArrayList<>();
        Order tempOrder = new Order("sd", "Zurna Tavuk Dürüm", "Acele Hatay Döner", "14/12/2023 21:03");
        for(int i=0; i<15; i++) {
            orderListTemp.add(tempOrder);
        }

        OrderAdapter adapter = new OrderAdapter(this, orderListTemp);
        ordersList.setAdapter(adapter);

        //Navbar Buttons click events
        dashboardButton.setOnClickListener(v -> finish());

        ordersButton.setOnClickListener(v -> {
            //Siparişleri yenileme işlemi
        });

        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(Orders.this, Profile.class);
            startActivity(profileIntent);
        });

        earningButton.setOnClickListener(v -> {
            Intent earningIntent = new Intent(Orders.this, Earning.class);
            startActivity(earningIntent);
        });

        //Hamburger Menu Click events
        hamburgerButton.setOnClickListener(v -> NavigationManager.showNavigationViewWithAnimation(hamburgerMenu, this));

        navFaqButton.setOnClickListener(v -> {
            Intent faqIntent = new Intent(Orders.this, FAQ.class);
            startActivity(faqIntent);
        });

        hamburgerEffect();
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

        ordersList.setOnTouchListener((view, motionEvent) -> {
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
    }
}
