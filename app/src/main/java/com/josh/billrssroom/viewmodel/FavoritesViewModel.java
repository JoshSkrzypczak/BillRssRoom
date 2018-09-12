package com.josh.billrssroom.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.db.FavoritesRepository;
import com.josh.billrssroom.model.BillItem;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesRepository repository;

    private LiveData<List<BillItem>> allFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoritesRepository(application);
        allFavorites = repository.getAllBills();
    }

    public LiveData<List<BillItem>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(BillItem billItem){
        repository.insert(billItem);
    }
}
