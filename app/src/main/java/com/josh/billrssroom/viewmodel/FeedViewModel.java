package com.josh.billrssroom.viewmodel;

import android.app.Application;
import android.util.Log;

import com.josh.billrssroom.BasicApp;
import com.josh.billrssroom.db.FeedDatabase;
import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.networking.Resource;
import com.josh.billrssroom.repository.FeedRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class FeedViewModel extends AndroidViewModel {

    public static final String TAG = FeedViewModel.class.getSimpleName();

    private ItemDao itemDao;

    private FeedRepository feedRepository;

    private final LiveData<Resource<List<FeedItem>>> liveDataFeedItems;



    public FeedViewModel(@NonNull Application application) {
        super(application);

        itemDao = FeedDatabase.getFeedDatabase(application).feedDao();

        feedRepository = ((BasicApp) application).getFeedRepository();

        liveDataFeedItems = feedRepository.loadBillItems();
    }

    public LiveData<Resource<List<FeedItem>>> getLiveDataFeedItems() {
        Log.d(TAG, "getLiveDataFeedItems: ");
        return liveDataFeedItems;
    }
}