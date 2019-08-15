package com.example.picshare_new.model;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.picshare_new.MyApp;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    public void uploadImage(Bitmap imageBmp , final Model.SaveImageListener listener)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Date d = new Date();
        StorageReference imagesRef = storage.getReference().child("images").child("image_" + d.getTime() + "jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.fail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.complete(uri.toString());

                    }
                });

            }
        });
    }

    //users!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public String getCurrentUserid(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
            return currentUser.getUid();
        else
            return null;
    }
    public void register(String email, String password, Model.basicListener listener) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    listener.onSuccess(authResult.getUser().getUid());
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

    public void addUser(String id, String email, String password, String uri, Model.addUserListener listener) {
        User user = new User(id, uri, email, password);


        db.collection("Users").document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onComplete(user);
            }
        });
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
    public void getAllPostsByUserId(Model.GetAllPostsListener listener) {
        db.collection("Posts").whereEqualTo("userId", MyApp.getCurrentUserId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
    public void deletePost(String postKey, Model.basicOnCompleateListener listener) {
        db.collection("Posts").document(postKey).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onCompleate(true);

            }
        });
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

    public void deleteAllCommentsByPostKey(String postKey, Model.basicOnCompleateListener listener) {
        db.collection("Comments").document(postKey).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onCompleate(true);
            }
        });
    }
}
