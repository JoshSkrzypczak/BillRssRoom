package com.josh.billrssroom.viewmodel;

import android.app.Application;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.db.FeedDatabase;
import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.networking.Resource;
import com.josh.billrssroom.repository.FeedRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class FeedViewModel extends AndroidViewModel {

    private ItemDao itemDao;

    private FeedRepository feedRepository;

    private final MediatorLiveData<List<FeedItem>> mObservableFavorites;
    private final MediatorLiveData<List<FeedItem>> mObservableFeedItems;

    private final LiveData<Resource<List<FeedItem>>> liveDataFeedItems;



    public FeedViewModel(@NonNull Application application) {
        super(application);

        itemDao = FeedDatabase.getFeedDatabase(application).feedDao();

        feedRepository = ((BasicApp) application).getFeedRepository();

        liveDataFeedItems = feedRepository.loadBillItems();

        mObservableFavorites = new MediatorLiveData<>();
        mObservableFavorites.setValue(null);
        LiveData<List<FeedItem>> favorites = ((BasicApp) application).getFeedRepository().getObservableFavorites();
        mObservableFavorites.addSource(favorites, mObservableFavorites::setValue);


        mObservableFeedItems = new MediatorLiveData<>();
        mObservableFeedItems.setValue(null);
        LiveData<List<FeedItem>> feedItems = ((BasicApp) application).getFeedRepository().getObservableFeedItems();
        mObservableFeedItems.addSource(feedItems, mObservableFeedItems::setValue);

    }

    public LiveData<Resource<List<FeedItem>>> getLiveDataFeedItems() {
        return liveDataFeedItems;
    }

    public LiveData<List<FeedItem>> searchFeedItems(String query){
        return feedRepository.searchFeedItems(query);
    }

    public LiveData<List<FeedItem>> getObservableFeedItems() {
        return mObservableFeedItems;
    }


    public LiveData<List<FeedItem>> searchFavorites(String query){
        return feedRepository.searchFavorites(query);
    }

    public LiveData<List<FeedItem>> getFavorites() {
        return mObservableFavorites;
    }

    public void deleteAllFavorites() {
        feedRepository.deleteAllFavorites();
    }





    public void updateItemAsFavorite(FeedItem feedItem) {
        feedRepository.updateFeedItemAsFavorite(feedItem);
    }

    public void removeItemFromFavorites(FeedItem feedItem) {
        feedRepository.removeItemFromFavorites(feedItem);
    }


}