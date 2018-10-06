package com.josh.billrssroom.db.dao;

import com.josh.billrssroom.model.FeedItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FeedDao {

    @Query("SELECT * FROM items WHERE isFav = 1")
    LiveData<List<FeedItem>> getFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAsFavorite(FeedItem feedItem);

    @Delete
    void deleteFavorite(FeedItem feedItem);

    @Query("SELECT title FROM items WHERE title = :billTitle LIMIT 1")
    String getItemId(String billTitle);

    @Query("SELECT isFav FROM items WHERE title = :billTitle LIMIT 1")
    boolean getItemBoolean(String billTitle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(List<FeedItem> item);

    @Query("SELECT * FROM items ORDER BY pubDate DESC")
    LiveData<List<FeedItem>> loadFeedDbItems();

    @Query("UPDATE items SET isFav = 1 WHERE title = :billTitle")
    void updateAndSetItemToTrue(String billTitle);

    @Query("UPDATE items SET isFav = 0 WHERE title = :billTitle")
    void updateAndSetItemToFalse(String billTitle);
}
