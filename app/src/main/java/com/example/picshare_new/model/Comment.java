package com.example.picshare_new.model;

import com.google.firebase.database.ServerValue;

public class Comment {

    private String content,uid,userImage,username;
    private Object timestamp;


    public Comment() {
    }

    public Comment(String content, String uid, String uimg, String username) {
        this.content = content;
        this.uid = uid;
        this.userImage = uimg;
        this.username = username;
        this.timestamp = ServerValue.TIMESTAMP;

    }

    public Comment(String content, String uid, String uimg, String username, Object timestamp) {
        this.content = content;
        this.uid = uid;
        this.userImage = uimg;
        this.username = username;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUimg() {
        return userImage;
    }

    public void setUimg(String uimg) {
        this.userImage = uimg;
    }

    public String getUname() {
        return username;
    }

    public void setUname(String username) {
        this.username = username;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}