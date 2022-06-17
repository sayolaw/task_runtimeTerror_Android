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
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class categoryAdapter extends ArrayAdapter {
    Context context;
    int layoutRes;
    String CompletionDate;
    List<Category> categoryModelList;
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
    public categoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> categoryModelList,
                       SQLiteDatabase sqLiteDatabase) {
        super(context, resource);
        this.categoryModelList = categoryModelList;
        this.context = context;
        layoutRes = resource;
        this.sqLiteDatabase = sqLiteDatabase;
    }
    @Override
    public int getCount(){
        return categoryModelList.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if(v == null) v = inflater.inflate(layoutRes,null);
        TextView nameTv = v.findViewById(R.id.name2);





        Category categoryModel = categoryModelList.get(position);
        nameTv.setText(categoryModel.getName());


        v.findViewById(R.id.editTask).setOnClickListener(view -> {
            deleteCategories(categoryModel);
        });

        v.findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                editCategories(categoryModel);


            }

            //            @RequiresApi(api = Build.VERSION_CODES.O)
            public void editCategories(Category categoryModel) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.categoryedit, null);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();



                TextView tName = view.findViewById(R.id.name);


                tName.setText(categoryModel.getName());



                view.findViewById(R.id.editTask).setOnClickListener(v -> {

                    TextView nName = view.findViewById(R.id.name);

                    String name = nName.getText().toString().trim();



                    if (name.isEmpty()) {
                        nName.setError("name field is empty");
                        nName.requestFocus();
                        return;
                    }


                    String sql = "UPDATE categories SET name = ? WHERE id = ?";
                    sqLiteDatabase.execSQL(sql, new String[]{
                            name,
                            String.valueOf(categoryModel.getId())
                    });
                    loadCategories();
                    dialog.dismiss();

                });


            }
        });

        return v;

    }
    public void deleteCategories(Category categoryModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this category");
        builder.setPositiveButton("Yes",(dialogInterface, i) -> {
            String sql = "DELETE FROM categories WHERE id = ?";
            sqLiteDatabase.execSQL(sql,new Integer[]{categoryModel.getId()});
            loadCategories();
        });
        builder.setNegativeButton("No",(d, i) ->{
            Toast.makeText(context,"Category Not Deleted",Toast.LENGTH_LONG).show();
        } );
        builder.create().show();
    }
    private void loadCategories() {

        String sql = "SELECT * FROM categories";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);



        if (cursor.moveToFirst()) {
            Log.d("here","we are here");
            categoryModelList.clear();
            do {
                // create an employee instance

                categoryModelList.add(new Category(
                        cursor.getInt(0),
                        cursor.getString(1)

                ));
                Log.d("sayo check","this is"+ categoryModelList.get(0).getName());
            } while (cursor.moveToNext());
            cursor.close();
        }
        notifyDataSetChanged();

    }
}
