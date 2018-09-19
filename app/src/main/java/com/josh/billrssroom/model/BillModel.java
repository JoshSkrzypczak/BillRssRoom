package com.josh.billrssroom.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


@Root(name = "item", strict = false)
@Entity(tableName = "items", indices = {@Index(value = {"title", "description"})})
public class BillModel {

    @ColumnInfo(name = "guid")
    @Element(name = "guid")
    private String guid;
    @ColumnInfo(name = "pubDate")
    @Element(name = "pubDate")
    private String pubDate;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    @Element(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    @Element(name = "description")
    private String description;
    @ColumnInfo(name = "link")
    @Element(name = "link")
    private String link;
    @Element(name = "isFavorite", required = false)
    private Boolean isFavorite;


    public BillModel(@Element(name = "guid") String guid,
                    @Element(name = "pubDate") String pubDate,
                    @Element(name = "title") String title,
                    @Element(name = "description") String description,
                    @Element(name = "link") String link,
                    @Element(name = "isFavorite", required = false) Boolean isFavorite) {
        this.guid = guid;
        this.pubDate = pubDate;
        this.title = title;
        this.description = description;
        this.link = link;
        this.isFavorite = isFavorite;
    }

    public String getGuid() {
        return guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
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