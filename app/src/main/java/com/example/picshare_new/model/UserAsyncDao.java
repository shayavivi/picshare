package com.example.picshare_new.model;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.picshare_new.MyApp;

import java.util.List;

@Dao
interface UserDao {
    @Query("select * from User where userId = :id")
    User getById(String id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

}

public class UserAsyncDao{

    public static void addUser(User user, Model.basicOnCompleateListener listener) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                ModelSql.db.UserDao().insertAll(user);
                return "";
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);
                if (listener != null)
                    listener.onCompleate(true);

            }
        }.execute();
    }


    public static void getUserById(Model.addUserListener listener) {
        new AsyncTask<String, String, User>() {
            @Override
            protected User doInBackground(String... strings) {
                User user = ModelSql.db.UserDao().getById(MyApp.getCurrentUserId());
                return user;
            }

            @Override
            protected void onPostExecute(User data) {
                super.onPostExecute(data);
                if (listener != null && data != null)
                    listener.onComplete(data);

            }
        }.execute();
    }
}
