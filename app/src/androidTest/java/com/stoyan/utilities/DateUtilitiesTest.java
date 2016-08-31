package com.stoyan.utilities;

import android.test.AndroidTestCase;

import java.util.Date;

/**
 * Created by stoyan.dimitrov on 8/30/16.
 */
public class DateUtilitiesTest extends AndroidTestCase{

    public void testDateUtilitiesTest(){
        Date date = new Date(1472519977000L);//2016-08-30T01:19:37.000
        String formatted = DateUtilities.dateToStringIso8601(date);

        assertNotNull(formatted);
        assertEquals("2016-08-30T01:19:37.000", formatted);
    }

}