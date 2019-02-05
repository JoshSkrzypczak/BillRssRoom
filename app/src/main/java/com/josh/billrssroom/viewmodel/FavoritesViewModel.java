package com.josh.billrssroom.viewmodel;

import android.app.Application;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.FeedRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class FavoritesViewModel extends AndroidViewModel {

    private FeedRepository feedRepository;

    private final MediatorLiveData<List<FeedItem>> observableFavorites;


    private MutableLiveData<List<FeedItem>> mutableLiveData;


    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        feedRepository = ((BasicApp) application).getFeedRepository();


        mutableLiveData = feedRepository.getMutableLiveData();


        observableFavorites = new MediatorLiveData<>();
        observableFavorites.setValue(null);
        LiveData<List<FeedItem>> favorites = ((BasicApp) application).getFeedRepository().getFavorites();
        observableFavorites.addSource(favorites, observableFavorites::setValue);
    }

    public LiveData<List<FeedItem>> getMutableLiveData(){
        return this.mutableLiveData;
    }

    public LiveData<List<FeedItem>> getFavorites() {
        return observableFavorites;
    }

    public void deleteAllFavorites() {
        feedRepository.deleteAllFavorites();
    }

    public void removeItemFromFavorites(FeedItem feedItem) {
        feedRepository.removeItemFromFavorites(feedItem);
    }

    public void updateRemoveItemFromFavorites(FeedItem feedItem){
        feedRepository.updateFeedItemAsFavorite(feedItem);
    }

    public LiveData<List<FeedItem>> searchFavorites(String query){
        return feedRepository.searchFavorites(query);
    }

    public int getNumFavCount(){
        return feedRepository.getNumFavCount();
    }
}
