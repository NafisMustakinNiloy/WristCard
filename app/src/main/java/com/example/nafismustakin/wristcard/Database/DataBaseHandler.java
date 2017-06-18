package com.example.nafismustakin.wristcard.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nafis Mustakin on 18-Jun-17.
 */

public class DataBaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wristCard.db";
    public static final String TABLE_HEARTRATE = "heartRate";
    public static final String TABLE_PRESSURE = "bloodPressure";
    public static final String TABLE_GLUCOSE = "glucoseLevel";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VALUE = "_value";
    public static final String COLUMN_TIME = "_time";

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_HEARTRATE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VALUE + " NUMBER(4,1),  " +
                COLUMN_TIME + " TIME DEFAULT CURRENT_TIME" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEARTRATE);
        onCreate(db);
    }

    public void addBPMInfo(HeartRate heartRate){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VALUE, heartRate.get_value());
        database.insert(TABLE_HEARTRATE, null, contentValues);
        database.close();
    }

}
