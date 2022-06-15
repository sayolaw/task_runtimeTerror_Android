package com.example.task_runtimeterror_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class addTasks extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    String CompletionDate;
//    List<Task> productList;
//    ListView lv;

    TextView searchDet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        CalendarView tDate = findViewById(R.id.cDate);
        sqLiteDatabase = openOrCreateDatabase("tasks_db",MODE_PRIVATE,null);
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
    public void addTask(View view){
        TextView tName = findViewById(R.id.name);
        Spinner tCategory = findViewById(R.id.category);
        String isSub;
        Switch tIsSub  = findViewById(R.id.isSub);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CANADA);
        String date = sdf.format(calendar.getTime());
        if(tIsSub.isChecked()){
            isSub = "1";
        }
        else{
            isSub = "0";
        }
        String name = tName.getText().toString().trim();
        String category = tCategory.getSelectedItem().toString().trim();
        String cDate = CompletionDate;
        Toast.makeText(addTasks.this, "name:"+name+" category: " +category+
                " isSub: " +isSub+" date_created: " + date+" date completed: "+cDate
                , Toast.LENGTH_LONG).show();

        if(name.isEmpty()){
            tName.setError("name field is empty");
            tName.requestFocus();
            return;
        }


        String sql = "INSERT INTO tasks(name,category,status,isSub,dateCreated,completionDate)"+
                "VALUES(?, ?, ?, ?, ?, ?)";
        sqLiteDatabase.execSQL(sql,new String[]{name,category, "0",isSub,date,cDate});
        Toast.makeText(addTasks.this, "Task has been added.", Toast.LENGTH_SHORT).show();

    }
}