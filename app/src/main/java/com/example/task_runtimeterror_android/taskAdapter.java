package com.example.task_runtimeterror_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class taskAdapter extends ArrayAdapter {
    Context context;
    int layoutRes;

    String CompletionDate;
    List<Task> taskModelList;
    ArrayList<String> categoryList = new ArrayList<String>();
    ListView lv;
    SQLiteDatabase sqLiteDatabase;

//    public void deleteTask(Task taskModel){
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Are you sure you want to delete this product");
//        builder.setPositiveButton("Yes",(dialogInterface, i) -> {
//            String sql = "DELETE FROM products WHERE id = ?";
//            sqLiteDatabase.execSQL(sql,new Integer[]{productModel.getId()});
//            loadEmployees();
//        });
//        builder.setNegativeButton("No",(d, i) ->{
//            Toast.makeText(context,"Product Not Deleted",Toast.LENGTH_LONG).show();
//        } );
//        builder.create().show();
//    }
    public taskAdapter(@NonNull Context context, int resource, @NonNull List<Task> taskModelList,
                          SQLiteDatabase sqLiteDatabase) {
        super(context, resource);
        this.taskModelList = taskModelList;
        this.context = context;
        layoutRes = resource;
        this.sqLiteDatabase = sqLiteDatabase;
    }
    @Override
    public int getCount(){
        return taskModelList.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        int count = 0;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if(v == null) v = inflater.inflate(layoutRes,null);

        TextView nameTv = v.findViewById(R.id.name2);
        TextView categoryTv = v.findViewById(R.id.category2);
        TextView dateCreatedTv = v.findViewById(R.id.dateC2);
        TextView dateCompletedTv = v.findViewById(R.id.dateComp);
        Switch completeTask = v.findViewById(R.id.switch1);

        count++;

        Task taskModel = taskModelList.get(position);
        nameTv.setText(taskModel.getName());
        categoryTv.setText(taskModel.getCategory());
        if(count < 2) {
            if (taskModel.getStatus() == 1) {
                completeTask.setChecked(true);
            } else {
                completeTask.setChecked(false);
            }
        }
        dateCreatedTv.setText(taskModel.getDateCreated());
        dateCompletedTv.setText(taskModel.getDateCompleted());
        completeTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    String mainCheck = "SELECT * from subtasks WHERE taskid="+taskModel.getId();
                    String subCheck = "SELECT * from subtasks WHERE taskid = "+taskModel.getId()+" AND status = 1";
                    Cursor mainCursor = sqLiteDatabase.rawQuery(mainCheck, null);
                    Cursor subCursor = sqLiteDatabase.rawQuery(subCheck, null);
                    if (mainCursor.getCount() == subCursor.getCount()) {
                        Toast.makeText(context,"All number: "+mainCursor.getCount(),Toast.LENGTH_LONG).show();
                        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new String[]{
                                "1",
                                String.valueOf(taskModel.getId())
                        });

                    }
                else{
                        Toast.makeText(context,"All subtasks must be completed before proceeding "+mainCursor.getCount(),Toast.LENGTH_LONG).show();
                        compoundButton.setChecked(false);
                    }
                }else {

                        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
                        sqLiteDatabase.execSQL(sql, new String[]{
                                "0",
                                String.valueOf(taskModel.getId())
                        });
                    Toast.makeText(context,"taskModel is :  "+taskModel.getId(),Toast.LENGTH_LONG).show();
                        loadTasks();
                    }
                }


        });
        v.findViewById(R.id.addSub).setOnClickListener(view -> {
            deleteTasks(taskModel);
        });
        v.findViewById(R.id.viewSubs).setOnClickListener(view -> {
            viewTask(taskModel);
        });

        v.findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                editTasks(taskModel);
            }

//            @RequiresApi(api = Build.VERSION_CODES.O)
            public void editTasks(Task taskModel) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.taskedit, null);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

                CalendarView tDate = view.findViewById(R.id.cDate);
                tDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                        String curDate = String.valueOf(i2);
                        String month = String.valueOf(i1);
                        String year = String.valueOf(i);
                        CompletionDate = year + "-" + month + "-" + curDate;

                    }
                });
                Spinner tCategory = view.findViewById(R.id.category);
                String cat = "SELECT * FROM categories";
                Cursor cursor = sqLiteDatabase.rawQuery(cat, null);
                if (cursor.moveToFirst()) {

                    do {
                        // create an employee instance

                        categoryList.add(new String(
                                cursor.getString(1)
                        ));

                    } while (cursor.moveToNext());
                    cursor.close();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categoryList);
                tCategory.setAdapter(adapter);

//                String[] catArray = context.getResources().getStringArray(categoryList);
                int position = categoryList.indexOf(taskModel.getCategory());
                TextView tName = view.findViewById(R.id.name);



                tName.setText(taskModel.getName());
                tCategory.setSelection(position);


                view.findViewById(R.id.addSub).setOnClickListener(v -> {

                    TextView nName = view.findViewById(R.id.name);
//                    Spinner nCategory = view.findViewById(R.id.category);

                    String name = nName.getText().toString().trim();
                    String category = tCategory.getSelectedItem().toString();


                    if (name.isEmpty()) {
                        nName.setError("name field is empty");
                        nName.requestFocus();
                        return;
                    }


                    String sql = "UPDATE tasks SET name = ?, category = ?, completionDate = ? WHERE id = ?";
                    sqLiteDatabase.execSQL(sql, new String[]{
                            name,
                            category,
                            CompletionDate,
                            String.valueOf(taskModel.getId())
                    });
                    loadTasks();
                    dialog.dismiss();

                });


            }
        });

        return v;

    }
    public void deleteTasks(Task taskModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this task");
        builder.setPositiveButton("Yes",(dialogInterface, i) -> {
            String sql = "DELETE FROM tasks WHERE id = ?";
            sqLiteDatabase.execSQL(sql,new Integer[]{taskModel.getId()});
            loadTasks();
        });
        builder.setNegativeButton("No",(d, i) ->{
            Toast.makeText(context,"Task Not Deleted",Toast.LENGTH_LONG).show();
        } );
        builder.create().show();
    }
    public void viewTask(Task taskModel){
        Intent intent = new Intent(context, viewSubs.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", taskModel.getId());
        Toast.makeText(context,"Task id " +taskModel.getId(),Toast.LENGTH_LONG).show();
        bundle.putString("name", taskModel.getName());
        intent.putExtras(bundle);
        context.startActivity(intent);


    }
    private void loadTasks() {

        String sql = "SELECT * FROM tasks";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);



        if (cursor.moveToFirst()) {
            Log.d("here","we are here");
            taskModelList.clear();
            do {
                // create an employee instance
                Log.d("first name","this is the "+cursor.getDouble(3));
                taskModelList.add(new Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
                Log.d("sayo check","this is"+ taskModelList.get(0).getName());
            } while (cursor.moveToNext());
            cursor.close();
        }
        notifyDataSetChanged();

    }
}
