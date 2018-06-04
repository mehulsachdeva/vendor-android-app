package com.example.user.dailyveg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelperBilling extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "vegetables_billing";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    //private static final String COLUMN_QUANTITY ="quantity";
    //private static final String COLUMN_AVAILABLE ="quantity_available";

    public DatabaseHelperBilling(Context context) {
        super(context, TABLE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_PRICE +" TEXT);";// + COLUMN_QUANTITY + " TEXT, " + COLUMN_AVAILABLE + " TEXT);";
        db.execSQL(createTable);
        Log.d(TAG,"Table Created " + TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropQuery = "DROP IF TABLE EXISTS " + TABLE_NAME;
        db.execSQL(dropQuery);
        onCreate(db);
    }

    public boolean addData(String item1,String item2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME,item1);
        contentValues.put(COLUMN_PRICE,item2);

        Log.d(TAG, "addData: Adding " + item1 + item2 + " to " + TABLE_NAME);

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
        String removeQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = " + "'" + item + "'";
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
        //String query = "SELECT * FROM " + TABLE_NAME + ";";
        //Cursor cursor = db.rawQuery(query, null);
        //int count = cursor.getCount();
        //cursor.close();
        //return count;
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
