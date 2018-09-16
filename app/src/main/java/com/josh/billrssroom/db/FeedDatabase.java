package com.josh.billrssroom.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.josh.billrssroom.AppExecutors;
import com.josh.billrssroom.db.dao.BillDao;
import com.josh.billrssroom.model.BillItem;

@Database(entities = {BillItem.class}, version = 1)
public abstract class FeedDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "feed-database";

    public abstract BillDao billDao();

    private static FeedDatabase INSTANCE;

    public static FeedDatabase getDatabase(final Context context, AppExecutors appExecutors){
        if (INSTANCE == null){
            synchronized (FeedDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FeedDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final BillDao dao;

        public PopulateDbAsync(FeedDatabase db) {
            dao = db.billDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {


            return null;
        }
    }
}
