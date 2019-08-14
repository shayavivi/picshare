package com.example.picshare_new.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface UserDao {
    @Query("select * from User where userId = :id")
    User getById(String id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

}

public class UserAsyncDao{

}
