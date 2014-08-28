package com.example.clemw.databasetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    EditText createName, password, enterName, enterPassword, nameToDelete, oldName, newName;
    VivzDatabaseAdapter vivzDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        createName = (EditText) findViewById(R.id.createName);
        password = (EditText) findViewById(R.id.createPassword);
        enterName = (EditText) findViewById(R.id.enterName);
        enterPassword = (EditText) findViewById(R.id.enterPassword);
        oldName = (EditText) findViewById(R.id.oldName);
        newName = (EditText) findViewById(R.id.newName);
        nameToDelete = (EditText) findViewById(R.id.nameToDelete);

        vivzDatabaseAdapter = new VivzDatabaseAdapter(this);

    }

    public void addUser(View view) {
        String user = createName.getText().toString();
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

    public void getDetails(View view) {
        String user = enterName.getText().toString();
        String pass = enterPassword.getText().toString();
        String _id = vivzDatabaseAdapter.getData(user, pass);
        Message.message(this, _id);
    }

    public void update(View view) {

        String oldString = oldName.getText().toString();
        String newString = newName.getText().toString();

        int count = vivzDatabaseAdapter.updateName(oldString, newString);
        Message.message(this, "" + count);
    }

    public void delete(View view) {

        String name = nameToDelete.getText().toString();
        int count = vivzDatabaseAdapter.deleteRow(name);
        Message.message(this, "" + count);

    }
}
