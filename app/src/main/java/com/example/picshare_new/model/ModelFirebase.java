package com.example.picshare_new.model;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {
    FirebaseFirestore db;
    public ModelFirebase(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }
    interface GetAllPostsListener{}

    public List<Post> getAllPosts() {
        List<Post> posts = new LinkedList<Post>();

        return posts;
    }

    public void addPost(Post post) {
        db.collection("Posts").document().set(post);
    }
}
