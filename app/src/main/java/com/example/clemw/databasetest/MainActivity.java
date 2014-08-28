package com.example.clemw.databasetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    EditText userName, password;
    VivzDatabaseAdapter vivzDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        userName = (EditText) findViewById(R.id.userNameValue);
        password = (EditText) findViewById(R.id.passwordValue);

        vivzDatabaseAdapter = new VivzDatabaseAdapter(this);

    }

    public void addUser(View view) {
        String user = userName.getText().toString();
        String pass = password.getText().toString();

        long id = vivzDatabaseAdapter.insertData(user, pass);
        if (id < 0) {
            Message.message(this, "Unsuccessful");
        } else {
            Message.message(this, "Successfully inserted a row");
        }
    }

    public void viewDetails (View view) {
        String data = vivzDatabaseAdapter.getAllData();
        Message.message(this, data);
    }
}
