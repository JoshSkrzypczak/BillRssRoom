package com.josh.billrssroom.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.josh.billrssroom.db.BillDatabase;
import com.josh.billrssroom.db.dao.FavoritesDao;
import com.josh.billrssroom.model.FeedItem;

import java.util.List;

public class FavRepository {
    private FavoritesDao favoritesDao;
    private LiveData<List<FeedItem>> allFavorites;


    public FavRepository(Application application) {
        BillDatabase db = BillDatabase.getFeedDatabase(application);
        favoritesDao = db.billDao();
        allFavorites = favoritesDao.getAllFavorites();
    }

    public LiveData<List<FeedItem>> getAllFavorites() {
        return allFavorites;
    }

    public void insert(FeedItem feedItem) {
        new insertAsyncTask(favoritesDao).execute(feedItem);
    }

    public void delete(FeedItem feedItem) {
        new deleteAsyncTask(favoritesDao).execute(feedItem);
    }

    private static class insertAsyncTask extends AsyncTask<FeedItem, Void, Void> {

        private FavoritesDao asyncFavDao;

        insertAsyncTask(FavoritesDao dao) {
            asyncFavDao = dao;
        }

        @Override
        protected Void doInBackground(FeedItem... feedItems) {

            String apiBillTitle = asyncFavDao.getItemId(feedItems[0].getTitle());

            if (apiBillTitle == null) {
                asyncFavDao.insertAsFavorite(feedItems[0]);
            } else {
                asyncFavDao.deleteFavorite(feedItems[0]);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<FeedItem, Void, Void> {
        private FavoritesDao asyncFavDao;

        deleteAsyncTask(FavoritesDao dao){
            asyncFavDao = dao;
        }

        @Override
        protected Void doInBackground(FeedItem... feedItems) {
            asyncFavDao.deleteFavorite(feedItems[0]);
            return null;
        }
    }
}
