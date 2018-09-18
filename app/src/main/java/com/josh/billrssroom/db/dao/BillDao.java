package com.josh.billrssroom.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.josh.billrssroom.model.BillModel;

import java.util.List;

@Dao
public interface BillDao {

    @Query("SELECT * FROM items")
    LiveData<List<BillModel>> loadBillItems();

    @Insert
    void insert(BillModel...models);

    @Update
    void update(BillModel...models);

    @Delete
    void delete(BillModel...models);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleRecord(BillModel model);

    @Delete
    void deleteSingleRecord(BillModel model);

    @Query("DELETE FROM items")
    void deleteAll();

    @Query("SELECT * FROM items")
    LiveData<List<BillModel>> loadAllFavorites();


}
