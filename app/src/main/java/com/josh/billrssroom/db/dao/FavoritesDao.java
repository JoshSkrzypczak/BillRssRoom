package com.josh.billrssroom.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.josh.billrssroom.model.FeedItem;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<FeedItem>> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAsFavorite(FeedItem feedItems);

    @Update
    void updateFavorites(FeedItem feedItem);

    @Delete
    void deleteFavorite(FeedItem feedItems);

    @Query("SELECT * FROM favorites WHERE title = :billTitle LIMIT 1")
    FeedItem checkIfFavoriteExistsByTitle(String billTitle);

    @Query("SELECT title FROM favorites WHERE title = :billTitle LIMIT 1")
    String getItemId(String billTitle);



}
