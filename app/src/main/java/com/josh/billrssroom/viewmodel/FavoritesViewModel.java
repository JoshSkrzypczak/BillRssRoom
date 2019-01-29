package com.josh.billrssroom.viewmodel;

import android.app.Application;
import android.util.Log;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.FeedRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class FavoritesViewModel extends AndroidViewModel {

    private FeedRepository feedRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<FeedItem>> observableFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        feedRepository = ((BasicApp) application).getFeedRepository();

        observableFavorites = new MediatorLiveData<>();
        observableFavorites.setValue(null);
        LiveData<List<FeedItem>> favorites = ((BasicApp) application).getFeedRepository().getFavorites();
        // observe the changes of the items from the database and forward them
        observableFavorites.addSource(favorites, observableFavorites::setValue);
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

    public LiveData<List<FeedItem>> searchFavorites(String query){
        return feedRepository.searchFavorites(query);
    }
}
