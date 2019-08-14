package com.example.picshare_new;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.picshare_new.model.Model;
import com.example.picshare_new.model.Post;

import java.util.LinkedList;
import java.util.List;

class HomeViewModel extends ViewModel {
    MutableLiveData<List<Post>> mData = new MutableLiveData<>();
    public HomeViewModel(){
        Model.instance.getAllPosts(new Model.GetAllPostsListener() {
            @Override
            public void onCompleate(List<Post> data) {
                mData.setValue(data);
            }
        });
    }
    public LiveData<List<Post>> getmData() {
        return mData;
    }

    public void setmData(List<Post> mData) {
        this.mData.setValue(mData);
    }


}
