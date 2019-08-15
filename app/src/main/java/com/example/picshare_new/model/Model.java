package com.example.picshare_new.model;


import android.graphics.Bitmap;

import com.example.picshare_new.MyApp;
import com.google.firebase.auth.FirebaseUser;

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
    interface addUserListener{
        public void onComplete(User user);
    }
    public interface addCommentListener{
        public void onComplete(Comment comment);
    }
    public interface addPostListener{
        public void onComplete(Post post);
    }




    //users!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public String getCurrentUserId(){
       return modelFirebase.getCurrentUserid();
    }

    public void register(String email, String password, basicListener listener){
        modelFirebase.register(email, password, listener);
    }
    public void addUser(String id, String email, String password, Bitmap imageBitmap, Model.basicOnCompleateListener listener) {
        Model.instance.modelFirebase.uploadImage(imageBitmap, new SaveImageListener() {
            @Override
            public void fail() {

            }

            @Override
            public void complete(String uri) {
                modelFirebase.addUser(id, email, password, uri, new addUserListener() {
                    @Override
                    public void onComplete(User user) {
                        UserAsyncDao.addUser(user, listener);

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

    public void getAllPostsByUserId(GetAllPostsListener listener) {
        PostAsyncDao.getAllPostsByUserId(MyApp.getCurrentUserId(), new GetAllPostsListener() {
            @Override
            public void onCompleate(List<Post> data) {
                listener.onCompleate(data);
                modelFirebase.getAllPostsByUserId(new GetAllPostsListener() {
                    @Override
                    public void onCompleate(List<Post> data) {
                        PostAsyncDao.addPostsAndGetPostsListByUserId(data, new GetAllPostsListener() {
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
    public void addPost(Post post, Bitmap imageBitmap, addPostListener listener)
    {
        modelFirebase.uploadImage(imageBitmap, new SaveImageListener() {
            @Override
            public void fail() {

            }

            @Override
            public void complete(String uri) {
                UserAsyncDao.getUserById(new addUserListener() {
                    @Override
                    public void onComplete(User user) {
                        post.setUserPhoto(user.getProfileImage());
                        post.setPicture(uri);
                        post.setPostKey(modelFirebase.addPost(post));
                        PostAsyncDao.addPost(post, null);
                        listener.onComplete(post);

                    }
                });
            }
        });
    }
    public void deletePost(String postKey, basicOnCompleateListener listener) {
        modelFirebase.deleteAllCommentsByPostKey(postKey, new basicOnCompleateListener() {
            @Override
            public void onCompleate(boolean done) {
                if (done == true){
                    modelFirebase.deletePost(postKey, new basicOnCompleateListener() {
                        @Override
                        public void onCompleate(boolean done) {
                            CommentAsyncDao.deleteAllCommentsByPostKey(postKey, new basicOnCompleateListener() {
                                @Override
                                public void onCompleate(boolean done) {
                                    if (done == true){
                                        PostAsyncDao.deletePostByPostKey(postKey, new basicOnCompleateListener() {
                                            @Override
                                            public void onCompleate(boolean done) {
                                                listener.onCompleate(true);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });

                }
            }
        });
    }

    public void updatePost(Post post, basicOnCompleateListener listener) {
        UserAsyncDao.getUserById(new addUserListener() {
            @Override
            public void onComplete(User user) {
                post.setUserPhoto(user.getProfileImage());
                modelFirebase.updatePost(post, new basicOnCompleateListener() {
                    @Override
                    public void onCompleate(boolean done) {
                        if (done == true){
                            PostAsyncDao.addPost(post, new basicOnCompleateListener() {
                                @Override
                                public void onCompleate(boolean done) {
                                    if (done == true){
                                        listener.onCompleate(true);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
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

    public void addComment(Comment comment, addCommentListener listener)
    {
        UserAsyncDao.getUserById(new addUserListener() {
            @Override
            public void onComplete(User user) {
                comment.setUimg(user.getProfileImage());
                modelFirebase.addComment(comment, new StoreToLocalListener() {
                    @Override
                    public void onCompleate(String key) {
                        comment.setCommentId(key);
                        CommentAsyncDao.addComment(comment);// maby add listener
                        listener.onComplete(comment);
                    }
                });
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
