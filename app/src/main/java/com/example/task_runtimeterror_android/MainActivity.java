package com.example.task_runtimeterror_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    List<Task> taskList;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.tasks_list);
//        searchDet = findViewById(R.id.searchDet);
        taskList = new ArrayList<>();
        sqLiteDatabase = openOrCreateDatabase("tasks_db",MODE_PRIVATE,null);
        loadEmployees();
    }
    public void addTasks(View view){
        startActivity(new Intent(MainActivity.this,addTasks.class));
    }
    private void loadEmployees() {
        String sql = "SELECT * FROM tasks";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);



        if (cursor.moveToFirst()) {
            Log.d("here","we are here");
            do {
                // create an employee instance
                Log.d("first name","this is the "+cursor.getDouble(3));
                taskList.add(new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
                Log.d("sayo check","this is"+ taskList.get(0).getName());
            } while (cursor.moveToNext());
            cursor.close();
        }

        taskAdapter taskAdapt = new taskAdapter(this,R.layout.layout_task,taskList,sqLiteDatabase);
        lv.setAdapter(taskAdapt);
    }
}