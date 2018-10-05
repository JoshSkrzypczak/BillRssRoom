package com.josh.billrssroom.viewmodel;

import android.app.Application;
import android.widget.ImageButton;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.R;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.db.FavoritesDatabase;
import com.josh.billrssroom.db.dao.FeedDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.repository.BillRepository;
import com.josh.billrssroom.repository.FavoriteRepository;

import java.util.List;


/**
 * The class that prepares the data for viewing in the MainActivity and reacts to user interactions
 */
public class BillViewModel extends AndroidViewModel {

    private final MediatorLiveData<Resource<List<FeedItem>>> observableFeedItems;

    private FeedDao feedDao;
    private BillRepository billRepository;
    private FavoriteRepository favoriteRepository;


    public BillViewModel(@NonNull Application application) {
        super(application);

        feedDao = BillDatabase.getFeedDatabase(application).billDao();

        favoriteRepository = ((BasicApp) application).getFavoriteRepository();

        billRepository = ((BasicApp) application).getRepository();


        /* Feed Items */
        LiveData<Resource<List<FeedItem>>> feedItems = ((BasicApp) application).getRepository().loadBillItems();
        observableFeedItems = new MediatorLiveData<>();
        observableFeedItems.addSource(feedItems, observableFeedItems::setValue);

    }

    public MediatorLiveData<Resource<List<FeedItem>>> getObservableFeedItems() {
        return observableFeedItems;
    }

    public void insertItemToFavorites(FeedItem feedItem) {
        favoriteRepository.insert(feedItem);
    }
}