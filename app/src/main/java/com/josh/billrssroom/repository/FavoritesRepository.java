package com.josh.billrssroom.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.db.FavoritesDatabase;
import com.josh.billrssroom.db.dao.BillDao;

import java.util.List;

public class FavoritesRepository {

    private BillDao billDao;
    private LiveData<List<FeedItem>> items;

    public FavoritesRepository(Application application) {
        FavoritesDatabase db = FavoritesDatabase.getDatabase(application);
        billDao = db.billDao();
//        items = billDao.loadItems();
    }

    public LiveData<List<FeedItem>> getAllBills() {
        return items;
    }

//    public void insertSingleRecord(FeedItem item) {
//        new insertAsyncTask(billDao).execute(item);
//    }
//
//    public void deleteSingleFavorite(FeedItem billModel) {
//        new deleteAsyncTask(billDao).execute(billModel);
//    }

//    private static class insertAsyncTask extends AsyncTask<FeedItem, Void, Void> {
//
//        private BillDao asyncTaskDao;
//
//        insertAsyncTask(BillDao dao) {
//            asyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(FeedItem... items) {
//
//            asyncTaskDao.insert(items[0]);
//
//            return null;
//        }
//    }

//    private static class deleteAsyncTask extends AsyncTask<FeedItem, Void, Void> {
//
//        private BillDao asyncTaskDao;
//
//        deleteAsyncTask(BillDao dao) {
//            asyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(FeedItem... items) {
//            asyncTaskDao.delete(items[0]);
//
//            return null;
//        }
//    }
}