package com.josh.billrssroom.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.josh.billrssroom.model.FeedItem;

import java.util.List;

@Dao
public interface BillDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FeedItem... items);

    @Query("SELECT * FROM items")
    LiveData<List<FeedItem>> loadItems();

//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertItems(List<FeedItem> items);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertSingleRecord(FeedItem item);

//
//    @Update
//    void update(FeedItem... items);
//
//    @Delete
//    void delete(FeedItem... items);
//
//    @Delete
//    void deleteSingleRecord(FeedItem item);
//

//
//    @Query("SELECT * FROM rssResult")
//    LiveData<List<FeedItem>> loadAllFavorites();

}
