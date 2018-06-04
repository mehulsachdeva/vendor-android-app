package com.example.user.dailyveg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by user on 17-03-2018.
 */

public class MainActivity3 extends AppCompatActivity {
    DatabaseHelperBilling mDatabaseHelperBilling;
    OrderHistory mOrderHistory;
    int total_price;
    TextView head,subhead,date,list,total,endline,lastline,head_list;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_generated);

        mDatabaseHelperBilling = new DatabaseHelperBilling(this);
        mOrderHistory = new OrderHistory(this);

        head = (TextView) findViewById(R.id.bill_head);
        subhead = (TextView) findViewById(R.id.bill_subhead);
        date = (TextView) findViewById(R.id.date);
        head_list = (TextView) findViewById(R.id.head_list);
        list = (TextView) findViewById(R.id.display_bought_vegetables);
        total = (TextView) findViewById(R.id.bill_total);
        endline = (TextView) findViewById(R.id.bill_bottom);
        lastline = (TextView) findViewById(R.id.bill_bottom_bottom);
        btn = (Button) findViewById(R.id.return_main);

        //long date = System.currentTimeMillis();

        displayData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMainHome();
            }
        });
    }
    public void displayData() {

        Cursor data = mDatabaseHelperBilling.getData();

        while (data.moveToNext()){
            list.append(data.getString(1) + "\t\t\t\t\t\t\t\t\t\t\t\t\t");
            list.append(data.getString(2) + "\n");
            total_price = Integer.parseInt(data.getString(2)) + total_price;
        }
        total.append("\t " + total_price);

        Cursor data2 = mOrderHistory.getData();
        data2.moveToLast();
        date.setText(data2.getString(4));
        //mOrderHistory.addData(Long.toString(mDatabaseHelperBilling.getRowsCount()),Integer.toString(total_price));
    }

    public void returnMainHome(){
        Intent myIntent = new Intent(MainActivity3.this,HomeActivity.class);
        startActivity(myIntent);
        mDatabaseHelperBilling.removeAllRecords();
    }
}
