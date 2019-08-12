package com.example.picshare_new;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picshare_new.model.Comment;

import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {
    public List<Comment> getmData() {
        return mData;
    }

    public void setmData(List<Comment> mData) {
        this.mData = mData;
    }

    List<Comment> mData;

    public CommentListAdapter(List<Comment> data){
        mData = data;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comment, parent, false);
        CommentViewHolder commentViewHolder= new CommentViewHolder(view);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        String content,userImage,username;
        Object timestamp;

        content = mData.get(position).getContent();
        userImage = mData.get(position).getUimg();
        username = mData.get(position).getUname();
        timestamp = mData.get(position).getTimestamp();
        holder.bind(content, userImage, username, timestamp);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder{
        ImageView mUserImage;
        TextView mContent, nUsername, mTimestamp;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserImage = itemView.findViewById(R.id.row_comment_image);
            mContent = itemView.findViewById(R.id.row_comment_comment_text);
            nUsername = itemView.findViewById(R.id.row_comment_username);
            mTimestamp = itemView.findViewById(R.id.row_comment_date);


        }
        public void bind(String content, String userImage, String username, Object timestamp){
//            if (mUserImage != null)
//                mUserImage.setText(content);
            if (mContent != null)
                mContent.setText(content);
            if (nUsername != null)
                nUsername.setText(username);
            if (mTimestamp != null)
                mTimestamp.setText(timestamp.toString());

        }

    }
}
