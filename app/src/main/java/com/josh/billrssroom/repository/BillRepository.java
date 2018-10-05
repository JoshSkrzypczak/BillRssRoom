package com.josh.billrssroom.repository;

import com.josh.billrssroom.AppExecutors;
import com.josh.billrssroom.api.ApiResponse;
import com.josh.billrssroom.api.DataService;
import com.josh.billrssroom.api.NetworkBoundResource;
import com.josh.billrssroom.api.Resource;
import com.josh.billrssroom.api.RetrofitClient;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.db.dao.FeedDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.model.RssResult;
import com.josh.billrssroom.utilities.RateLimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


public class BillRepository {
    private static BillRepository INSTANCE;
    private final DataService apiService;
    private final BillDatabase billDatabase;

    private final AppExecutors appExecutors;
    private FeedDao feedDao;
    private RateLimiter<String> billFeedRateLimit = new RateLimiter<>(5, TimeUnit.MINUTES);


    public BillRepository(final BillDatabase billDatabase, AppExecutors appExecutors) {
        this.billDatabase = billDatabase;
        this.appExecutors = appExecutors;

        feedDao = billDatabase.billDao();

        apiService = RetrofitClient.getInstance().getRestApi();
    }

    public LiveData<Resource<List<FeedItem>>> loadBillItems() {
        return new NetworkBoundResource<List<FeedItem>, RssResult>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull RssResult item) {
                billDatabase.billDao().insertData(item.getChannel().getItems());
            }

            @Override
            protected boolean shouldFetch(@androidx.annotation.Nullable List<FeedItem> data) {
                return data == null || data.isEmpty() || billFeedRateLimit.shouldFetch(data.toString());
            }

            @NonNull
            @Override
            protected LiveData<List<FeedItem>> loadFromDb() {
                return billDatabase.billDao().loadItems();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RssResult>> createCall() {
                return apiService.getMyService();
            }
        }.asLiveData();
    }

    public static BillRepository getInstance(final BillDatabase billDatabase, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (BillRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BillRepository(billDatabase, appExecutors);
                }
            }
        }
        return INSTANCE;
    }
}