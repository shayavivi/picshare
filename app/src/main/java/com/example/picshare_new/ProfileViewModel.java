package com.example.picshare_new;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.picshare_new.model.Model;
import com.example.picshare_new.model.Post;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    MutableLiveData<List<Post>> mData = new MutableLiveData<>();
    public ProfileViewModel(){

    }
    public void setListenerProfile(String userId){
        Model.instance.getAllPostsByUserId(new Model.GetAllPostsListener() {//change to get all by id
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
