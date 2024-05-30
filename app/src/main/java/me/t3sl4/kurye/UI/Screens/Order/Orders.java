package me.t3sl4.kurye.UI.Screens.Order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.General.Dashboard;
import me.t3sl4.kurye.UI.Screens.User.Earning;
import me.t3sl4.kurye.UI.Screens.User.Profile;
import me.t3sl4.kurye.Model.Order.Order;
import me.t3sl4.kurye.Model.Order.OrderAdapter;

public class Orders extends AppCompatActivity {
    //Header Buttons:
    private ImageView goBackButton;

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
        setContentView(R.layout.orders);

        NavigationBarUtil.hideNavigationBar(this);

        componentInitialize();

        headerButtonClicks();

        navbarButtonClicks();

        filterButtonClicks();

        getAllOrders();
    }

    private void componentInitialize() {
        //Header Buttons:
        goBackButton = findViewById(R.id.backButtonImage);

        //Filter Section Buttons:
        completedOrdersButton = findViewById(R.id.completedOrders);
        allOrdersButton = findViewById(R.id.allOrders);

        //Navbar Buttons
        dashboardButton = findViewById(R.id.dashboardButton);
        ordersButton = findViewById(R.id.ordersButton);
        earningButton = findViewById(R.id.earningButton);
        profileButton = findViewById(R.id.profileButton);

        //Order ListView
        ordersList = findViewById(R.id.ordersListView);
    }

    private void headerButtonClicks() {
        goBackButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void filterButtonClicks() {
        int defaultTextColor = ContextCompat.getColor(this, R.color.editTextTopColor);
        int secondTextColor = ContextCompat.getColor(this, R.color.white);

        Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.allorders);
        Drawable secondDrawable = ContextCompat.getDrawable(this, R.drawable.background_waitingorders);

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
