package com.josh.billrssroom.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import org.simpleframework.xml.Element

@Entity(tableName = "favorites")
data class FavoritesEntity(
    @ColumnInfo(name = "guid") @Element(name = "guid") val guid: String,
    @ColumnInfo(name = "pubDate") @Element(name = "pubDate") val pubDate: String,
    @PrimaryKey @NonNull @ColumnInfo(name = "title") @Element(name = "title") val title: String,
    @ColumnInfo(name = "description") @Element(name = "description") val description: String,
    @ColumnInfo(name = "link") @Element(name = "link") val link: String,
    @ColumnInfo(name = "isFavorite") @Element(required = false) val isFavorite: Boolean
)