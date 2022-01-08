package com.robin.tolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME="TO_DO_TASKS";
    public static final String DB_NAME="TO_DO";
    public static final String ID ="id ";
    public static final String TASK ="task ";
    public static final String DATE ="date ";
    public static final String DETAILS ="details ";
    public static final String PRIORITY ="priority ";
    public static final String STATUS ="status ";
    public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            TASK +"TEXT NOT NULL, "+ DATE +"TEXT, "+ DETAILS +"TEXT, "+ PRIORITY +"TEXT, "+ STATUS +"TEXT"+");";


    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
