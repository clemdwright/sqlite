package com.example.clemw.databasetest;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * From: https://www.youtube.com/watch?v=tsNgtAdlof8&list=PLonJJ3BVjZW5JdoFT0Rlt3ry5Mjp7s8cT&index=13
 */
public class VivzHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "vivzdatabase";
    private static final String TABLE_NAME = "VIVZTABLE";
    private static final String UID = "_id";
    private static final String NAME = "Name";
    private static final int DATABASE_VERSION = 3;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private Context context;

    public VivzHelper(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Message.message(context, "Constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
            Message.message(context, "onCreate called");
        } catch (android.database.SQLException e) {
            e.printStackTrace();
            Message.message(context, ""+e);
        }
    }

    // Make sure to upgrade version number to make onUpgrade get called
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // CMD + ALT + T to automatically surround with try catch
        try {
            Message.message(context, "onUpgrade called");
            // This deletes the table
            db.execSQL(DROP_TABLE);
            // New database being created
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
