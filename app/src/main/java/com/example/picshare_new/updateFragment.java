package com.example.picshare_new;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.picshare_new.model.Model;
import com.example.picshare_new.model.Post;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class updateFragment extends Fragment {
    String postKey;
    String uri;
    EditText title;
    ImageView pic;
    Button saveBtn;

    public updateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update, container, false);
        title = v.findViewById(R.id.updateTitle);
        pic = v.findViewById(R.id.updatePic);
        saveBtn = v.findViewById(R.id.updateBtnSave);


        postKey = updateFragmentArgs.fromBundle(getArguments()).getPostKey();
        uri = updateFragmentArgs.fromBundle(getArguments()).getPicUri();
        Glide.with(this).load(uri).into(pic);
        String sTitle = updateFragmentArgs.fromBundle(getArguments()).getTitle();
        title.setText(sTitle);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post post = new Post();
                post.setPostKey(postKey);
                post.setTitle(title.getText().toString());
                post.setPicture(uri);
                post.setUserId(MyApp.getCurrentUserId());

                Model.instance.updatePost(post, new Model.basicOnCompleateListener() {
                    @Override
                    public void onCompleate(boolean done) {
                        if (done == true){
                            Navigation.findNavController(view).popBackStack();
                        }
                    }
                });


            }
        });

        return v;
    }

}
