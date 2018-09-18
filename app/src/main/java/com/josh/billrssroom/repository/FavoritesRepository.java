package com.josh.billrssroom.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.josh.billrssroom.db.FavoritesDatabase;
import com.josh.billrssroom.db.dao.BillDao;
import com.josh.billrssroom.model.BillModel;

import java.util.List;

public class FavoritesRepository {

    private BillDao billDao;
    private LiveData<List<BillModel>> allBills;

    public FavoritesRepository(Application application) {
        FavoritesDatabase db = FavoritesDatabase.getDatabase(application);
        billDao = db.billDao();
        allBills = billDao.loadAllFavorites();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<BillModel>> getAllBills() {
        return allBills;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insertSingleRecord(BillModel billModel) {
        new insertAsyncTask(billDao).execute(billModel);
    }

    public void deleteSingleFavorite(BillModel billModel) {
        new deleteAsyncTask(billDao).execute(billModel);
    }

    private static class insertAsyncTask extends AsyncTask<BillModel, Void, Void> {

        private BillDao asyncTaskDao;

        insertAsyncTask(BillDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(BillModel... billModels) {

            asyncTaskDao.insertSingleRecord(billModels[0]);

            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<BillModel, Void, Void> {

        private BillDao asyncTaskDao;

        deleteAsyncTask(BillDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(BillModel... billModels) {
            asyncTaskDao.deleteSingleRecord(billModels[0]);

            return null;
        }
    }
}