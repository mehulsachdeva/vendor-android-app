package com.example.user.dailyveg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class OrderHistory extends SQLiteOpenHelper {

    private static final String TAG = "OrderHistoryDatabaseHelper";

    private static final String TABLE_NAME = "orders";
    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_CUSTOMER_NAME = "name";
    private static final String COLUMN_ITEMS = "items";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_ADDRESS = "address";
    //private static final String COLUMN_IMAGE_ID = "image";
    //private static final String COLUMN_QUANTITY ="quantity";

    public OrderHistory(Context context) {
        super(context, TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_ITEMS + " TEXT, " + COLUMN_PRICE + " TEXT, " + COLUMN_DATE + " TEXT, " + COLUMN_ADDRESS + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropQuery = "DROP IF TABLE EXISTS " + TABLE_NAME;
        db.execSQL(dropQuery);
        onCreate(db);
    }

    public boolean addData(String item1,String item2,String item3,String item4,String item5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CUSTOMER_NAME,item1);
        contentValues.put(COLUMN_ITEMS,item2);
        contentValues.put(COLUMN_PRICE,item3);
        contentValues.put(COLUMN_DATE,item4);
        contentValues.put(COLUMN_ADDRESS,item5);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public void removeData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String removeQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ITEMS + " = " + "'" + item + "'";
        db.execSQL(removeQuery);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public long getRowsCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        return count;
    }

    public void removeAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }
}
