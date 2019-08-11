package com.example.picshare_new;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picshare_new.model.Post;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder>{
    List<Post> mData;

    public PostListAdapter(List<Post> data){
        mData = data;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        PostViewHolder postViewHolder= new PostViewHolder(view);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        String userPhoto, picture, title;
        Object timeStamp;

        userPhoto = mData.get(position).getUserPhoto();
        picture = mData.get(position).getPicture();
        title = mData.get(position).getTitle();
        timeStamp = mData.get(position).getTimeStamp();

        holder.bind(userPhoto, picture, title, timeStamp);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{
        ImageView mUserPhoto, mPicture;
        TextView mTitle, mTimeStamp;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserPhoto = itemView.findViewById(R.id.row_post_uploader_image);
            mPicture = itemView.findViewById(R.id.row_post_image);
            mTitle = itemView.findViewById(R.id.row_post_title);
            mTimeStamp = itemView.findViewById(R.id.row_post_date);
//            EditText editText = itemView.findViewById(R.id.editText);
        }

        public void bind(String userPhoto,String picture, String title, Object timeStamp){
//            if (mUserPhoto != null)
//                mUserPhoto.setText(str);
//            if (mPicture != null)
//                mPicture.setText(str);
            if (mTitle != null)
                mTitle.setText(title);
            if (mTimeStamp != null)
                mTimeStamp.setText(timeStamp.toString());
        }
    }
}
