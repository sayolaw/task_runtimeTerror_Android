package com.example.task_runtimeterror_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    List<Task> taskList;
    ListView lv;
    TextView searchDet;
    Spinner sorters;
    String sorting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.tasks_list);
        searchDet = findViewById(R.id.searchDet);
        taskList = new ArrayList<>();
        sqLiteDatabase = openOrCreateDatabase("tasks_db",MODE_PRIVATE,null);
        sorters = findViewById(R.id.sortlist);
        createTables();
        loadTask();
        sorters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               sorting = sorters.getSelectedItem().toString();
               sortTasks();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void createTables(){
        String sql = "CREATE TABLE IF NOT EXISTS tasks(" +
                "id INTEGER NOT NULL CONSTRAINT task_pk PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(20) NOT NULL,"+
                "status INT NOT NULL,"+
                "category VARCHAR(25),"+
                "issub VARCHAR(25),"+
                "dateCreated DATETIME NOT NULL,"+
                "completionDate DATETIME NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }
    public void addTasks(View view){
        startActivity(new Intent(MainActivity.this,addTasks.class));
    }
    public void searchTasks(View view){
        Log.d("empty:","this is empty" + searchDet.getText().toString());
        if(searchDet.getText().toString().trim().isEmpty()){
            Log.d("empty:","this is empty");
            taskList.clear();
            loadTask();
        }
        else {
            String sql = "SELECT * FROM tasks WHERE name LIKE ?";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{"%"+searchDet.getText().toString().trim()+"%"});
            Log.d("cursor", "this is the: " + cursor.getCount());

            if (cursor.moveToFirst()) {

                taskList.clear();
                do {
                    // create an employee instance
                    Log.d("first name", "this is the " + cursor.getDouble(3));
                    taskList.add(new Task(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6)
                    ));
                    Log.d("sayo check", "this is" + taskList.get(0).getName());
                } while (cursor.moveToNext());
                cursor.close();
            }
            taskAdapter taskAdapt = new taskAdapter(this, R.layout.layout_task, taskList, sqLiteDatabase);
            lv.setAdapter(taskAdapt);
        }
    }
    public void sortTasks(){


                    String sql;
                    if(sorting.equals("Title")){
                       sql = "SELECT * FROM tasks ORDER BY name ASC";
                    }
                    else{
                      sql  = "SELECT * FROM tasks ORDER BY dateCreated ASC";
                    }
       Cursor cursor = sqLiteDatabase.rawQuery(sql, null);


            if (cursor.moveToFirst()) {

                taskList.clear();
                do {
                    // create an employee instance
                    Log.d("first name", "this is the " + cursor.getDouble(3));
                    taskList.add(new Task(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6)
                    ));
                    Log.d("sayo check", "this is" + taskList.get(0).getName());
                } while (cursor.moveToNext());
                cursor.close();
            }
            taskAdapter taskAdapt = new taskAdapter(this, R.layout.layout_task, taskList, sqLiteDatabase);
            lv.setAdapter(taskAdapt);

    }
    private void loadTask() {
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