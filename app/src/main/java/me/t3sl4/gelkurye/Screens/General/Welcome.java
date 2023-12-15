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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Component.Button.ButtonManager;
import me.t3sl4.gelkurye.Util.Component.Navigation.NavigationManager;
import me.t3sl4.gelkurye.Util.Order.Order;
import me.t3sl4.gelkurye.Util.Order.OrderAdapter;

public class Welcome extends AppCompatActivity {
    private NavigationView hamburgerMenu;
    private ImageView navigationButton;
    private ConstraintLayout mainLayout;
    private ListView ordersList;

    private Button completedOrdersButton;
    private Button allOrdersButton;

    private ArrayList<Order> orderListTemp;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        hamburgerMenu = findViewById(R.id.hamburgerMenu);
        navigationButton = findViewById(R.id.navigationButton);
        mainLayout = findViewById(R.id.mainLayout);
        ordersList = findViewById(R.id.ordersListView);
        completedOrdersButton = findViewById(R.id.completedOrders);
        allOrdersButton = findViewById(R.id.allOrders);

        orderListTemp = new ArrayList<>();
        Order tempOrder = new Order("sd", "Zurna Tavuk Dürüm", "Acele Hatay Döner", "14/12/2023 21:03");
        for(int i=0; i<15; i++) {
            orderListTemp.add(tempOrder);
        }

        OrderAdapter adapter = new OrderAdapter(this, orderListTemp);
        ordersList.setAdapter(adapter);

        navigationButton.setOnClickListener(v -> {
            NavigationManager.showNavigationViewWithAnimation(hamburgerMenu, this);
        });

        mainLayout.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                NavigationManager.hideNavigationViewWithAnimation(hamburgerMenu, this);
                return true;
            }
            return false;
        });

        completedOrdersButton.setOnClickListener(view -> ButtonManager.orderButtonColorEffect(1, completedOrdersButton, allOrdersButton, this));

        allOrdersButton.setOnClickListener(view -> ButtonManager.orderButtonColorEffect(2, completedOrdersButton, allOrdersButton, this));
    }
}
