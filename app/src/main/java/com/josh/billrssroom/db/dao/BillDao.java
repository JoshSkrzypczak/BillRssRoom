package com.josh.billrssroom.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.josh.billrssroom.db.entity.BillEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface BillDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<BillEntity>> loadAllFavorites();


    @Query("select * from favorites where title like :billTitle OR description like :billDescription")
    LiveData<List<BillEntity>> findBillByTitleOrDescription(String billTitle, String billDescription);

    @Insert(onConflict = IGNORE)
    void insertBillAsFavorite(BillEntity favoritesEntity);

    @Delete
    void deleteBill(BillEntity billEntity);
}
