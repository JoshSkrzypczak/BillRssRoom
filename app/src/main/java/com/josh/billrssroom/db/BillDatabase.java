package com.josh.billrssroom.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.josh.billrssroom.db.dao.BillDao;
import com.josh.billrssroom.model.BillModel;

@Database(entities = {BillModel.class}, version = 1)
public abstract class BillDatabase extends RoomDatabase {
    public abstract BillDao billDao();

    private static final String DB_NAME = "feed.db";
    private static volatile BillDatabase INSTANCE = null;

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
