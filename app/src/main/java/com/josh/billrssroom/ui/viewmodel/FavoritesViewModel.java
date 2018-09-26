package com.josh.billrssroom.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.FavoriteRepository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoriteRepository favoriteRepository;

    private LiveData<List<FeedItem>> allFavorites;

    private final MediatorLiveData<List<FeedItem>> observableFavorites;


    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        favoriteRepository = ((BasicApp)application).getFavoriteRepository();

        observableFavorites = new MediatorLiveData<>();
        observableFavorites.setValue(null);

        LiveData<List<FeedItem>> favorites = ((BasicApp)application).getFavoriteRepository().getAllFavorites();

        observableFavorites.addSource(favorites, observableFavorites::setValue);
    }

    public LiveData<List<FeedItem>> getAllFavorites(){
        return observableFavorites;
    }

    public void delete(FeedItem feedItem){
        favoriteRepository.delete(feedItem);
    }
}