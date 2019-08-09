package com.example.picshare_new.model;

public class Post {
    public  String id;
    String image;
    // list of comments
    public String getId(){
        return id;
    }
    public String getImage(){
        return image;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setImage(String image){
        this.id = image;
    }
}
