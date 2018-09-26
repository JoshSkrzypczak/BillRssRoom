package com.josh.billrssroom.utilities;

import android.content.Context;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.josh.billrssroom.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Utils {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();

    public static void openCustomTab(Context context, String url){
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.color_accent));

        intentBuilder.setStartAnimations(context, R.anim.tabs_slide_in_right,
                R.anim.tabs_slide_out_left);
        intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = intentBuilder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

    public static String formatDate(final Date date){
        return DATE_FORMAT.format(date);
    }

    public static Date parseDate(final String dateString){
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e){
            return null;
        }
    }
}
