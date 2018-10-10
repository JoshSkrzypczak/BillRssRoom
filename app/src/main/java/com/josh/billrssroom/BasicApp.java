package com.josh.billrssroom;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.josh.billrssroom.db.FeedDatabase;
import com.josh.billrssroom.repository.FeedRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();

        Stetho.initializeWithDefaults(this);
    }

    public FeedDatabase getFeedDatabase(){
        return FeedDatabase.getFeedDatabase(this);
    }

    public FeedRepository getFeedRepository(){
        return FeedRepository.getInstance(getFeedDatabase(), mAppExecutors);
    }
}