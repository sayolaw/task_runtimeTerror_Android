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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class subAdapter extends ArrayAdapter {
    Context context;
    int layoutRes;
    String CompletionDate;
    List<Sub> subModelList;
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
    public subAdapter(@NonNull Context context, int resource, @NonNull List<Sub> subModelList,
                           SQLiteDatabase sqLiteDatabase) {
        super(context, resource);
        this.subModelList = subModelList;
        this.context = context;
        layoutRes = resource;
        this.sqLiteDatabase = sqLiteDatabase;
    }
    @Override
    public int getCount(){
        return subModelList.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = convertView;
        if(v == null) v = inflater.inflate(layoutRes,null);
        TextView nameTv = v.findViewById(R.id.name2);





        Sub subModel = subModelList.get(position);
        nameTv.setText(subModel.getName());


        v.findViewById(R.id.addSub).setOnClickListener(view -> {
            deleteSub(subModel);
        });






        return v;

    }
    public void deleteSub(Sub subModel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this Sub task");
        builder.setPositiveButton("Yes",(dialogInterface, i) -> {
            String sql = "DELETE FROM subtasks WHERE id = ?";
            sqLiteDatabase.execSQL(sql,new Integer[]{subModel.getId()});
            loadSub();
        });
        builder.setNegativeButton("No",(d, i) ->{
            Toast.makeText(context,"Sub Not Deleted",Toast.LENGTH_LONG).show();
        } );
        builder.create().show();
    }
    private void loadSub() {

        String sql = "SELECT * FROM subtasks";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);



        if (cursor.moveToFirst()) {
            Log.d("here","we are here");
            subModelList.clear();
            do {
                // create an employee instance

                subModelList.add(new Sub(
                        cursor.getInt(0),
                        cursor.getInt(0),
                        cursor.getString(1)

                ));
                Log.d("sayo check","this is"+ subModelList.get(0).getName());
            } while (cursor.moveToNext());
            cursor.close();
        }
        notifyDataSetChanged();

    }
}
