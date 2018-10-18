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
public interface ItemDao {

    @Query("UPDATE items SET isFav = 0")
    void deleteAllFavorites();

    @Query("SELECT * FROM items WHERE isFav = 1")
    LiveData<List<FeedItem>> getFavorites();

    // If insert method only receives 1 param, it can return a long, which is the new rowId for the inserted item.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAsFavorite(FeedItem feedItem);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(FeedItem feedItem);

    @Delete
    void deleteFavorite(FeedItem feedItem);

    @Query("SELECT title FROM items WHERE title = :billTitle LIMIT 1")
    String getItemId(String billTitle);

    @Query("SELECT title FROM items where title = :currentId")
    LiveData<String> inDatabase(String currentId);

    @Query("SELECT isFav FROM items WHERE title = :billTitle LIMIT 1")
    boolean getItemBoolean(String billTitle);

    @Query("SELECT isFav FROM items WHERE title = :billTitle LIMIT 1")
    int getIntBoolean(String billTitle);

    // TODO: 10/5/2018 Use Replace or Ignore?
    /**
     * By using REPLACE, when fetching from the api, an item with same title as another item
     * already in the list, it replaces that original at the old position instead of at the top
     *
     * Possible Options:
     * 1) Use IGNORE
     * 2) Instead of using bill title as the primary key, autogenerate primary key to an int.
     * @param item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(List<FeedItem> item);

    @Query("SELECT * FROM items ORDER BY pubDate DESC")
    LiveData<List<FeedItem>> loadFeedDbItems();

    // True is 1
    @Query("UPDATE items SET isFav = 1 WHERE title = :billTitle")
    void updateAndSetItemToTrue(String billTitle);

    // False is 0
    @Query("UPDATE items SET isFav = 0 WHERE title = :billTitle")
    void updateAndSetItemToFalse(String billTitle);

}
