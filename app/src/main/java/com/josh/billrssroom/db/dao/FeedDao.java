package com.josh.billrssroom.db.dao;

import com.josh.billrssroom.model.FeedItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FeedDao {

    @Query("SELECT * FROM items")
    LiveData<List<FeedItem>> getAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAsFavorite(FeedItem feedItem);

    @Update
    void updateFavorites(FeedItem feedItem);

    @Delete
    void deleteFavorite(FeedItem feedItem);

    @Query("SELECT * FROM items WHERE title = :billTitle LIMIT 1")
    FeedItem checkIfFavoriteExistsByTitle(String billTitle);

    @Query("SELECT title FROM items WHERE title = :billTitle LIMIT 1")
    String getItemId(String billTitle);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(List<FeedItem> item);

    @Query("SELECT * FROM items")
    LiveData<List<FeedItem>> loadItems();

    @Query("SELECT * FROM items WHERE isFav = 1 ORDER BY pubDate")
    LiveData<List<FeedItem>> getFavorites();



}
