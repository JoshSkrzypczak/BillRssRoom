package com.josh.billrssroom.ui.favorites;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.repository.FavoritesRepository;
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

    public void insertSingleRecord(BillItem billItem){
        repository.insertSingleRecord(billItem);
    }

    public void deleteSingleRecord(BillItem billItem){
        repository.deleteSingleFavorite(billItem);
    }
}
