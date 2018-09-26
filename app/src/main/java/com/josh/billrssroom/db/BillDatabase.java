package com.josh.billrssroom.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.josh.billrssroom.db.dao.FeedDao;
import com.josh.billrssroom.model.FeedItem;

@Database(entities = {FeedItem.class}, version = 1)
public abstract class BillDatabase extends RoomDatabase {

    private static volatile BillDatabase INSTANCE = null;
    private static final String DB_NAME = "feed.db";
    public abstract FeedDao billDao();

    public synchronized static BillDatabase getFeedDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = create(context);
        }
        return INSTANCE;
    }

    private static BillDatabase create(Context context){
        RoomDatabase.Builder<BillDatabase> b =
                Room.databaseBuilder(context.getApplicationContext(), BillDatabase.class,
                        DB_NAME);
        
        return b.build();
    }
}
