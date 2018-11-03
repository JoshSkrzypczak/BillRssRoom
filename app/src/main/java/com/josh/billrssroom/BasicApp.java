package com.josh.billrssroom;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.josh.billrssroom.common.dependencyinjection.CompositionRoot;
import com.josh.billrssroom.db.FeedDatabase;
import com.josh.billrssroom.repository.FeedRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicApp extends Application {

    private CompositionRoot compositionRoot;

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();

        Stetho.initializeWithDefaults(this);

        this.compositionRoot = new CompositionRoot();
    }

    public FeedDatabase getFeedDatabase(){
        return FeedDatabase.getFeedDatabase(this);
    }

    public FeedRepository getFeedRepository(){
        return FeedRepository.getInstance(getFeedDatabase(), mAppExecutors);
    }

    public CompositionRoot getCompositionRoot() {
        return compositionRoot;
    }
}