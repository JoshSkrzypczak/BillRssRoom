package com.josh.billrssroom.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.FavRepository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FavRepository favRepository;
    private LiveData<List<FeedItem>> allFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        favRepository = new FavRepository(application);
        allFavorites = favRepository.getAllFavorites();
    }

    public LiveData<List<FeedItem>> getAllFavorites(){
        return allFavorites;
    }

    public void delete(FeedItem feedItem){
        favRepository.delete(feedItem);
    }
}