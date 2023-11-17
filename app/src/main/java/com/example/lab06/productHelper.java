package com.example.lab06;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class productHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "product";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";

    public productHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " DOUBLE" +")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addproduct(product pd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, pd.getId());
        values.put(COLUMN_NAME, pd.getName());
        values.put(COLUMN_PRICE, pd.getPrice());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<product> getAllproduct() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<product> products = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                product pd = new product();
                pd.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                pd.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                pd.setPrice(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)));
                products.add(pd);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return products;
    }

    public void deleteproduct(product pd) {
        SQLiteDatabase db = this.getWritableDatabase();
        String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(pd.getId())});
        db.close();
    }

    public void updateproduct(product pd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pd.getName());
        values.put(COLUMN_PRICE, pd.getPrice());
        String UPDATE = "UPDATE " + TABLE_NAME + " SET "
                + COLUMN_NAME + " = ?,"
                + COLUMN_PRICE + " = ?,"
                + " WHERE " + COLUMN_ID + " = ?";
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(pd.getId())});
        db.close();
    }
}