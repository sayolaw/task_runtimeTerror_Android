package com.example.task_runtimeterror_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class viewSubs extends AppCompatActivity {
    int id;
    String task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subs);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        task = bundle.getString("name");
    }
    public void addSub(View view){
        Intent intent = new Intent(this, addSubs.class);

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("name", task);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}