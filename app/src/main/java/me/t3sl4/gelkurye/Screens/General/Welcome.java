package me.t3sl4.gelkurye.Screens.General;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.Button.ButtonManager;
import me.t3sl4.gelkurye.Util.Component.Navigation.NavigationManager;
import me.t3sl4.gelkurye.Util.Order.Order;
import me.t3sl4.gelkurye.Util.Order.OrderAdapter;

public class Welcome extends AppCompatActivity {
    private ImageView hamburgerButton;
    private NavigationView hamburgerMenu;
    private ImageView profilePhoto;
    private ConstraintLayout restrictedArea4Hamburger;

    private Button completedOrdersButton;
    private Button allOrdersButton;

    private ListView ordersList;
    private ArrayList<Order> orderListTemp;

    private ImageView homeButton;
    private ImageView ordersButton;
    private ImageView profileButton;
    private ImageView settingsButton;
    private FloatingActionButton currentOrderButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        hamburgerButton = findViewById(R.id.navigationButton);
        hamburgerMenu = findViewById(R.id.hamburgerMenu);
        profilePhoto = findViewById(R.id.profilePhotoImageView);
        restrictedArea4Hamburger = findViewById(R.id.mainLayout);

        completedOrdersButton = findViewById(R.id.completedOrders);
        allOrdersButton = findViewById(R.id.allOrders);

        ordersList = findViewById(R.id.ordersListView);
        orderListTemp = new ArrayList<>();
        Order tempOrder = new Order("sd", "Zurna Tavuk Dürüm", "Acele Hatay Döner", "14/12/2023 21:03");
        for(int i=0; i<15; i++) {
            orderListTemp.add(tempOrder);
        }

        homeButton = findViewById(R.id.bottomHomeIcon);
        ordersButton = findViewById(R.id.bottomOrdersIcon);
        profileButton = findViewById(R.id.bottomProfileIcon);
        settingsButton = findViewById(R.id.bottomSettingsIcon);
        currentOrderButton = findViewById(R.id.currentOrderFAB);

        OrderAdapter adapter = new OrderAdapter(this, orderListTemp);
        ordersList.setAdapter(adapter);

        hamburgerButton.setOnClickListener(v -> NavigationManager.showNavigationViewWithAnimation(hamburgerMenu, this));

        hamburgerEffect();
        filterColorEffect();
    }

    private void filterColorEffect() {
        completedOrdersButton.setOnClickListener(view -> ButtonManager.orderButtonColorEffect(1, completedOrdersButton, allOrdersButton, this));

        allOrdersButton.setOnClickListener(view -> ButtonManager.orderButtonColorEffect(2, completedOrdersButton, allOrdersButton, this));
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
    }
}
