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
    LiveData<List<FeedItem>> loadFavorites();

    @Query("SELECT isFav FROM items WHERE title = :billTitle LIMIT 1")
    int getIntBoolean(String billTitle);

    @Query("SELECT * FROM items ORDER BY pubDate DESC")
    LiveData<List<FeedItem>> loadFeedDbItems();

    // True is 1
    @Query("UPDATE items SET isFav = 1 WHERE title = :billTitle")
    void updateAndSetItemToTrue(String billTitle);

    // False is 0
    @Query("UPDATE items SET isFav = 0 WHERE title = :billTitle")
    void updateAndSetItemToFalse(String billTitle);

    @Insert
    void insertItem(FeedItem feedItem);

    @Query("UPDATE items SET pubDate = :date, description = :description WHERE title = :billTitle")
    void updateItem(String date, String description, String billTitle);

    @Query("SELECT items.* FROM items JOIN itemsFts ON (items.title = itemsFts.title) WHERE items.isFav = 1"
            + " AND itemsFts MATCH :query")
    LiveData<List<FeedItem>> searchFavorites(String query);

    @Query("SELECT title FROM items WHERE title LIKE :billTitle")
    String getItemTitle(String billTitle);

    @Query("SELECT COUNT(title) FROM items WHERE isFav = 1")
    int getFavoriteCount();


    @Query("SELECT COUNT(title) FROM items")
    int getFeedCount();







//    @Insert (onConflict = OnConflictStrategy.REPLACE)
//    void insertData(List<FeedItem> item);
//
//    @Query("SELECT * FROM items WHERE isFav = 1")
//    List<FeedItem> loadMutableFavorites();
//
//    @Query("SELECT items.* FROM items JOIN itemsFts ON (items.title = itemsFts.title) "
//            + " AND itemsFts MATCH :query")
//    LiveData<List<FeedItem>> searchFeedItems(String query);
//
//    @Query("SELECT title FROM items WHERE title LIKE :billTitle || '%'")
//    String getItemLikeTitle(String billTitle);
//
//    @Query("SELECT description FROM items WHERE description = :billDescription LIMIT 1")
//    String getItemDescription(String billDescription);
//
//    @Query("SELECT title FROM items where title = :currentId")
//    LiveData<String> inDatabase(String currentId);
//
//    @Query("SELECT isFav FROM items WHERE title = :billTitle LIMIT 1")
//    boolean getItemBoolean(String billTitle);
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void insertAsFavorite(FeedItem feedItem);
//
//    @Update(onConflict = OnConflictStrategy.IGNORE)
//    void update(FeedItem feedItem);
//
//    @Delete
//    void deleteFavorite(FeedItem feedItem);
}
