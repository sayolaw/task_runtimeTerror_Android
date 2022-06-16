package com.example.task_runtimeterror_android;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class taskAdapter extends ArrayAdapter {
    Context context;
    int layoutRes;
    List<Task> taskModelList;
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if(v == null) v = inflater.inflate(layoutRes,null);
        TextView nameTv = v.findViewById(R.id.name2);
        TextView categoryTv = v.findViewById(R.id.category2);
        TextView dateCreatedTv = v.findViewById(R.id.dateC2);
        TextView dateCompletedTv = v.findViewById(R.id.dateComp);


        Task taskModel = taskModelList.get(position);
        nameTv.setText(taskModel.getName());
        categoryTv.setText(taskModel.getCategory());
        dateCreatedTv.setText(taskModel.getDateCreated());
        dateCompletedTv.setText(taskModel.getDateCompleted());
//        v.findViewById(R.id.button2).setOnClickListener(view -> {
//            deleteProduct(productModel);
//        });
//        v.findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editProduct(productModel);
//
//            }
//
//            public void editProduct(Product productModel) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                LayoutInflater inflater = LayoutInflater.from(context);
//                View view = inflater.inflate(R.layout.dialogeedit, null);
//                builder.setView(view);
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//                EditText tName = view.findViewById(R.id.name);
//                EditText tDescription = view.findViewById(R.id.description);
//                EditText tPrice = view.findViewById(R.id.price);
//                Log.d("product", "this is product: " + productModel.getId());
//                tName.setText(productModel.getName());
//                tDescription.setText(productModel.getDescription());
//                tPrice.setText(String.valueOf(productModel.getPrice()));
//                view.findViewById(R.id.editButton).setOnClickListener(v -> {
//                    TextView nName = view.findViewById(R.id.name);
//                    TextView nDescription = view.findViewById(R.id.description);
//                    TextView nPrice = view.findViewById(R.id.price);
//                    String name = nName.getText().toString().trim();
//                    String description = nDescription.getText().toString().trim();
//                    String price = nPrice.getText().toString().trim();
//
//                    if (name.isEmpty()) {
//                        nName.setError("name field is empty");
//                        nName.requestFocus();
//                        return;
//                    }
//                    if (description.isEmpty()) {
//                        nDescription.setError("salary field is empty");
//                        nDescription.requestFocus();
//                        return;
//                    }
//                    if (price.isEmpty()) {
//                        nPrice.setError("salary field is empty");
//                        nPrice.requestFocus();
//                        return;
//                    }
//                    String sql = "UPDATE products SET name = ?, description = ?, price = ? WHERE id = ?";
//                    sqLiteDatabase.execSQL(sql, new String[]{
//                            name,
//                            description,
//                            price,
//                            String.valueOf(productModel.getId())
//                    });
//                    loadEmployees();
//                    dialog.dismiss();
//
//                });
//
//
//            }
//        });

        return v;

    }

    private void loadEmployees() {

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
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
                Log.d("sayo check","this is"+ taskModelList.get(0).getName());
            } while (cursor.moveToNext());
            cursor.close();
        }

    }
}
