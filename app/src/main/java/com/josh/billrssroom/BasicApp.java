package com.josh.billrssroom;

import android.app.Application;

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
    }

    public FeedDatabase getDatabase() {
        return FeedDatabase.getDatabase(this, mAppExecutors);
    }

    public FeedRepository getRepository() {
        return FeedRepository.getInstance(getDatabase());
    }
}