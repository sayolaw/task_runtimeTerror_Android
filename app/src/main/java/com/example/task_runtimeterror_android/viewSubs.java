package com.example.task_runtimeterror_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class viewSubs extends AppCompatActivity {
    int id;
    String task;
    SQLiteDatabase sqLiteDatabase;
    List<Sub> subList;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subs);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        task = bundle.getString("name");
        lv = findViewById(R.id.subList);

        subList = new ArrayList<>();
        sqLiteDatabase = openOrCreateDatabase("tasks_db",MODE_PRIVATE,null);

        loadSubs();
    }
    public void homeBtn(View view){
        startActivity(new Intent(this,MainActivity.class));
    }
    public void addSub(View view){
        Intent intent = new Intent(this, addSubs.class);

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("name", task);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    private void loadSubs() {
        String sql = "SELECT * FROM subtasks WHERE taskid = "+id;

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);



        if (cursor.moveToFirst()) {
            Log.d("here","we are here");
            do {
                // create an employee instance

                subList.add(new Sub(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getInt(3)

                ));

            } while (cursor.moveToNext());
            cursor.close();
        }

        subAdapter subAdapt = new subAdapter(this,R.layout.sub_layout,subList,sqLiteDatabase);
        lv.setAdapter(subAdapt);
    }

}