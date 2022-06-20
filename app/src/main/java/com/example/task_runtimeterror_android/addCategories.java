package com.example.task_runtimeterror_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class addCategories extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categories);
        sqLiteDatabase = openOrCreateDatabase("tasks_db",MODE_PRIVATE,null);
        createTables();
    }
    public void createTables(){
        String sql = "CREATE TABLE IF NOT EXISTS categories(" +
                "id INTEGER NOT NULL CONSTRAINT task_pk PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(20) NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }
    public void addCategory(View view){
        TextView tName = findViewById(R.id.name);

        String name = tName.getText().toString().trim();



        if(name.isEmpty()){
            tName.setError("name field is empty");
            tName.requestFocus();
            return;
        }


        String sql = "INSERT INTO categories(name)"+
                "VALUES(?)";
        sqLiteDatabase.execSQL(sql,new String[]{name});
        Toast.makeText(addCategories.this, "Category has been added.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));

    }
}