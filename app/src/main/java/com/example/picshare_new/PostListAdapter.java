package com.example.picshare_new;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.picshare_new.model.Comment;
import com.example.picshare_new.model.Model;
import com.example.picshare_new.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder>{
    List<Post> mData = new LinkedList<>();
    Fragment holdingFragment;

    public void setmData(List<Post> mData) {
        this.mData = mData;
    }




    public PostListAdapter(List<Post> data, Fragment holdingFragment){
        mData = data;
        this.holdingFragment = holdingFragment;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        String postKey, userPhoto, picture, title;

        postKey = mData.get(position).getPostKey();
        userPhoto = mData.get(position).getUserPhoto();
        picture = mData.get(position).getPicture();
        title = mData.get(position).getTitle();

        holder.bind(postKey, userPhoto, picture, title, holdingFragment);


    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

     class PostViewHolder extends RecyclerView.ViewHolder{
        ImageView mUserPhoto, mPicture;
        TextView mTitle;
        RecyclerView mCommentsListRv;
        LinearLayoutManager mLayoutManager;
        CommentListAdapter mCommentListAdapter;
        Button btn, deletePost, updatePost;
        String postKeyOfClass;
        EditText content;
        List<Comment> mData = new LinkedList<>();
        CommentViewModel viewData;
        LiveData<List<Comment>> liveData;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserPhoto = itemView.findViewById(R.id.row_post_uploader_image);
            mPicture = itemView.findViewById(R.id.row_post_image);
            mTitle = itemView.findViewById(R.id.row_post_title);

            content = itemView.findViewById(R.id.row_post_comment);
            btn = itemView.findViewById(R.id.row_post_add_comment_btn);
            deletePost = itemView.findViewById(R.id.row_post_delete_post);
            updatePost = itemView.findViewById(R.id.row_post_updata_post);


            mCommentsListRv = itemView.findViewById(R.id.recycler_view_comments);
            mLayoutManager = new LinearLayoutManager(itemView.getContext());
            mCommentsListRv.setLayoutManager(mLayoutManager);
            mCommentsListRv.setHasFixedSize(true);
            mCommentListAdapter = new CommentListAdapter(mData);


        }

        public void bind(String postKey, String userPhoto,String picture, String title, Fragment holdingFragment){
            dealButtonsAccrdingFragment(holdingFragment, postKey, picture, title);
            postKeyOfClass = postKey;
            if (userPhoto != null)
                Glide.with(MainActivity.context).load(userPhoto).into(mUserPhoto);
            if (picture != null)
                Glide.with(MainActivity.context).load(picture).into(mPicture);
            if (mTitle != null)
                mTitle.setText(title);
            mCommentsListRv.setAdapter(mCommentListAdapter);

            viewData = ViewModelProviders.of(holdingFragment).get(CommentViewModel.class);
            viewData.setListener(postKey);
            liveData = viewData.getmData();
            liveData.observe(holdingFragment, new Observer<List<Comment>>() {
                @Override
                public void onChanged(List<Comment> comments) {
                    if (CommentViewModel.changedPostKey == postKeyOfClass) {
                        mCommentListAdapter.setmData(comments);
                        mCommentListAdapter.notifyDataSetChanged();
                    }
                }
            });







            Model.instance.getAllcommentsOfPost(postKey, new Model.GetAllCommentsOfPostListener() {
                @Override
                public void onCompleate(List<Comment> data) {
                    mData = data;
                    mCommentListAdapter.setmData(data);
                    mCommentListAdapter.notifyDataSetChanged();
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Comment comment = new Comment("10",content.getText().toString(), MyApp.getCurrentUserId(), postKey, "user image");
                    Model.instance.addComment(comment, new Model.addCommentListener() {

                        @Override
                        public void onComplete(Comment comment) {
                            mData.add(comment);
                            mCommentListAdapter.setmData(mData);
                            mCommentListAdapter.notifyDataSetChanged();
                            holdingFragment.onAttach(holdingFragment.getContext());
                        }
                    });
                }
            });

        }
        private void dealButtonsAccrdingFragment(Fragment fragment, String postKey, String picture, String title){
            if(Home.class == fragment.getClass()){
                deletePost.setVisibility(View.GONE);
                updatePost.setVisibility(View.GONE);
            }
            else {
                deletePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Model.instance.deletePost(postKeyOfClass, new Model.basicOnCompleateListener() {

                            @Override
                            public void onCompleate(boolean done) {
                                holdingFragment.onAttach(holdingFragment.getContext());
                            }
                        });
                    }
                });

                ProfileDirections.ActionProfileFragmentToUpdateFragment actionToUpdate = ProfileDirections.actionProfileFragmentToUpdateFragment(postKey, picture, title);
                updatePost.setOnClickListener(Navigation.createNavigateOnClickListener(actionToUpdate));

            }

        }
    }
}
