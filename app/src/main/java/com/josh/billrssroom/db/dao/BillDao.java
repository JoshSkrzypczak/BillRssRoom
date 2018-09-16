package com.josh.billrssroom.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.josh.billrssroom.model.BillItem;
import com.josh.billrssroom.model.Rss;

import java.util.List;

@Dao
public interface BillDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<BillItem>> loadAllFavorites();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleRecord(BillItem billEntity);

    @Delete
    void deleteSingleRecord(BillItem billItem);

    @Query("DELETE FROM favorites")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM favorites")
    int getCount();

    @Update
    void updateSingleRecord(BillItem...billItems);

    @Query("SELECT * FROM favorites LIMIT 1")
    BillItem[] getAnyBillItem();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFeedItems(List<BillItem> billItems);

    @Query("SELECT * FROM favorites")
    LiveData<List<BillItem>> loadBillItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<BillItem> billItems);







}
