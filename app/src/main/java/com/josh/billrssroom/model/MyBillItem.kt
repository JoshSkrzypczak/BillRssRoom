package com.josh.billrssroom.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Build
import android.support.annotation.NonNull
import android.text.Html
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Root(name = "item", strict = false)
@Entity(tableName = "items")
data class MyBillItem(
    @ColumnInfo(name = "guid") @Element(name = "guid") val guid: String,
    @ColumnInfo(name = "pubDate") @Element(name = "pubDate") val pubDate: String,
    @PrimaryKey @NonNull @ColumnInfo(name = "title") @Element(name = "title") val title: String,
    @ColumnInfo(name = "description") @Element(name = "description") val description: String,
    @ColumnInfo(name = "link") @Element(name = "link") val link: String,
    @ColumnInfo(name = "isFavorite") @Element(required = false) val isFavorite: Boolean
) {
    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("EST")

        var parsedDate: Date? = null
        try {
            parsedDate = formatter.parse(pubDate)
        } catch (e: ParseException){
            e.printStackTrace()
        }

        val timeZone = TimeZone.getTimeZone("America/Detroit")
        val destinationFormat = SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a", Locale.getDefault())
        destinationFormat.timeZone = timeZone

        return destinationFormat.format(parsedDate)
    }

    fun getFormattedDescription(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            return Html.fromHtml(description).toString()
        }
    }
}

