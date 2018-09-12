package com.josh.billrssroom;

import android.app.Application;

import com.josh.billrssroom.db.AppDatabase;

public class BasicApp extends Application {

    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        this.appExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getDatabase(this);
    }

    public FeedRepository getRepository(){
        return FeedRepository.getInstance();
    }
}
