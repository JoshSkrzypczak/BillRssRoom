package com.josh.billrssroom.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.db.dao.FavoritesDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.BillRepository;
import com.josh.billrssroom.repository.FavRepository;

import java.util.List;


/**
 * The class that prepares the data for viewing in the MainActivity and reacts to user interactions
 */
public class BillViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<FeedItem>> observableBills;
    private FavoritesDao favoritesDao;
    private BillRepository billRepository;
    private FavRepository favRepository;

    public BillViewModel(@NonNull Application application) {
        super(application);

        favRepository = new FavRepository(application);

        favoritesDao = BillDatabase.getFeedDatabase(application).billDao();

        observableBills = new MediatorLiveData<>();

        observableBills.setValue(null);

        LiveData<List<FeedItem>> items = ((BasicApp) application).getRepository().getFeedItems();

        observableBills.addSource(items, observableBills::setValue);
    }

    public LiveData<List<FeedItem>> getAllBills() {
        return observableBills;
    }

    public void insert(FeedItem feedItem){
        favRepository.insert(feedItem);
    }
}