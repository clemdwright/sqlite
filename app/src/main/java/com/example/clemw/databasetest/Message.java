package com.example.clemw.databasetest;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by clemw on 8/27/14.
 */
public class Message {
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
