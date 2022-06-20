package com.example.task_runtimeterror_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class addSubs extends AppCompatActivity {
    int id;
    String task;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subs);
        Bundle bundle = getIntent().getExtras();
        sqLiteDatabase = openOrCreateDatabase("tasks_db",MODE_PRIVATE,null);
        id = bundle.getInt("id");
        task = bundle.getString("name");
    }
    public void addSub(View view){
        TextView tName = findViewById(R.id.name);

        String name = tName.getText().toString().trim();



        if(name.isEmpty()){
            tName.setError("name field is empty");
            tName.requestFocus();
            return;
        }


        String sql = "INSERT INTO subtasks(taskid,name,status)"+
                "VALUES(?,?,?)";
        sqLiteDatabase.execSQL(sql,new String[]{String.valueOf(id),name,"0"});
        Toast.makeText(addSubs.this, "subTask has been added.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,categories.class));

    }
}