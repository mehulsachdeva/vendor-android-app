package com.example.user.dailyveg;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by user on 18-03-2018.
 */

public class OrderHistoryDisplay extends AppCompatActivity {

    TextView txt;
    OrderHistory mOrderHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history_layout);

        mOrderHistory = new OrderHistory(this);
        txt = (TextView) findViewById(R.id.order_display);

        processData();
    }

    public void processData(){
        Cursor data = mOrderHistory.getData();

        while(data.moveToNext()){
            txt.append("Order #: " +  data.getString(0) + "\n");
            txt.append("Buyer: " +  data.getString(1) + "\n");
            txt.append("Date Purchased: " +  data.getString(4) + "\n");
            txt.append("Number of Items Purchased: " + data.getString(2) + "\n");
            txt.append("Grand Total: " + data.getString(3) + "\n");
            txt.append("Address: " +  data.getString(5) + "\n\n\n");
        }
    }
}
