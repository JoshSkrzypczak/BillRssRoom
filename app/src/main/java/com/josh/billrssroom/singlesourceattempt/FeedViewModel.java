package com.josh.billrssroom.singlesourceattempt;

import android.app.Application;

import com.josh.billrssroom.AppExecutors;
import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.db.dao.FeedDao;
import com.josh.billrssroom.model.FeedItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class FeedViewModel extends AndroidViewModel {

    private FeedDao feedDao;
    private FeedRepository feedRepository;

    private final MediatorLiveData<Resource<List<FeedItem>>> observableFeedItems;
    private final LiveData<List<FeedItem>> observableFavorites;


    public FeedViewModel(@NonNull Application application) {
        super(application);

        feedRepository = new FeedRepository(FeedDatabase.getFeedDatabase(application), new AppExecutors());

        feedDao = FeedDatabase.getFeedDatabase(application).feedDao();

        observableFeedItems = new MediatorLiveData<>();
        observableFeedItems.setValue(null);
        LiveData<Resource<List<FeedItem>>> feedItems = ((BasicApp)application).getFeedRepository().loadBillItems();
        observableFeedItems.addSource(feedItems, observableFeedItems::setValue);

        observableFavorites = feedRepository.loadAllFavoriteItems();

    }

    public LiveData<Resource<List<FeedItem>>> getObservableFeedItems() {
        return observableFeedItems;
    }

    public LiveData<List<FeedItem>> getObservableFavorites() {
        return observableFavorites;
    }

    public void updateFeedItemAsFavorite(FeedItem item) {
        feedRepository.updateFeedItemAsFavorite(item);

    }

    public void removeItemFromFavorites(FeedItem feedItem){
        feedRepository.removeItemFromFavorites(feedItem);
    }
}