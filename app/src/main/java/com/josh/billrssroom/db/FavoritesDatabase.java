package com.josh.billrssroom.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.db.dao.BillDao;
import com.josh.billrssroom.model.RssResult;

@Database(entities = {FeedItem.class}, version = 1)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static FavoritesDatabase INSTANCE = null;
    public static final String DATABASE_NAME = "favorites";
    public abstract BillDao billDao();

    public synchronized static FavoritesDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = create(context);
        }
        return INSTANCE;
    }

    private static FavoritesDatabase create(Context context){
        RoomDatabase.Builder<FavoritesDatabase> b =
                Room.databaseBuilder(context.getApplicationContext(), FavoritesDatabase.class,
                        DATABASE_NAME);

        return b.build();
    }
}