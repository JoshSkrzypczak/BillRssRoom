package com.josh.billrssroom.model;

import android.os.Build;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.josh.billrssroom.utilities.Utils;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Root(name = "item", strict = false)
public class BillItem {

    @Element(name = "guid")
    private String guid;
    @Element(name = "pubDate")
    private String pubDate;
    @Element(name = "title")
    private String title;
    @Element(name = "description")
    private String description;
    @Element(name = "link")
    private String link;

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

    public String getFormattedDate(){
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

    public String getFormattedDescription(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(description).toString();
        }
    }
}