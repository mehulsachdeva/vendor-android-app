package com.example.user.dailyveg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 18-03-2018.
 */

public class HomeActivity extends AppCompatActivity {

    TextView txt;
    Button mbtnVendor, mbtnCustomer;
    DefaultDatabaseHelper mDefaultDatabaseHelper;

    int[] quantity = {40,40,40,40,
                      40,40,40,40,
                      40,40,40,40,
                      40,40,40,40,
                      40,40,40,40,40};

    String[] vegetables_name = {"Tomato","Spinach","Cabbage","Bell Peppers",
                                "Potato","Chilli Pepper","Onion","Cucumber",
                                "Egg Plant","Garlic","Broccoli","Cauliflower",
                                "Green Peas","Lettuce","Celery","Kale","Turnip"};

    String[] price={"25","30","25","50",
                    "10","45","15","35",
                    "10","20","45","35",
                    "40","10","20","30","35"};

    Integer[] image = {
            R.drawable.tomato,
            R.drawable.spinach,
            R.drawable.cabbage,
            R.drawable.bellpepper,
            R.drawable.potato,
            R.drawable.chillipepper,
            R.drawable.onion,
            R.drawable.cucumber,
            R.drawable.eggplant,
            R.drawable.garlic,
            R.drawable.broccoli,
            R.drawable.cauliflower,
            R.drawable.greenpeas,
            R.drawable.lettuce,
            R.drawable.celery,
            R.drawable.kale,
            R.drawable.turnip,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txt = (TextView) findViewById(R.id.home_use);
        mbtnVendor = (Button) findViewById(R.id.vendor_btn);
        mbtnCustomer = (Button) findViewById(R.id.customer_btn);

        mDefaultDatabaseHelper = new DefaultDatabaseHelper(this);

        for(int i=0;i<vegetables_name.length;i++){
            mDefaultDatabaseHelper.addData(i+1,vegetables_name[i],price[i],image[i],quantity[i]);
        }

        mbtnVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent firstIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(firstIntent);
            }
        });

        mbtnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(HomeActivity.this, MainActivity2.class);
                startActivity(secondIntent);
            }
        });
    }
}