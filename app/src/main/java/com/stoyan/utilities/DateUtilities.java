package com.stoyan.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by stoyan.dimitrov on 8/30/16.
 */

public final class DateUtilities {

    /**
     * Year-Month-Day Hour:Minutes:Seconds pattern: 2015-01-28T11:25:59.000
     */
    public static final String DATE_ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * @param date
     */
    public static String dateToStringIso8601(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(DATE_ISO_8601_FORMAT, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }
}
