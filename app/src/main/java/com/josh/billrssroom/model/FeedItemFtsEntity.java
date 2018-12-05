package com.josh.billrssroom.model;

import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(tableName = "itemsFts")
@Fts4(contentEntity = FeedItem.class)
public class FeedItemFtsEntity {

    public String title;

    public String link;

    public String description;

    public String pubDate;

    public String guid;

    public boolean isFav;

    public FeedItemFtsEntity(String title, String link, String description, String pubDate, String guid) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
