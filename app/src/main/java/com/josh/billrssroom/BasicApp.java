package com.josh.billrssroom;

import android.app.Application;

import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.db.FavoritesDatabase;
import com.josh.billrssroom.repository.BillRepository;
import com.josh.billrssroom.repository.FavoriteRepository;
import com.josh.billrssroom.singlesourceattempt.FeedDatabase;
import com.josh.billrssroom.singlesourceattempt.FeedRepository;

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

    public BillDatabase getDatabase() {
        return BillDatabase.getFeedDatabase(this);
    }

    public BillRepository getRepository() {
        return BillRepository.getInstance(getDatabase(), mAppExecutors);
    }

    public FavoritesDatabase getFavoriteDatabase(){
        return FavoritesDatabase.getFavoriteDatabase(this);
    }

    public FavoriteRepository getFavoriteRepository(){
        return FavoriteRepository.getInstance(getFavoriteDatabase());
    }

    public FeedDatabase getFeedDatabase(){
        return FeedDatabase.getFeedDatabase(this);
    }

    public FeedRepository getFeedRepository(){
        return FeedRepository.getInstance(getFeedDatabase(), mAppExecutors);
    }
}