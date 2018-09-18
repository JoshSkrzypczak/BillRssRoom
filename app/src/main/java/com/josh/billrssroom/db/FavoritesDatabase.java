package com.josh.billrssroom.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.josh.billrssroom.db.dao.BillDao;
import com.josh.billrssroom.model.BillModel;

@Database(entities = {BillModel.class}, version = 1)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static FavoritesDatabase INSTANCE;

    public static final String DATABASE_NAME = "basic-sample-db";

    public abstract BillDao billDao();

    public static FavoritesDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (FavoritesDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoritesDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
//            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BillDao mDao;

        PopulateDbAsync(FavoritesDatabase db) {
            mDao = db.billDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();

            return null;
        }
    }
}