package com.example.picshare_new.model;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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


}
