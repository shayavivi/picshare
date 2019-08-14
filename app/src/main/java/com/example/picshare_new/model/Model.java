package com.example.picshare_new.model;


import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public class Model {
    final public static  Model instance = new Model();
    ModelSql modelSql;
    ModelFirebase modelFirebase;
    private  Model(){
        modelSql = new ModelSql();
        modelFirebase = new ModelFirebase();
//        for (int i =0; i < 10; i++){
//         Post post = new Post(Integer.toString(i + 1), "a title" + Integer.toString(i), "picture", "123","user photo", new Date());
//         addPost(post,null);
//        }
//
//        for (int i =0; i < 3; i++){
//            Comment comment = new Comment(Integer.toString(i),"the" + Integer.toString(i + 1) + "comment", "user_id", "2","userimage");
//            addComment(comment);
//        }

    }

    interface SaveImageListener{
        public void fail();
        public void complete(String uri);
    }
    interface RegisterListener{
        public void onCcomplete(FirebaseUser user, String password);
    }
    interface addUserListener{
        public void onCcomplete(User user);
    }



    public void uploadImage(Bitmap imageBmp, String name)
    {
        modelFirebase.uploadImage(imageBmp, name, new SaveImageListener() {
            @Override
            public void fail() {

            }

            @Override
            public void complete(String uri) {

            }
        });
    }
    //users!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public String getCurrentUserId(){
       return modelFirebase.getCurrentUserid();
    }

    public void register(String email, String password, String image, basicListener listener){
        modelFirebase.register(email, password, new RegisterListener() {
            @Override
            public void onCcomplete(FirebaseUser user, String password) {
                modelFirebase.addUser(user, image, password, new addUserListener() {
                    @Override
                    public void onCcomplete(User user) {
                        UserAsyncDao.addUser(user, null);
                        listener.onSuccess(user.getUserId());
                    }
                });
            }

        });

    }

    public void login(String sEmail, String sPassword, basicListener listener) {
        modelFirebase.login(sEmail, sPassword, listener);
    }
    public void logout() {
        modelFirebase.logout();
    }

    public interface basicListener {
        public void onSuccess(String id);
        public void onFailure(String e);
    }

    //posts!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public interface GetAllPostsListener{
        void onCompleate(List<Post> data);
    }

    public interface GetAllCommentsOfPostListener{
        void onCompleate(List<Comment> data);
    }

    public interface basicOnCompleateListener{
        void onCompleate(boolean done);
    }

    public interface StoreToLocalListener{
        void onCompleate(String key);
    }

    public void getAllPosts(GetAllPostsListener listener){
        PostAsyncDao.getAllPosts(new GetAllPostsListener() {
             @Override
             public void onCompleate(List<Post> data) {
                 listener.onCompleate(data);
                 modelFirebase.getAllPosts(new GetAllPostsListener() {
                     @Override
                     public void onCompleate(List<Post> data) {
                         PostAsyncDao.addPostsAndGetPostsList(data, new GetAllPostsListener() {
                             @Override
                             public void onCompleate(List<Post> data) {
                                 listener.onCompleate(data);
                             }
                         });

                     }
                 });

             }
         });




    }
    public void addPost(Post post, basicOnCompleateListener listener)
    {

        modelFirebase.addPost(post);
        PostAsyncDao.addPost(post, listener);
    }







    //comments!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void getAllcommentsOfPost(String PostKey ,GetAllCommentsOfPostListener listener){


        CommentAsyncDao.getAllcommentsOfPost(PostKey, new GetAllCommentsOfPostListener() {
            @Override
            public void onCompleate(List<Comment> data) {
                listener.onCompleate(data);
                modelFirebase.getAllcommentsOfPosts(PostKey, new GetAllCommentsOfPostListener() {
                    @Override
                    public void onCompleate(List<Comment> data) {
                        CommentAsyncDao.addCommentsAndGetCommentsaList(PostKey, data, new GetAllCommentsOfPostListener() {

                            @Override
                            public void onCompleate(List<Comment> data) {
                                listener.onCompleate(data);
                            }
                        });


                    }
                });

            }
        });
    }

    public void addComment(Comment comment, basicOnCompleateListener listener)
    {
        modelFirebase.addComment(comment, new StoreToLocalListener() {
            @Override
            public void onCompleate(String key) {
                comment.setCommentId(key);
                CommentAsyncDao.addComment(comment, listener);
            }
        });

    }

}
//        if (cursor.moveToFirst()){
//                do {
//
//
//                }while (cursor.moveToNext());
//                }
