package com.example.picshare_new;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.picshare_new.model.Model;
import com.example.picshare_new.model.Post;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    RecyclerView mPostsListRv;
    LinearLayoutManager mLayoutManeger;
    PostListAdapter mPostListAdapter;
    List<Post> mData = new LinkedList<>();

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        Button btn = v.findViewById(R.id.home_next_btn);
//        btn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_home_to_profile));
        mPostsListRv = v.findViewById(R.id.recycler_view_posts);
        mLayoutManeger = new LinearLayoutManager(v.getContext());
        mPostsListRv.setLayoutManager(mLayoutManeger);
        mPostsListRv.setHasFixedSize(true);
        mPostListAdapter = new PostListAdapter(mData);
        mPostsListRv.setAdapter(mPostListAdapter);

        Model.instance.getAllPosts(new Model.GetAllPostsListener() {
            @Override
            public void onCompleate(List<Post> data) {
                mData = data;
                mPostListAdapter.setmData(data);
                mPostListAdapter.notifyDataSetChanged();
            }
        });


        return v;

    }

}
