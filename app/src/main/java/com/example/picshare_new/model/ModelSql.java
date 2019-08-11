package com.example.picshare_new.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class ModelSql {
    MyHelper mDBhelper;
    public ModelSql(){
//        mDBhelper = new MyHelper();
    }


    public List<Post> getAllPosts(){
        SQLiteDatabase db = mDBhelper.getReadableDatabase();
        LinkedList<Post> data = new LinkedList<Post>();
        Cursor cursor = db .query("posts",null,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex("id");
            int imageIndex = cursor.getColumnIndex("image");

            do {
                String id = cursor.getString(idIndex);
                String image = cursor.getString(imageIndex);
                Post post = new Post(id, image);
                data.add(post);

            }while (cursor.moveToNext());
        }

        return data;
    }
    public void addPost(Post post){
        ContentValues values = new ContentValues();
        values.put("id", post.getId());
        values.put("image", post.getImage());
        SQLiteDatabase db = mDBhelper.getWritableDatabase();
        db.insert("Posts","id",values);

    }

    class MyHelper extends SQLiteOpenHelper {

        public MyHelper(Context context) {
//            update version evry time we update the scheme
            super(context, "database.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table Posts( id text primary key, image text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("drop table if exists posts");
            sqLiteDatabase.execSQL("create table Posts( id text primary key, image text)");
        }
    }
}
