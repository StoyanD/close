package com.stoyan.models;

import com.google.gson.annotations.SerializedName;

/**
 * {
 * "date" : "2015-12-30T00:00:00",
 * "address" : "100 Block of BARTLETT ST",
 * "resolution" : "NONE",
 * "pddistrict" : "MISSION",
 * "incidntnum" : "151124812",
 * "x" : "-122.419711251166",
 * "dayofweek" : "Wednesday",
 * "y" : "37.7545565620279",
 * "location" : {
 * "latitude" : "37.7545565620279",
 * "human_address" : "{\"address\":\"\",\"city\":\"\",\"state\":\"\",\"zip\":\"\"}",
 * "needs_recoding" : false,
 * "longitude" : "-122.419711251166"
 * },
 * "time" : "23:45",
 * "pdid" : "15112481251040",
 * "category" : "NON-CRIMINAL",
 * "descript" : "AIDED CASE"
 * }
 * Created by stoyan.dimitrov on 8/29/16.
 */

public class CrimeApi {
    private static final String LONGITUDE_KEY = "x";
    private static final String LATITUDE_KEY = "y";

    @SerializedName(LONGITUDE_KEY)
    private Long longitude;
    @SerializedName(LATITUDE_KEY)
    private Long latitude;


    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }
}
