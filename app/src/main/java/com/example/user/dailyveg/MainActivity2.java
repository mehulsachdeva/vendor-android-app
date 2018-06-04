package com.example.user.dailyveg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by user on 16-03-2018.
 */

public class MainActivity2 extends AppCompatActivity {

    MyCustomAdapter dataAdapter = null;
    TextView txt;
    Button myButton;
    int checked_item=0;

    DatabaseHelper mDatabaseHelper;
    DatabaseHelperBilling mDatabaseHelperBilling;
    DefaultDatabaseHelper mDefaultDatabaseHelper;

    ArrayList<ArrayList<String>> checkedarray = new ArrayList<ArrayList<String>>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt = (TextView) findViewById(R.id.text_head_activity2);
        myButton = (Button) findViewById(R.id.get_bill);

        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelperBilling = new DatabaseHelperBilling(this);
        mDefaultDatabaseHelper = new DefaultDatabaseHelper(this);

        mDatabaseHelperBilling.removeAllRecords();

        processData();
        displayListView();

        myButton.setBackgroundColor(getResources().getColor(R.color.red_disable));

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"No Vegetable Selected",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void processData(){
        Cursor data = mDatabaseHelper.getData();

        while(data.moveToNext()){
            ArrayList<String> listData = new ArrayList<>();
            listData.add(data.getString(1));
            listData.add(data.getString(2));
            listData.add(data.getString(3));
            listData.add(data.getString(4));
            checkedarray.add(listData);
        }
    }

    private void displayListView(){

        //Display List Of Vegetables
        ArrayList<Vegetable> countryList = new ArrayList<Vegetable>();
        for(int i=0;i<mDatabaseHelper.getRowsCount();i++){
            Vegetable vegetable = new Vegetable(Integer.parseInt(checkedarray.get(i).get(2)),checkedarray.get(i).get(0),checkedarray.get(i).get(1),checkedarray.get(i).get(3),false);
            countryList.add(vegetable);
        }

        //create an ArrayAdapter from the String Array
        dataAdapter = new MainActivity2.MyCustomAdapter(this, R.layout.country_info, countryList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Vegetable vegetable = (Vegetable) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Clicked on Row: " + vegetable.getName() + " - " + vegetable.getPrice(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Vegetable> {

        private ArrayList<Vegetable> vegetableList;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Vegetable> countryList) {
            super(context, textViewResourceId, countryList);
            this.vegetableList = new ArrayList<Vegetable>();
            this.vegetableList.addAll(countryList);
        }

        private class ViewHolder {
            ImageView image;
            CheckBox name;
            TextView price;
            TextView quantity;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MainActivity2.MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info, null);

                holder = new MainActivity2.MyCustomAdapter.ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image_list);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.price = (TextView) convertView.findViewById(R.id.text_view_price);
                holder.quantity = (TextView) convertView.findViewById(R.id.text_view_quantity);
                convertView.setTag(holder);


                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Vegetable vegetable = (Vegetable) cb.getTag();

                        if(cb.isChecked()== true){
                            checked_item++;
                        }
                        else{
                            checked_item--;
                        }

                        if(checked_item == 0){
                            myButton.setBackgroundColor(getResources().getColor(R.color.red_disable));
                            myButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(),"No Vegetable Selected",Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{
                            myButton.setBackgroundColor(getResources().getColor(R.color.red_active));
                            checkButtonClick();
                        }

                        //Toast.makeText(getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + " - " + checked_item, Toast.LENGTH_LONG).show();
                        vegetable.setSelected(cb.isChecked());


                    }
                });
            }
            else {
                holder = (MainActivity2.MyCustomAdapter.ViewHolder) convertView.getTag();
            }

            Vegetable vegetable = vegetableList.get(position);
            holder.image.setImageResource(vegetable.getImageId());
            holder.name.setText(vegetable.getName());
            holder.name.setChecked(vegetable.isSelected());
            holder.name.setTag(vegetable);
            holder.price.setText("Price Per Unit :" + vegetable.getPrice() + "  Qty. ");
            holder.quantity.setText(vegetable.getQuantity());

            if(Integer.parseInt(holder.quantity.getText().toString()) == 0){
                holder.quantity.setText("N/A");
                holder.name.setEnabled(false);
            }
            else{
                holder.name.setEnabled(true);
            }

            return convertView;
        }
    }

    private void checkButtonClick() {

        myButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                    ArrayList<Vegetable> vegetableListBill = dataAdapter.vegetableList;
                    for (int i = 0; i < vegetableListBill.size(); i++) {
                        Vegetable vegetable = vegetableListBill.get(i);
                        if (vegetable.isSelected()) {
                            int quantity = Integer.parseInt(vegetable.getQuantity())-1;
                            mDatabaseHelperBilling.addData(vegetable.getName(), vegetable.getPrice());
                            mDatabaseHelper.updateQuantity(vegetable.getName(),Integer.toString(quantity));
                            mDefaultDatabaseHelper.updateQuantity(vegetable.getName(),Integer.toString(quantity));
                        }
                    }
                    Intent mynewIntent = new Intent(MainActivity2.this, PaymentActivity.class);
                    startActivity(mynewIntent);
               }
        });
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
                Intent myIntent = new Intent(MainActivity2.this,OrderHistoryDisplay.class);
                startActivity(myIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

