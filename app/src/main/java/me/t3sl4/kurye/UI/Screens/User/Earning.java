package me.t3sl4.kurye.UI.Screens.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import me.t3sl4.kurye.Model.Earning.EarningAdapter;
import me.t3sl4.kurye.R;
import me.t3sl4.kurye.UI.Components.NavigationBar.NavigationBarUtil;
import me.t3sl4.kurye.UI.Screens.General.Carrier.CarrierDashboard;
import me.t3sl4.kurye.UI.Screens.Order.Orders;

public class Earning extends AppCompatActivity {
    //Personal Statistics:
    private TextView totalBalance;

    //Header Buttons:
    private ImageView goBackButton;
    private Button withdrawButton;
    private Button filterButton;

    //Transaction List Definitions:
    private ListView earningList;
    private ArrayList<me.t3sl4.kurye.Model.Earning.Earning> earningListTemp;

    //Navbar Buttons
    private LinearLayout dashboardButton;
    private LinearLayout ordersButton;
    private LinearLayout earningButton;
    private LinearLayout profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_earning);

        NavigationBarUtil.hideNavigationBar(this);

        componentInitialize();

        navbarButtonClicks();

        headerButtonClicks();

        getAllEarnings();
    }

    private void componentInitialize() {
        //Personal Stats Definitions:
        totalBalance = findViewById(R.id.balanceText);

        //Header Buttons:
        goBackButton = findViewById(R.id.backButtonImage);
        withdrawButton = findViewById(R.id.withdrawButton);
        filterButton = findViewById(R.id.filterButton);

        //Navbar Buttons
        dashboardButton = findViewById(R.id.dashboardButton);
        ordersButton = findViewById(R.id.ordersButton);
        earningButton = findViewById(R.id.earningButton);
        profileButton = findViewById(R.id.profileButton);

        //Transaction List:
        earningList = findViewById(R.id.transactionsListView);
    }

    private void navbarButtonClicks() {
        //Navbar button click listeners
        dashboardButton.setOnClickListener(v -> {
            Intent dashboardIntent = new Intent(Earning.this, CarrierDashboard.class);
            finish();
            startActivity(dashboardIntent);
        });

        ordersButton.setOnClickListener(v -> {
            Intent ordersIntent = new Intent(Earning.this, Orders.class);
            finish();
            startActivity(ordersIntent);
        });

        earningButton.setOnClickListener(v -> {
            finish();
            startActivity(getIntent());
        });

        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(Earning.this, Profile.class);
            finish();
            startActivity(profileIntent);
        });
    }

    private void headerButtonClicks() {
        goBackButton.setOnClickListener(v -> {
            finish();
        });

        withdrawButton.setOnClickListener(v -> {
            //Hesaba aktarma ekranları
        });

        filterButton.setOnClickListener(v -> {
            //Filtreleme ekranı
        });
    }

    private void getAllEarnings() {
        earningListTemp = new ArrayList<>();
        me.t3sl4.kurye.Model.Earning.Earning tempEarning = new me.t3sl4.kurye.Model.Earning.Earning( "23423423", "11.05.2024 20:32", 123.23, true);
        for(int i=0; i<8; i++) {
            earningListTemp.add(tempEarning);
        }

        me.t3sl4.kurye.Model.Earning.Earning tempEarningFailed = new me.t3sl4.kurye.Model.Earning.Earning( "23423423", "11.05.2024 20:32", 13.23, false);
        for(int i=0; i<7; i++) {
            earningListTemp.add(tempEarningFailed);
        }

        EarningAdapter adapter = new EarningAdapter(this, earningListTemp);
        earningList.setAdapter(adapter);
    }
}
