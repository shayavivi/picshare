package com.example.picshare_new.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.picshare_new.MainActivity;
import com.example.picshare_new.MyApp;

import java.util.LinkedList;
import java.util.List;

@Database(entities = {Post.class, Comment.class, User.class}, version = 14, exportSchema = false)
@TypeConverters({Converters.class})
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao PostDao();
    public abstract CommentDao CommentDao();
    public abstract UserDao UserDao();
}

public class ModelSql {

    static public AppLocalDbRepository db =
            Room.databaseBuilder(MainActivity.context,
                    AppLocalDbRepository.class,
                    "database.db")
                    .fallbackToDestructiveMigration()
                    .build();


}