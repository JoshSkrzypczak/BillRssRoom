package com.josh.billrssroom.singlesourceattempt;

import android.app.Application;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.db.FeedDatabase;
import com.josh.billrssroom.db.dao.FeedDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.FeedRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FeedViewModel extends AndroidViewModel {

    private FeedDao feedDao;
    private FeedRepository feedRepository;

    private final LiveData<Resource<List<FeedItem>>> observableFeedItems;

    public FeedViewModel(@NonNull Application application) {
        super(application);

        feedDao = FeedDatabase.getFeedDatabase(application).feedDao();

        feedRepository = ((BasicApp)application).getFeedRepository();

        observableFeedItems = feedRepository.loadBillItems();
    }

    /**
     * Expose the LiveData Feed query so the UI can observe it.
     */
    public LiveData<Resource<List<FeedItem>>> getObservableFeedItems() {
        return observableFeedItems;
    }

//    public void updateFeedItemAsFavorite(FeedItem item) {
//        feedRepository.updateFeedItemAsFavorite(item);
//
//    }

//    public void removeItemFromFavorites(FeedItem feedItem){
//        feedRepository.removeItemFromFavorites(feedItem);
//    }
}