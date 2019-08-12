package com.example.picshare_new;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picshare_new.model.Comment;
import com.example.picshare_new.model.Model;
import com.example.picshare_new.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder>{
    List<Post> mData = new LinkedList<>();

    public void setmData(List<Post> mData) {
        this.mData = mData;
    }




    public PostListAdapter(List<Post> data){
        mData = data;
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
        Object timeStamp;

        postKey = mData.get(position).getPostKey();
        userPhoto = mData.get(position).getUserPhoto();
        picture = mData.get(position).getPicture();
        title = mData.get(position).getTitle();
        timeStamp = mData.get(position).getTimeStamp();

        holder.bind(postKey, userPhoto, picture, title, timeStamp);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{
        ImageView mUserPhoto, mPicture;
        TextView mTitle, mTimeStamp;
        RecyclerView mCommentsListRv;
        LinearLayoutManager mLayoutManager;
        CommentListAdapter mCommentListAdapter;
        Button btn;
        EditText content;
        List<Comment> mData = new LinkedList<>();

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserPhoto = itemView.findViewById(R.id.row_post_uploader_image);
            mPicture = itemView.findViewById(R.id.row_post_image);
            mTitle = itemView.findViewById(R.id.row_post_title);
            mTimeStamp = itemView.findViewById(R.id.row_post_date);

            content = itemView.findViewById(R.id.row_post_comment);
            btn = itemView.findViewById(R.id.row_post_add_comment_btn);


            mCommentsListRv = itemView.findViewById(R.id.recycler_view_comments);
            mLayoutManager = new LinearLayoutManager(itemView.getContext());
            mCommentsListRv.setLayoutManager(mLayoutManager);
            mCommentsListRv.setHasFixedSize(true);
            mCommentListAdapter = new CommentListAdapter(mData);

        }

        public void bind(String postKey, String userPhoto,String picture, String title, Object timeStamp){
//            if (mUserPhoto != null)
//                mUserPhoto.setText(str);
//            if (mPicture != null)
//                mPicture.setText(str);
            if (mTitle != null)
                mTitle.setText(title);
            if (mTimeStamp != null)
                mTimeStamp.setText(timeStamp.toString());
            mCommentsListRv.setAdapter(mCommentListAdapter);
            Model.instance.getAllcommentsOfPosts(postKey,new Model.GetAllCommentsOfPostListener() {
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
                    // get user image and username from query
                    Comment comment = new Comment("10",content.getText().toString(), "userid", postKey, "user image", "username");
                    mData.add(comment);
                    mCommentListAdapter.setmData(mData);
                    mCommentListAdapter.notifyDataSetChanged();
                }
            });

        }
    }
}
