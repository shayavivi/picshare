package com.example.picshare_new.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

public class ModelFirebase {
    FirebaseFirestore db;
    public ModelFirebase(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }
//    interface GetAllPostsListener{
//        void onComplete(List<Post> data);
//    }

    public void getAllPosts(Model.GetAllPostsListener listener ) {
        db.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                LinkedList<Post> data = new LinkedList<>();
                if (e != null) {
                    listener.onCompleate(data);
                    return;
                }
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Post post = doc.toObject(Post.class);
                    data.add(post);
                }
                listener.onCompleate(data);
            }
        });

    }

    public void addPost(Post post) {
        db.collection("Posts").document().set(post);
    }
    //comments!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void addComment(Comment comment){
        db.collection("Comments").document().set(comment);
    }

    public void getAllcommentsOfPosts(String postKey, Model.GetAllCommentsOfPostListener listener ) {
        db.collection("Comments").whereEqualTo("postKey", postKey).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                LinkedList<Comment> data = new LinkedList<>();
                if (e != null) {
                    listener.onCompleate(data);
                    return;
                }
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Comment comment = doc.toObject(Comment.class);
                    data.add(comment);
                }
                listener.onCompleate(data);
            }
        });

    }

}
