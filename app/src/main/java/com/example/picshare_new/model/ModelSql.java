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
            int idIndex = cursor.getColumnIndex("postKey");
            int imageIndex = cursor.getColumnIndex("title");
            int pictureIndex = cursor.getColumnIndex("picture");
            int userIdeIndex = cursor.getColumnIndex("userId");
            int userPhotoIndex = cursor.getColumnIndex("userPhoto");
            int timeStampIndex = cursor.getColumnIndex("timeStamp");

            do {
                String postKey = cursor.getString(idIndex);
                String title = cursor.getString(imageIndex);
                String picture = cursor.getString(pictureIndex);
                String userId = cursor.getString(userIdeIndex);
                String userPhoto = cursor.getString(userPhotoIndex);
                String timeStamp = cursor.getString(timeStampIndex);

                Post post = new Post(postKey, title, picture, userId, userPhoto, timeStamp);
                data.add(post);

            }while (cursor.moveToNext());
        }

        return data;
    }
    public void addPost(Post post){
        ContentValues values = new ContentValues();
        values.put("postKey", post.getPostKey());
        values.put("title", post.getTitle());
        values.put("picture", post.getPicture());
        values.put("userId", post.getUserId());
        values.put("userPhoto", post.getUserPhoto());
        values.put("timeStamp", post.getTimeStamp().toString());

        SQLiteDatabase db = mDBhelper.getWritableDatabase();
        db.insert("Posts","postKey",values);

    }

    class MyHelper extends SQLiteOpenHelper {

        public MyHelper(Context context) {
//            update version evry time we update the scheme
            super(context, "database.db", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table Users( userId text primary key, image text, username text, password text )");
            sqLiteDatabase.execSQL("create table Posts( postKey text primary key, title text, picture text, userId text, userPhoto text, timeStamp datetime)");
            sqLiteDatabase.execSQL("create table Comments( commentId text primary key, content, text, uid text, postKey text, userImage text, username text, timestamp datetime)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("create table Users( userId text primary key, image text, username text, password text )");
            sqLiteDatabase.execSQL("create table Posts( postKey text primary key, title text, picture text, userId text, userPhoto text, timeStamp datetime)");
            sqLiteDatabase.execSQL("create table Comments( commentId text primary key, content, text, uid text, postKey text, userImage text, username text, timestamp datetime)");
        }
    }
}