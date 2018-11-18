package com.josh.billrssroom.viewmodel;

import android.app.Application;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.networking.Resource;
import com.josh.billrssroom.db.FeedDatabase;
import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.FeedRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class FeedViewModel extends AndroidViewModel {
    private ItemDao itemDao;
    private FeedRepository feedRepository;

    private final LiveData<Resource<List<FeedItem>>> liveDataFeedItems;

    private final MediatorLiveData<List<FeedItem>> favoriteMediatorLd;

    public FeedViewModel(@NonNull Application application) {
        super(application);

        itemDao = FeedDatabase.getFeedDatabase(application).feedDao();

        feedRepository = ((BasicApp)application).getFeedRepository();

        liveDataFeedItems = feedRepository.loadBillItems();

        favoriteMediatorLd = new MediatorLiveData<>();
        favoriteMediatorLd.setValue(null);
        LiveData<List<FeedItem>> favorites = ((BasicApp)application).getFeedRepository().getFavorites();

        favoriteMediatorLd.addSource(favorites, favoriteMediatorLd::setValue);
    }


    public LiveData<Resource<List<FeedItem>>> getObservableFeedItems() {
        return liveDataFeedItems;
    }

    public LiveData<List<FeedItem>> getFavoriteFeedItems(){
        return favoriteMediatorLd;
    }

    public void deleteAllFavorites(){
        feedRepository.deleteAllFavorites();
    }

    public void updateItemAsFavorite(FeedItem feedItem){
        feedRepository.updateFeedItemAsFavorite(feedItem);
    }

    public void removeItemFromFavorites(FeedItem feedItem){
        feedRepository.removeItemFromFavorites(feedItem);
    }
}