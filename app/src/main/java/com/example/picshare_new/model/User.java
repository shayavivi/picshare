package com.example.picshare_new.model;

public class User {
    String userId;
    String profileImage;
    String username;
    String password;
    public User(){ }
    public User(String id, String image, String uname, String pass){
        this.userId = id;
        this.profileImage = image;
        this.username = uname;
        this.password = pass;

    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
