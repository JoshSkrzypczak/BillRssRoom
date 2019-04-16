package com.josh.billrssroom.db;

import com.josh.billrssroom.model.FeedItem;

import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(tableName = "itemsFts")
@Fts4(contentEntity = FeedItem.class)
public class FeedItemFtsEntity {

    private String title;
    private String description;
    private String pubDate;


    public FeedItemFtsEntity(String title, String description, String pubDate) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }
}
