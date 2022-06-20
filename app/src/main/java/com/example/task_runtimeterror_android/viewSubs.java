package com.example.task_runtimeterror_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class viewSubs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subs);
    }
    public void addSub(View view){
        startActivity(new Intent(viewSubs.this,addSubs.class));
        TextView tName = findViewById(R.id.su);

        String name = tName.getText().toString().trim();



        if(name.isEmpty()){
            tName.setError("name field is empty");
            tName.requestFocus();
            return;
        }


        String sql = "INSERT INTO categories(name)"+
                "VALUES(?)";
        sqLiteDatabase.execSQL(sql,new String[]{name});
        Toast.makeText(addCategories.this, "Task has been added.", Toast.LENGTH_SHORT).show();

    }

}