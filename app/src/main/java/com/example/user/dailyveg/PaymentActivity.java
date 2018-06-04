package com.example.user.dailyveg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by user on 20-03-2018.
 */

public class PaymentActivity extends AppCompatActivity{

    TextView payment_head,txt_name,txt_address;
    EditText enter_name,enter_address1,enter_address2;
    Button btn,btn_return;
    int total_price=0;

    DefaultDatabaseHelper mDefaultDatabaseHelper;
    DatabaseHelperBilling mDatabaseHelperBilling;
    OrderHistory mOrderHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_payment);

        mDefaultDatabaseHelper = new DefaultDatabaseHelper(this);
        mDatabaseHelperBilling = new DatabaseHelperBilling(this);
        mOrderHistory = new OrderHistory(this);

        payment_head = (TextView) findViewById(R.id.payment_head);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_address = (TextView) findViewById(R.id.txt_address);
        enter_name = (EditText) findViewById(R.id.enter_name);
        enter_address1 = (EditText) findViewById(R.id.enter_address);
        enter_address2 = (EditText) findViewById(R.id.enter_address_2);
        btn = (Button) findViewById(R.id.button);
        btn_return = (Button) findViewById(R.id.return_back);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = enter_address1.getText() + " " + enter_address2.getText();
                final Calendar c = Calendar.getInstance();
                int yy = c.get(Calendar.YEAR);
                int mm = c.get(Calendar.MONTH);
                int dd = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);
                String date = dd + " - " + (mm + 1) + " - " + yy + "  " + hour + ":" + min;
                getTotalPrice();
                mOrderHistory.addData(enter_name.getText().toString(),Long.toString(mDatabaseHelperBilling.getRowsCount()),Integer.toString(total_price),date,address);

                Intent myIntent = new Intent(PaymentActivity.this,MainActivity3.class);
                startActivity(myIntent);
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PaymentActivity.this,HomeActivity.class);
                startActivity(myIntent);
            }
        });

    }
    public void getTotalPrice() {
        Cursor data = mDatabaseHelperBilling.getData();
        while (data.moveToNext()) {
            total_price = Integer.parseInt(data.getString(2)) + total_price;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.credits:
                Toast.makeText(getApplicationContext(),"Made By Mehul Sachdeva",Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(getApplicationContext(),"Page Not Found",Toast.LENGTH_SHORT).show();
                break;
            case R.id.clear_db_bill:
                mDatabaseHelperBilling.removeAllRecords();
                Toast.makeText(getApplicationContext(),"Cleared Bill Successfully",Toast.LENGTH_LONG).show();
                break;
            case R.id.order_history:
                Intent myIntent = new Intent(PaymentActivity.this,OrderHistoryDisplay.class);
                startActivity(myIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
