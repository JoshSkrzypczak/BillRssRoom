package com.josh.billrssroom.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.josh.billrssroom.db.dao.FeedDao;
import com.josh.billrssroom.model.FeedItem;

@Database(entities = {FeedItem.class}, version = 1)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static FavoritesDatabase INSTANCE = null;
    // TODO: 9/26/2018 Consider using single database across app
    // See https://stackoverflow.com/questions/48279481/multiple-tables-with-same-type-of-objects-in-room-database
    private static final String DATABASE_NAME = "favorites.db";
    public abstract FeedDao favoriteDao();

    public synchronized static FavoritesDatabase getFavoriteDatabase(Context context){
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