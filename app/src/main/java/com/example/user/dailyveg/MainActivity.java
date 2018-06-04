package com.example.user.dailyveg;
        import java.util.ArrayList;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.View.OnClickListener;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity {

    //ArrayList<String> arrayVegetables = new ArrayList<>();
    ArrayList<String> arrayPrices = new ArrayList<>();
    Button myButton;
    int checked_item = 0;

    public MyCustomAdapter dataAdapter = null;

    DatabaseHelper mDatabaseHelper;
    DefaultDatabaseHelper mDefaultDatabaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button) findViewById(R.id.findSelected);

        mDatabaseHelper = new DatabaseHelper(this);
        mDefaultDatabaseHelper = new DefaultDatabaseHelper(this);

        displayListView();

        myButton.setBackgroundColor(getResources().getColor(R.color.green_disable));

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"No Vegetable Selected",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void displayListView(){
        Cursor data = mDefaultDatabaseHelper.getData();
        //Display List Of Vegetables
        ArrayList<Vegetable> countryList = new ArrayList<Vegetable>();
        while(data.moveToNext()){
            Vegetable vegetable = new Vegetable(data.getInt(3) ,data.getString(1),data.getString(2),data.getString(4),false);
            countryList.add(vegetable);
        }

        //create an ArrayAdapter from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.country_info, countryList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
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

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info, null);

                holder = new ViewHolder();
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
                            myButton.setBackgroundColor(getResources().getColor(R.color.green_disable));
                            myButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(),"No Vegetable Selected",Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{
                            myButton.setBackgroundColor(getResources().getColor(R.color.green_active));
                            checkButtonClick();
                        }

                        //Toast.makeText(getApplicationContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                        vegetable.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Vegetable vegetable = vegetableList.get(position);
            holder.image.setImageResource(vegetable.getImageId());
            holder.name.setText(vegetable.getName());
            holder.name.setChecked(vegetable.isSelected());
            holder.name.setTag(vegetable);
            holder.price.setText("Price Per Unit :" + vegetable.getPrice() + "  Qty. ");
            holder.quantity.setText(vegetable.getQuantity());

            return convertView;
        }
    }

    private void checkButtonClick() {

        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<Vegetable> vegetableList = dataAdapter.vegetableList;
                for(int i=0;i<vegetableList.size();i++){
                    Vegetable vegetable = vegetableList.get(i);
                    if(vegetable.isSelected()){
                        mDatabaseHelper.addData(vegetable.getName(),vegetable.getPrice(),Integer.toString(vegetable.getImageId()),vegetable.getQuantity());
                    }
                }

                Toast.makeText(getApplicationContext(),"Inserted Available Vegetables",Toast.LENGTH_LONG).show();


            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
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
            case R.id.clear_customer_db:
                mDatabaseHelper.removeAllRecords();
                Toast.makeText(getApplicationContext(),"Cleared All Available Vegetables For Customers",Toast.LENGTH_LONG).show();
                break;
            case R.id.update:
                Intent myIntent = new Intent(MainActivity.this,VendorPriceUpdate.class);
                startActivity(myIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}