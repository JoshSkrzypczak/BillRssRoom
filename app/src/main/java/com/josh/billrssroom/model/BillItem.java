package com.josh.billrssroom.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class BillItem {

    @Element(name = "guid")
    public String guid;
    @Element(name = "pubDate")
    public String pubDate;
    @Element(name = "title")
    public String title;
    @Element(name = "description")
    public String description;
    @Element(name = "link")
    public String link;

    public BillItem(@Element(name = "guid") String guid,
                    @Element(name = "pubDate") String pubDate,
                    @Element(name = "title") String title,
                    @Element(name = "description") String description,
                    @Element(name = "link") String link){
        this.guid = guid;
        this.pubDate = pubDate;
        this.title = title;
        this.description = description;
        this.link = link;
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
}