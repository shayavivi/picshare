package com.example.picshare_new;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.picshare_new.model.Comment;
import com.example.picshare_new.model.Model;
import com.example.picshare_new.model.Post;

import java.util.List;

public class CommentViewModel extends ViewModel {
    MutableLiveData<List<Comment>> mData = new MutableLiveData<>();
    static String changedPostKey;
    public CommentViewModel(){
    }
    public LiveData<List<Comment>> getmData() {
        return mData;
    }

    public void setmData(List<Comment> mData) {
        this.mData.setValue(mData);
    }

    public void setListener(String postKey) {
        Model.instance.getAllcommentsOfPost(postKey, new Model.GetAllCommentsOfPostListener() {

            @Override
            public void onCompleate(List<Comment> data) {
                changedPostKey = postKey;
                mData.setValue(data);
            }
        });

    }
}
