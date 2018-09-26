package com.josh.billrssroom.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import android.os.AsyncTask;

import com.josh.billrssroom.AppExecutors;
import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.db.FavoritesDatabase;
import com.josh.billrssroom.db.dao.FeedDao;
import com.josh.billrssroom.model.FeedItem;

import java.util.List;

public class FavoriteRepository {

    private static FavoriteRepository INSTANCE;

    private FeedDao feedDao;

    private LiveData<List<FeedItem>> allFavorites;

    private final FavoritesDatabase favoritesDatabase;


    public FavoriteRepository(final FavoritesDatabase favoritesDatabase) {
        this.favoritesDatabase = favoritesDatabase;
        feedDao = favoritesDatabase.favoriteDao();
        allFavorites = feedDao.getAllFavorites();
    }



    public LiveData<List<FeedItem>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(FeedItem feedItem) {
        new insertAsyncTask(feedDao).execute(feedItem);
    }

    public void delete(FeedItem feedItem) {
        new deleteAsyncTask(feedDao).execute(feedItem);
    }

    private static class insertAsyncTask extends AsyncTask<FeedItem, Void, Void> {

        private FeedDao asyncFavDao;

        insertAsyncTask(FeedDao dao) {
            asyncFavDao = dao;
        }

        @Override
        protected Void doInBackground(FeedItem... feedItems) {

            String apiBillTitle = asyncFavDao.getItemId(feedItems[0].getTitle());

            if (apiBillTitle == null) {
                feedItems[0].setFavorite(true);
                asyncFavDao.insertAsFavorite(feedItems[0]);
            } else {
                feedItems[0].setFavorite(false);
                asyncFavDao.deleteFavorite(feedItems[0]);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<FeedItem, Void, Void> {
        private FeedDao asyncFavDao;

        deleteAsyncTask(FeedDao dao) {
            asyncFavDao = dao;
        }

        @Override
        protected Void doInBackground(FeedItem... feedItems) {
            feedItems[0].setFavorite(false);
            asyncFavDao.deleteFavorite(feedItems[0]);
            return null;
        }
    }

    public static FavoriteRepository getInstance(final FavoritesDatabase favoritesDatabase) {
        if (INSTANCE == null) {
            synchronized (FavoriteRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteRepository(favoritesDatabase);
                }
            }
        }
        return INSTANCE;
    }
}
