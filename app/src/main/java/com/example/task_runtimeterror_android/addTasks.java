package com.example.task_runtimeterror_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class addTasks extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    String CompletionDate;

    ArrayList<String> categoryList = new ArrayList<String>();


//    List<Task> productList;
//    ListView lv;

    TextView searchDet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        CalendarView tDate = findViewById(R.id.cDate);
        sqLiteDatabase = openOrCreateDatabase("tasks_db",MODE_PRIVATE,null);
        Spinner tCategory = findViewById(R.id.category);
        String cat = "SELECT * FROM categories";
        Cursor cursor = sqLiteDatabase.rawQuery(cat, null);
        Log.d("cursor", "this is the: " + cursor.getCount());

        if (cursor.moveToFirst()) {

            do {
                // create an employee instance

                categoryList.add(new String(
                        cursor.getString(1)
                ));

            } while (cursor.moveToNext());
            cursor.close();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList);
        tCategory.setAdapter(adapter);
        tDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String curDate = String.valueOf(i2);
                String month = String.valueOf(i1);
                String year = String.valueOf(i);
                CompletionDate = year + "-" + month + "-" + curDate;

            }
        });
        createTables();
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
    public void addImages(View view){
    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
    startActivityForResult(intent,3);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
    Log.d("image","selected");
    if(resultCode == RESULT_OK && data!=null){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        ArrayList<String> imagesEncodedList = new ArrayList<String>();
        String imageEncoded;
        if(data.getClipData() != null) {
            int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
            for(int i = 0; i < count; i++) {
                Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                imagesEncodedList.add(imageUri);
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        }
    } else if(data.getData() != null) {
        String imagePath = data.getData().getPath();
        //do something with the image (save it to some directory or whatever you need to do with it here)
    }
        }


    public void addTask(View view){
        TextView tName = findViewById(R.id.name);
        Spinner tCategory = findViewById(R.id.category);
        String isSub;
//        Switch tIsSub  = findViewById(R.id.isSub);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
        String date = sdf.format(calendar.getTime());
//        if(tIsSub.isChecked()){
//            isSub = "1";
//        }
//        else{
//            isSub = "0";
//        }
        String name = tName.getText().toString().trim();
        String category = tCategory.getSelectedItem().toString().trim();
        String cDate = CompletionDate;
        Toast.makeText(addTasks.this, "name:"+name+" category: " +category+
                " isSub: date_created: " + date+" date completed: "+cDate
                , Toast.LENGTH_LONG).show();

        if(name.isEmpty()){
            tName.setError("name field is empty");
            tName.requestFocus();
            return;
        }


        String sql = "INSERT INTO tasks(name,category,status,isSub,dateCreated,completionDate)"+
                "VALUES(?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(sql,new String[]{name,category, "0","0",date,cDate});
        Toast.makeText(addTasks.this, "Task has been added.", Toast.LENGTH_SHORT).show();

    }
}