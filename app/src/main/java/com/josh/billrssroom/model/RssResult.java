package com.josh.billrssroom.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class RssResult {

    @Attribute(required = false)
    public String version;

    @Element(name = "channel")
    public FeedChannel channel;

    public FeedChannel getChannel() {
        return channel;
    }

    public void setChannel(FeedChannel channel) {
        this.channel = channel;
    }
}