package com.josh.billrssroom.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class FeedChannel {

    @ElementList(name = "item", inline = true)
    public List<FeedItem> items;

    @Ignore
    @Element(required = false)
    public String pubDate;
    @Ignore
    @Element(required = false)
    public String title;
    @Ignore
    @Element(required = false)
    public String managingEditor;
    @Ignore
    @Element(required = false)
    public String description;
    @Ignore
    @Element(required = false)
    public String docs;
    @Ignore
    @Element(required = false)
    public String link;
    @Ignore
    @Element(required = false)
    public String lastBuildDate;
    @Ignore
    @Element(required = false)
    public String generator;
    @Ignore
    @Element(required = false)
    public String language;
    @Ignore
    @Element(required = false)
    public String webMaster;



    public List<FeedItem> getItems() {
        return items;
    }

    public void setItems(List<FeedItem> items) {
        this.items = items;
    }
}