package com.josh.billrssroom.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.FavoritesRepository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesRepository repository;

    private LiveData<List<FeedItem>> items;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoritesRepository(application);
        items = repository.getAllBills();
    }

    public LiveData<List<FeedItem>> getAllFavorites() {
        return items;
    }

//    public void insert(FeedItem... items){
//        repository.insertSingleRecord(items[0]);
//    }
//
//    public void delete(FeedItem... items){
//        repository.deleteSingleFavorite(items[0]);
//    }
}
