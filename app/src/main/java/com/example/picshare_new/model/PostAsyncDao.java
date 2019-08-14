package com.example.picshare_new.model;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface PostDao {
    @Query("select * from Post")
    List<Post> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<Post> data);
    @Delete
    void delete(Post post);
}

public class PostAsyncDao{

    public static void getAllPosts(Model.GetAllPostsListener listener) {
        new AsyncTask<String, String, List <Post>>() {
            @Override
            protected List<Post> doInBackground(String... strings) {
                List<Post> list = ModelSql.db.PostDao().getAll();
                return list;
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                listener.onCompleate(data);

            }
        }.execute();
    }

    public static void addPost(Post post, Model.basicOnCompleateListener listener) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                ModelSql.db.PostDao().insertAll(post);
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

    public static void addPostsAndGetPostsList(List<Post> data, Model.GetAllPostsListener listener) {
        new AsyncTask<List<Post>, String, List <Post>>() {
            @Override
            protected List<Post> doInBackground(List<Post>... posts) {
                    ModelSql.db.PostDao().insertPosts(posts[0]);

                List<Post> list = ModelSql.db.PostDao().getAll();
                return list;
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                listener.onCompleate(data);

            }
        }.execute(data);
    }
}

