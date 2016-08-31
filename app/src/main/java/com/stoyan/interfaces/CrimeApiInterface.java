package com.stoyan.interfaces;

import com.stoyan.models.CrimeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by stoyan.dimitrov on 8/29/16.
 */

public interface CrimeApiInterface {
    String WHERE_DATE_QUERY_TAG = "$where";

    @Headers({
            "Accept: application/json",
    })
    /**
     * ?$where=date between '2016-08-01T00:00:00.000' and '2016-08-30T00:00:00.000'
     */
    @GET("/resource/cuks-n6tp.json")
    Call<List<CrimeApi>> getCrimeJson(@Query(WHERE_DATE_QUERY_TAG) String formattedInBetweenDates);
//    Call<CrimeListApi> getCrimeJson();
}
