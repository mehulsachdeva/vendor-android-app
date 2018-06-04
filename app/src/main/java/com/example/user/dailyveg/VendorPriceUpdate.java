package com.example.user.dailyveg;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 18-03-2018.
 */

public class VendorPriceUpdate extends AppCompatActivity {

    TextView txt_head,txt_enter_vegetable,txt_enter_price,txt_enter_quantity;
    EditText enter_price,enter_quantity;
    Button btn_update,btn_return;
    String item,price,quantity;
    Spinner spinner;
    private static final String[] paths = { "Tomato","Spinach","Cabbage","Bell Peppers",
                                            "Potato","Chilli Pepper","Onion","Cucumber",
                                            "Egg Plant","Garlic","Broccoli","Cauliflower",
                                            "Green Peas","Lettuce","Celery","Kale","Turnip" };

    DatabaseHelper mDatabaseHelper;
    DefaultDatabaseHelper mDefaultDatabaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_price_update);
        mDatabaseHelper = new DatabaseHelper(this);
        mDefaultDatabaseHelper = new DefaultDatabaseHelper(this);

        txt_head = (TextView) findViewById(R.id.txt_head);
        txt_enter_vegetable = (TextView) findViewById(R.id.txt_enter_item);
        txt_enter_price = (TextView) findViewById(R.id.txt_enter_price);
        //enter_vegetable = (EditText) findViewById(R.id.enter_item);
        enter_price = (EditText) findViewById(R.id.enter_price);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_return = (Button) findViewById(R.id.return_home);
        txt_enter_quantity=(TextView) findViewById(R.id.txt_enter_quantity);
        enter_quantity = (EditText) findViewById(R.id.enter_quantity);

        //Display Vegetables Drop Down Menu
        spinner = (Spinner) findViewById(R.id.enter_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(VendorPriceUpdate.this, android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Set old Price of Drop Down List Vegetable

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processUpdation();
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(VendorPriceUpdate.this,MainActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void processUpdation(){
        int res1,res2;
        item = spinner.getSelectedItem().toString();
        price = enter_price.getText().toString();
        quantity = enter_quantity.getText().toString();
        int price_length = price.trim().length();
        int quantity_length = quantity.trim().length();

        if(price_length > 0 && quantity_length == 0){
            res1 = mDefaultDatabaseHelper.updateData(item,price);
            mDatabaseHelper.updateData(item,price);
            if(res1>0){
                Toast.makeText(getApplicationContext(),"Updated: " + item + " - Price " + price,Toast.LENGTH_LONG).show();

            }
        }
        else if(quantity_length > 0 && price_length == 0){
            res2 = mDefaultDatabaseHelper.updateQuantity(item,quantity);
            mDatabaseHelper.updateQuantity(item,quantity);
            if (res2>0){
                Toast.makeText(getApplicationContext(),"Updated: " + item + " - Quantity " + quantity,Toast.LENGTH_LONG).show();
            }
        }
        else if(quantity_length > 0){
            res1 = mDefaultDatabaseHelper.updateData(item,price);
            res2 = mDefaultDatabaseHelper.updateQuantity(item,quantity);
            mDatabaseHelper.updateData(item,price);
            mDatabaseHelper.updateQuantity(item,quantity);
            if (res1>0 && res2>0){
                Toast.makeText(getApplicationContext(),"Updated: " + item + " - Price " + price + " - Quantity " + quantity,Toast.LENGTH_LONG).show();

            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Update Error",Toast.LENGTH_LONG).show();
        }
    }
}
