package com.example.picshare_new;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class MyApp extends MultiDexApplication {
    static Context contest;
    static String current_user_id;

    public static Context getContest() {
        return contest;
    }

    public static void setContest(Context contest) {
        contest = contest;
    }

    public static String getCurrentUserId() {
        return current_user_id;
    }

    public static void setCurrentUserId(String currentUserId) {
        current_user_id = currentUserId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        contest = getApplicationContext();
    }
}
