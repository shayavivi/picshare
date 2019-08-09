package com.example.picshare_new;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.picshare_new.model.Post;

import java.util.List;
import java.util.Vector;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder>{
    List<Post> mdata;

    public PostListAdapter(List<Post> data){
        mdata = data;
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
        String str = mdata.get(position).getId();
        holder.bind(str);


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{
        ImageView mpost;
        EditText editText;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mpost = itemView.findViewById(R.id.row_post_image);
            EditText editText = itemView.findViewById(R.id.editText);
        }

        public void bind(String str){
            if (editText != null)
                editText.setText(str);
        }
    }
}
