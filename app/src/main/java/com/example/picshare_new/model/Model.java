package com.example.picshare_new.model;


import java.util.Date;
import java.util.LinkedList;
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
//         addPost(post);
//        }
//
//        for (int i =0; i < 3; i++){
//            Comment comment = new Comment(Integer.toString(i),"the" + Integer.toString(i + 1) + "comment", "user_id", "2","userimage","username");
//            addComment(comment);
//        }

    }

    public interface GetAllPostsListener{
        void onCompleate(List<Post> data);
    }

    public interface GetAllCommentsOfPostListener{
        void onCompleate(List<Comment> data);
    }

    public void getAllPosts(GetAllPostsListener listener){
        modelFirebase.getAllPosts(listener);
    }
    public void addPost(Post post){
        modelFirebase.addPost(post);
    }





    //comments!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void getAllcommentsOfPosts(String PostKey,GetAllCommentsOfPostListener listener){
        modelFirebase.getAllcommentsOfPosts(PostKey, listener);
    }
    public void addComment(Comment comment){
        modelFirebase.addComment(comment);
    }

}
//        if (cursor.moveToFirst()){
//                do {
//
//
//                }while (cursor.moveToNext());
//                }
