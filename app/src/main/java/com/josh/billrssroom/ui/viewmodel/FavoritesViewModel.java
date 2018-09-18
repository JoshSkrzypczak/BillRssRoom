package com.josh.billrssroom.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.model.BillModel;
import com.josh.billrssroom.repository.FavoritesRepository;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesRepository repository;

    private LiveData<List<BillModel>> allFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoritesRepository(application);
        allFavorites = repository.getAllBills();
    }

    public LiveData<List<BillModel>> getAllFavorites() {
        return allFavorites;
    }

    public void insertSingleRecord(BillModel billModel){
        repository.insertSingleRecord(billModel);
    }

    public void deleteSingleRecord(BillModel billModel){
        repository.deleteSingleFavorite(billModel);
    }
}
