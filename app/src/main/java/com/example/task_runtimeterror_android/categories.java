package com.example.task_runtimeterror_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class categories extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    List<Category> categoryList;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        lv = findViewById(R.id.categoryList);

        categoryList = new ArrayList<>();
        sqLiteDatabase = openOrCreateDatabase("tasks_db",MODE_PRIVATE,null);

        loadCategory();
    }
    public void addCategories(View view){
        startActivity(new Intent(categories.this,addCategories.class));
    }
    public void homeBtn(View view){
        startActivity(new Intent(this,MainActivity.class));
    }
    private void loadCategory() {
        String sql = "SELECT * FROM categories";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);



        if (cursor.moveToFirst()) {
            Log.d("here","we are here");
            do {
                // create an employee instance

                categoryList.add(new Category(
                        cursor.getInt(0),
                        cursor.getString(1)

                ));

            } while (cursor.moveToNext());
            cursor.close();
        }

        categoryAdapter taskAdapt = new categoryAdapter(this,R.layout.categories_list,categoryList,sqLiteDatabase);
        lv.setAdapter(taskAdapt);
    }
}