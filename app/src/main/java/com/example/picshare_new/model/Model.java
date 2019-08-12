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
        for (int i =0; i < 10; i++){
         Post post = new Post(Integer.toString(i + 1), "a title", "picture", "123","user photo", new Date());
         addPost(post);
        }

    }


    public List<Post> getAllPosts(){

        return modelFirebase.getAllPosts();
    }
    public void addPost(Post post){
        modelFirebase.addPost(post);
    }

}
//        if (cursor.moveToFirst()){
//                do {
//
//
//                }while (cursor.moveToNext());
//                }
