package me.t3sl4.gelkurye.Screens.User;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import me.t3sl4.gelkurye.R;
import me.t3sl4.gelkurye.Util.Model.Earning.EarningAdapter;
import me.t3sl4.gelkurye.Util.Model.Order.Order;
import me.t3sl4.gelkurye.Util.Model.Order.OrderAdapter;

public class Earning extends AppCompatActivity {
    private ListView earningList;
    private ArrayList<me.t3sl4.gelkurye.Util.Model.Earning.Earning> earningListTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);

        //ListView
        earningList = findViewById(R.id.transactionsListView);

        earningListTemp = new ArrayList<>();
        me.t3sl4.gelkurye.Util.Model.Earning.Earning tempEarning = new me.t3sl4.gelkurye.Util.Model.Earning.Earning( "23423423", "11.05.2024 20:32", 123.23, true);
        for(int i=0; i<8; i++) {
            earningListTemp.add(tempEarning);
        }

        me.t3sl4.gelkurye.Util.Model.Earning.Earning tempEarningFailed = new me.t3sl4.gelkurye.Util.Model.Earning.Earning( "23423423", "11.05.2024 20:32", 13.23, false);
        for(int i=0; i<7; i++) {
            earningListTemp.add(tempEarningFailed);
        }

        EarningAdapter adapter = new EarningAdapter(this, earningListTemp);
        earningList.setAdapter(adapter);
    }
}
