package com.josh.billrssroom.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.josh.billrssroom.AppExecutors;
import com.josh.billrssroom.db.dao.BillDao;
import com.josh.billrssroom.db.entity.BillEntity;

@Database(entities = {BillEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static final String DATABASE_NAME = "basic-sample-db";

    public abstract BillDao billDao();

    public final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = buildDatabase(context.getApplicationContext(), executors);
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }

        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors){
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() ->{

                        });
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context){
        if (context.getDatabasePath(DATABASE_NAME).exists()){
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated(){
        return isDatabaseCreated;
    }
}
