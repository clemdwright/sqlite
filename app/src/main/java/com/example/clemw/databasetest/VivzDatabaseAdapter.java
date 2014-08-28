package com.example.clemw.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * From: https://www.youtube.com/watch?v=tsNgtAdlof8&list=PLonJJ3BVjZW5JdoFT0Rlt3ry5Mjp7s8cT&index=13
 */
public class VivzDatabaseAdapter {

    VivzHelper vivzHelper;

    public VivzDatabaseAdapter(Context context) {
        vivzHelper = new VivzHelper(context);
    }

    public long insertData(String name, String password) {
        SQLiteDatabase db = vivzHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VivzHelper.NAME, name);
        contentValues.put(VivzHelper.PASSWORD, password);
        long id = db.insert(VivzHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getAllData() {
        SQLiteDatabase db = vivzHelper.getWritableDatabase();

        //select _id, Name, Password from VIVZTABLE
        String[] columns = {VivzHelper.UID, VivzHelper.NAME, VivzHelper.PASSWORD};
        Cursor cursor = db.query(VivzHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
//            int index1 = cursor.getColumnIndex(VivzHelper.UID); //better way of doing this, in case column positions change
            int cid = cursor.getInt(0); //not a robust way to do this, passing in int directly, because column positions could change
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            buffer.append(cid + " " + name + " " + password + "\n");
        }
        return buffer.toString();
    }

    public String getData(String name, String password) {

        //select _id from vivztable where name=? AND password=?
;        SQLiteDatabase db = vivzHelper.getWritableDatabase();

        //select _id from VIVZTABLE
        String[] columns = {VivzHelper.UID};

        //using these arguments
        String[] selectionArgs = {name, password};

        Cursor cursor = db.query(
                VivzHelper.TABLE_NAME,
                columns,
                VivzHelper.NAME + " =? AND " +
                VivzHelper.PASSWORD + " =?",
                selectionArgs,
                null, null, null);

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int uidColumnIndex = cursor.getColumnIndex(VivzHelper.UID);
            String personId = cursor.getString(uidColumnIndex);
            buffer.append(personId + "\n");
        }
        return buffer.toString();
    }

    public int updateName(String oldName, String newName) {
        //UPDATE VIVZTABLE SET Name = 'vivz' where Name='test'
        SQLiteDatabase db = vivzHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VivzHelper.NAME, newName);
        String[] whereArgs = {oldName};
        int count = db.update(
                VivzHelper.TABLE_NAME,
                contentValues,
                VivzHelper.NAME + " =? ",
                whereArgs);
        return count;
    }

    public int deleteRow(String name) {

        //DELETE * FROM VIVZTABLE Where Name='vivz'
        SQLiteDatabase db = vivzHelper.getWritableDatabase();
        String[] whereArgs={name};
        int count = db.delete(VivzHelper.TABLE_NAME, VivzHelper.NAME + "=?", whereArgs);
        return count;

    }

    static class VivzHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "vivzdatabase";
        private static final String TABLE_NAME = "VIVZTABLE";
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String PASSWORD = "Password";
        private static final int DATABASE_VERSION = 4;
        private static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " VARCHAR(255), " +
                PASSWORD + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +
                TABLE_NAME;
        private Context context;

        public VivzHelper (Context context) {
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

}
