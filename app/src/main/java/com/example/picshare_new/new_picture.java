package com.example.picshare_new;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.picshare_new.model.Model;
import com.example.picshare_new.model.Post;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class new_picture extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;
    EditText title;
    ImageView pic;
    boolean pickedImage = false;
    Button saveBtn;
    private Bitmap imageBitmap;


    public new_picture() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_picture, container, false);
        title = v.findViewById(R.id.newPictureTitle);
        pic = v.findViewById(R.id.newPicturePhoto);
        saveBtn = v.findViewById(R.id.newPictureSave);


        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                if (title.getText().toString() != "" && pickedImage){
                    Post post = new Post();
                    post.setTitle(title.getText().toString());
                    post.setUserId(MyApp.getCurrentUserId());
                    Model.instance.addPost(post, imageBitmap, new Model.addPostListener() {
                        @Override
                        public void onComplete(Post post) {
                            Navigation.findNavController(getView()).popBackStack();
                        }
                    });
            }
            }
        });

//        String user_id = new_pictureArgs.fromBundle(getArguments()).getUserId();
//        TextView textView = v.findViewById(R.id.new_picture);
//        textView.setText(user_id);

        return  v;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            pic.setImageBitmap(imageBitmap);
            pickedImage = true;
        }
    }

}
