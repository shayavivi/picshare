package com.example.picshare_new.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Key;
import java.util.LinkedList;

import javax.annotation.Nullable;

public class ModelFirebase {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    public ModelFirebase(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
        mAuth = FirebaseAuth.getInstance();
    }
//    interface GetAllPostsListener{
//        void onComplete(List<Post> data);
//    }

    //users!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public String getCurrentUserid(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
            return currentUser.getUid();
        else
            return null;
    }
    public void register(String email, String password, String image, Model.basicListener listener) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if(authResult != null) {
                        addUser(authResult.getUser(), password);
                        listener.onSuccess(authResult.getUser().getUid());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.onFailure(e.getMessage().replace("[","").replace("]",""));
                }
            });

    }
    public void login(String email, String password, Model.basicListener listener){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(authResult != null) {
                            listener.onSuccess(authResult.getUser().getUid());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onFailure(e.getMessage().replace("[","").replace("]",""));
            }
        });

    }
    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public void addUser(FirebaseUser firebaseUser, String password) {
        User user = new User(firebaseUser.getUid(), "an image", firebaseUser.getEmail(), password);
        db.collection("Users").document().set(user);
    }









    //Posts!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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

    public String addPost(Post post) {
        String key = db.collection("Comments").document().getId();
        post.setPostKey(key);
        db.collection("Posts").document(post.getPostKey()).set(post);
        return key;
    }
    //comments!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public String addComment(Comment comment, Model.StoreToLocalListener listener){
        String key = db.collection("Comments").document().getId();
        comment.setCommentId(key);
        db.collection("Comments").document(comment.getCommentId()).set(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onCompleate(key);
            }
        });
        return key;
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
