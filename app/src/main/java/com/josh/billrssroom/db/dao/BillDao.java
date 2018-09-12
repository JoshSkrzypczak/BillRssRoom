package com.josh.billrssroom.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.josh.billrssroom.db.entity.BillEntity;
import com.josh.billrssroom.model.BillItem;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface BillDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<BillItem>> loadAllFavorites();


    @Query("select * from favorites where title like :billTitle OR description like :billDescription")
    LiveData<List<BillItem>> findBillByTitleOrDescription(String billTitle, String billDescription);

    @Insert(onConflict = IGNORE)
    void insertBillAsFavorite(BillItem favoritesEntity);

    @Delete
    void deleteBill(BillItem billEntity);

    @Query("DELETE FROM favorites")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BillItem billEntity);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BillItem> billItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(BillItem billItem);

    @Update
    void update(BillItem billItem);

//    @Query("SELECT id FROM favorites WHERE id = :id LIMIT 1")
//    void getItemId(String id);
}
