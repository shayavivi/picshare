package com.example.picshare_new;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class new_picture extends Fragment {


    public new_picture() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_picture, container, false);

//        String user_id = new_pictureArgs.fromBundle(getArguments()).getUserId();
//        TextView textView = v.findViewById(R.id.new_picture);
//        textView.setText(user_id);

        return  v;
    }

}
