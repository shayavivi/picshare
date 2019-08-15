package com.example.picshare_new.model;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface CommentDao {
    @Query("select * from Comment")
    List<Comment> getAll();
    @Query("select * from Comment where postKey = :postKey")
    List<Comment> getAllByPostId(String postKey);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Comment... comments);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertComments(List<Comment> data);
    @Delete
    void delete(Comment comment);
    @Query("DELETE FROM Comment where postKey = :postKey")
    void deleteComments(String postKey);
}

public class CommentAsyncDao{

    public static void getAllcommentsOfPost(String PostKey , Model.GetAllCommentsOfPostListener listener) {

        new AsyncTask<List<Comment>, String, List <Comment>>() {
            @Override
            protected List<Comment> doInBackground(List<Comment>... comments) {
                List<Comment> list = ModelSql.db.CommentDao().getAllByPostId(PostKey);
                return list;
            }

            @Override
            protected void onPostExecute(List<Comment> data) {
                super.onPostExecute(data);
                listener.onCompleate(data);

            }
        }.execute();

    }

    public static void addCommentsAndGetCommentsaList(String PostKey ,List<Comment> data, Model.GetAllCommentsOfPostListener listener) {

        new AsyncTask<List<Comment>, String, List <Comment>>() {
            @Override
            protected List<Comment> doInBackground(List<Comment>... comments) {
                ModelSql.db.CommentDao().insertComments(comments[0]);

                List<Comment> list = ModelSql.db.CommentDao().getAllByPostId(PostKey);
                return list;
            }

            @Override
            protected void onPostExecute(List<Comment> data) {
                super.onPostExecute(data);
                listener.onCompleate(data);

            }
        }.execute(data);

    }

    public static void addComment(Comment comment) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                ModelSql.db.CommentDao().insertAll(comment);
                return "";
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);

            }
        }.execute();
    }

    public static void deleteAllCommentsByPostKey(String postKey, Model.basicOnCompleateListener listener) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                ModelSql.db.CommentDao().deleteComments(postKey);
                return "";
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);
                listener.onCompleate(true);
            }
        }.execute();

    }
}
