package com.josh.billrssroom.model;

import android.os.Build;
import android.text.Html;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Root(name = "item", strict = false)
@Entity(tableName = "items")
public class FeedItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    @Element(name = "title")
    public String title;
    @ColumnInfo(name = "link")
    @Element(name = "link")
    public String link;
    @ColumnInfo(name = "description")
    @Element(name = "description")
    public String description;
    @ColumnInfo(name = "pubDate")
    @Element(name = "pubDate")
    public String pubDate;
    @ColumnInfo(name = "guid")
    @Element(name = "guid")
    public String guid;
    @ColumnInfo(name = "isFav")
    @Element(required = false)
    public boolean isFavorite;

        public FeedItem(
                @NonNull @Element(name = "title") String title,
                @Element(name = "link") String link,
                @Element(name = "description") String description,
                @Element(name = "pubDate") String pubDate,
                @Element(name = "guid") String guid) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.guid = guid;
}

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getFormattedDate() {
        SimpleDateFormat formatter =
                new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("EST"));

        Date parsedDated = null;
        try {
            parsedDated = formatter.parse(pubDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TimeZone tz = TimeZone.getTimeZone("America/Detroit");
        SimpleDateFormat destinationFormat =
                new SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a", Locale.getDefault());
        destinationFormat.setTimeZone(tz);

        return destinationFormat.format(parsedDated);
    }

    public String getFormattedDescription() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(description).toString();
        }
    }
}