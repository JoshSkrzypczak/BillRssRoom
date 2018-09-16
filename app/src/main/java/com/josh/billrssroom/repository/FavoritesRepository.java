package com.josh.billrssroom.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.josh.billrssroom.db.FavoritesDatabase;
import com.josh.billrssroom.db.dao.BillDao;
import com.josh.billrssroom.model.BillItem;

import java.util.List;

public class FavoritesRepository {

    private BillDao billDao;
    private LiveData<List<BillItem>> allBills;

    public FavoritesRepository(Application application){
        FavoritesDatabase db = FavoritesDatabase.getDatabase(application);
        billDao = db.billDao();
        allBills = billDao.loadAllFavorites();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<BillItem>> getAllBills() {
        return allBills;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insertSingleRecord(BillItem billItem){
        new insertAsyncTask(billDao).execute(billItem);
    }

    public void deleteSingleFavorite(BillItem billItem){
        new deleteAsyncTask(billDao).execute(billItem);
    }

    private static class insertAsyncTask extends AsyncTask<BillItem, Void, Void>{

        private BillDao asyncTaskDao;

        insertAsyncTask(BillDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(BillItem... billItems) {
            asyncTaskDao.insertSingleRecord(billItems[0]);

            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<BillItem, Void, Void>{

        private BillDao asyncTaskDao;

        deleteAsyncTask(BillDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(BillItem... billItems) {
            asyncTaskDao.deleteSingleRecord(billItems[0]);

            return null;
        }
    }
}
