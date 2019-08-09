package com.example.picshare_new.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static  Model instance = new Model();
    private  Model(){

    }
    private MyHelper mDBhelper;
    public void init(Context context){
        mDBhelper = new MyHelper(context);
    }

    public List<Post> getAllPosts(){
        SQLiteDatabase db = mDBhelper.getReadableDatabase();
        Cursor cursor = db .query("posts",null,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
         do {


         }while (cursor.moveToNext());
        }

        return new LinkedList<Post>();
    }
    public void addPost(Post post){

    }

    class MyHelper extends SQLiteOpenHelper{

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
