package me.t3sl4.gelkurye.Screens.General;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;
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
            showNavigationViewWithAnimation();
        });

        mainLayout.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && hamburgerMenu.getVisibility() == View.VISIBLE) {
                hideNavigationViewWithAnimation();
                return true;
            }
            return false;
        });

        completedOrdersButton.setOnClickListener(view -> {
            allOrdersButton.setBackgroundResource(R.drawable.allorders);
            completedOrdersButton.setBackgroundResource(R.drawable.waitingorders);
            allOrdersButton.setTextColor(getResources().getColor(R.color.editTextTopColor));
            completedOrdersButton.setTextColor(getResources().getColor(R.color.white));
        });

        allOrdersButton.setOnClickListener(view -> {
            completedOrdersButton.setBackgroundResource(R.drawable.allorders);
            allOrdersButton.setBackgroundResource(R.drawable.waitingorders);
            allOrdersButton.setTextColor(getResources().getColor(R.color.white));
            completedOrdersButton.setTextColor(getResources().getColor(R.color.editTextTopColor));
        });
    }

    private void showNavigationViewWithAnimation() {
        Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        hamburgerMenu.setVisibility(View.VISIBLE);
        hamburgerMenu.startAnimation(slideIn);
    }

    private void hideNavigationViewWithAnimation() {
        Animation slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                hamburgerMenu.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        hamburgerMenu.startAnimation(slideOut);
    }
}
