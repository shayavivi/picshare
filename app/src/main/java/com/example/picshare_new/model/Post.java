package com.example.picshare_new.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    private String postKey;
    private String title;
    private String picture;
    private String userId;
    private String userPhoto;


    @Ignore
    public Post(String postKey, String title, String picture, String userId, String userPhoto, Date timeStamp) {
        this.postKey = postKey;
        this.title = title;
        this.picture = picture;
        this.userId = userId;
        this.userPhoto = userPhoto;
    }

    @Ignore
    public Post(String postKey, String title, String picture, String userId, String userPhoto) {
        this.postKey = postKey;
        this.title = title;
        this.picture = picture;
        this.userId = userId;
        this.userPhoto = userPhoto;
    }

    // make sure to have an empty constructor inside ur model class
    public Post() {
    }


    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getTitle() {
        return title;
    }

    public String getPicture() {
        return picture;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

}
