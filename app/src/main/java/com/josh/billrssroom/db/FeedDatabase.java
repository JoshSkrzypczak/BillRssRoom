package com.josh.billrssroom.db;

import android.content.Context;

import com.josh.billrssroom.db.dao.ItemDao;
import com.josh.billrssroom.model.FeedItem;
import com.josh.billrssroom.utilities.DateTypeConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {FeedItem.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class FeedDatabase extends RoomDatabase {

    private static volatile FeedDatabase INSTANCE = null;
    private static final String DB_NAME = "dummy.db";
    public abstract ItemDao feedDao();

    public synchronized static FeedDatabase getFeedDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = create(context);
        }
        return INSTANCE;
    }

    private static FeedDatabase create(Context context){
        RoomDatabase.Builder<FeedDatabase> b =
                Room.databaseBuilder(context.getApplicationContext(), FeedDatabase.class,
                        DB_NAME);

        return b.build();
    }
}