package com.josh.billrssroom.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//@Database(entities = {FavoriteEntity.class}, version = 1)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static FavoritesDatabase INSTANCE = null;
    private static final String DATABASE_NAME = "favorites";
//    public abstract FavoriteDao favoriteDao();

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