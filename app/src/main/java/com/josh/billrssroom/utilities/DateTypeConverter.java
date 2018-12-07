package com.josh.billrssroom.utilities;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import androidx.room.TypeConverter;

public class DateTypeConverter {

    public static final String TAG = DateTypeConverter.class.getSimpleName();

    @TypeConverter
    public static Calendar fromStringDate(String value){
        if (value == null){
            return null;
        }

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z", Locale.getDefault());
        Date convertedDate = new Date();
        try {
            convertedDate = simpleDateFormat.parse(value);
        } catch (ParseException e){
            e.printStackTrace();
        }

        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(convertedDate.getTime()*1000);
        Log.d(TAG, "fromStringDate: " + cal);
        return cal;


    }

    @TypeConverter
    public static String toDate(Calendar calendar){
        if (calendar == null){
            return null;
        }

        Log.d(TAG, "toDate: " + calendar.getTimeInMillis()/1000);
        return "" + calendar.getTimeInMillis()/1000;


    }
}
