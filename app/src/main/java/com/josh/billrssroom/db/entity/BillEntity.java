package com.josh.billrssroom.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.josh.billrssroom.model.BillItem;

@Entity(tableName = "favorites")
public class BillEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String guid;

    public String puDate;

    public String title;

    public String description;

    public String link;

    public BillEntity(int id, String guid, String puDate, String title, String description, String link) {
        this.id = id;
        this.guid = guid;
        this.puDate = puDate;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    @Ignore
    public BillEntity(String guid, String puDate, String title, String description, String link) {
        this.guid = guid;
        this.puDate = puDate;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPuDate() {
        return puDate;
    }

    public void setPuDate(String puDate) {
        this.puDate = puDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
