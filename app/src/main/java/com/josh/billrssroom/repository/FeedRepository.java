package com.josh.billrssroom.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.josh.billrssroom.AppExecutors;
import com.josh.billrssroom.networking.ApiResponse;
import com.josh.billrssroom.networking.DataService;
import com.josh.billrssroom.networking.NetworkBoundResource;
import com.josh.billrssroom.networking.Resource;
import com.josh.billrssroom.networking.RetrofitClient;
import com.josh.billrssroom.db.FeedDatabase;
import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.model.RssResult;
import com.josh.billrssroom.utilities.RateLimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class FeedRepository {
    public static final String TAG = FeedRepository.class.getSimpleName();

    private static FeedRepository INSTANCE;
    private ItemDao itemDao;
    private final AppExecutors appExecutors;
    private final FeedDatabase feedDatabase;
    private final DataService apiService;

    private RateLimiter<String> billFeedRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

//    private final LiveData<List<FeedItem>> favorites;

    private MediatorLiveData<List<FeedItem>> mObservableFavorites;


    public FeedRepository(final FeedDatabase feedDatabase, AppExecutors appExecutors) {
        this.feedDatabase = feedDatabase;
        this.appExecutors = appExecutors;

        itemDao = feedDatabase.feedDao();

        apiService = RetrofitClient.getInstance().getRestApi();

//        favorites = itemDao.loadFavorites();

        mObservableFavorites = new MediatorLiveData<>();
        mObservableFavorites.addSource(feedDatabase.feedDao().loadFavorites(), favorites -> {
            mObservableFavorites.postValue(favorites);
        });
    }

    public LiveData<List<FeedItem>> getFavorites() {
        return mObservableFavorites;
    }

    public LiveData<List<FeedItem>> searchFavorites(String query){
        return feedDatabase.feedDao().searchFavorites(query);
    }


    public LiveData<Resource<List<FeedItem>>> loadBillItems() {
        return new NetworkBoundResource<List<FeedItem>, RssResult>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull RssResult item) {
                Log.d(TAG, "saveCallResult: ");
//                feedDatabase.feedDao().insertData(item.getChannel().getItems());

                for (FeedItem feedItem : item.getChannel().getItems()) {
                    String billTitle = feedDatabase.feedDao().getItemTitle(feedItem.getTitle());

                    System.out.println(":::billTitle::: " + billTitle);

                    if (billTitle == null) {
                        feedDatabase.feedDao().insertItem(feedItem);
                    } else {
                        feedDatabase.feedDao().updateItem(feedItem.pubDate, feedItem.description, feedItem.title);
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<FeedItem> data) {
                Log.d(TAG, "shouldFetch: ");
                return data == null || data.isEmpty() || billFeedRateLimit.shouldFetch(data.toString());
            }

            @NonNull
            @Override
            protected LiveData<List<FeedItem>> loadFromDb() {
                Log.d(TAG, "loadFromDb: ");
                return feedDatabase.feedDao().loadFeedDbItems();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RssResult>> createCall() {
                Log.d(TAG, "createCall: ");
                return apiService.getMyService();
            }
        }.asLiveData();
    }

    public void deleteAllFavorites() {
        new deleteAllFavoritesAsyncTask(itemDao).execute();
    }

    public void removeItemFromFavorites(FeedItem feedItem) {
        new removeItemFromFavoritesAsync(itemDao).execute(feedItem);
    }

    public void updateFeedItemAsFavorite(FeedItem item) {
        new updateFeedItemFavoriteAsync(itemDao).execute(item);
    }

    private static class updateFeedItemFavoriteAsync extends AsyncTask<FeedItem, Void, Void> {

        private ItemDao asyncDao;

        updateFeedItemFavoriteAsync(ItemDao dao) {
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(FeedItem... items) {

            int favoriteValueInt = asyncDao.getIntBoolean(items[0].getTitle());

            if (favoriteValueInt == 1) {
                Log.d(TAG, "doInBackground: updateAndSetItemToFalse");
                // Sets isFavorite to false
                // Item previously favorite; onClick sets favorite to false
                // Show empty image drawable
                asyncDao.updateAndSetItemToFalse(items[0].getTitle());
            } else {
                Log.d(TAG, "doInBackground: updateAndSetItemToTrue");
                // Sets isFavorite to true
                // Item is not favorite; onClick sets favorite to true
                // Show full image drawable
                asyncDao.updateAndSetItemToTrue(items[0].getTitle());
            }
            return null;
        }
    }

    private static class removeItemFromFavoritesAsync extends AsyncTask<FeedItem, Void, Void> {
        private ItemDao asyncRemoveDao;

        public removeItemFromFavoritesAsync(ItemDao asyncRemoveDao) {
            this.asyncRemoveDao = asyncRemoveDao;
        }

        @Override
        protected Void doInBackground(FeedItem... items) {

            asyncRemoveDao.updateAndSetItemToFalse(items[0].getTitle());

            return null;
        }
    }

    private static class deleteAllFavoritesAsyncTask extends AsyncTask<Void, Void, Void> {

        private ItemDao asyncItemDao;

        deleteAllFavoritesAsyncTask(ItemDao itemDao) {
            asyncItemDao = itemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncItemDao.deleteAllFavorites();
            return null;
        }
    }

    public static FeedRepository getInstance(final FeedDatabase feedDatabase, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (FeedRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedRepository(feedDatabase, appExecutors);
                }
            }
        }
        return INSTANCE;
    }
}