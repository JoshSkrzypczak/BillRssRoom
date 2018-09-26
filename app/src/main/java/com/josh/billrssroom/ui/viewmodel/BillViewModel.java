package com.josh.billrssroom.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.annotation.NonNull;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.db.dao.FeedDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.BillRepository;
import com.josh.billrssroom.repository.FavoriteRepository;

import java.util.List;


/**
 * The class that prepares the data for viewing in the MainActivity and reacts to user interactions
 */
public class BillViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
//    private final MediatorLiveData<List<FeedItem>> observableBills;

    private final MediatorLiveData<Resource<List<FeedItem>>> secondAttemptObservableBills;

    private FeedDao feedDao;
    private BillRepository billRepository;
    private FavoriteRepository favoriteRepository;

//    private final LiveData<Resource<List<FeedItem>>> items;

    public BillViewModel(@NonNull Application application) {
        super(application);

//        favoriteRepository = new FavoriteRepository(application);

        favoriteRepository = ((BasicApp)application).getFavoriteRepository();

        feedDao = BillDatabase.getFeedDatabase(application).billDao();

        LiveData<Resource<List<FeedItem>>> feedItems = ((BasicApp) application).getRepository().loadBillItems();
        secondAttemptObservableBills = new MediatorLiveData<>();
        secondAttemptObservableBills.addSource(feedItems, secondAttemptObservableBills::setValue);
    }

    public MediatorLiveData<Resource<List<FeedItem>>> getSecondAttemptObservableBills() {
        return secondAttemptObservableBills;
    }

    public void insertItemToFavorites(FeedItem feedItem){
        favoriteRepository.insert(feedItem);
    }
}