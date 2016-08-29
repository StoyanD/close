package com.stoyan.interfaces;

import com.stoyan.models.CrimeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by stoyan.dimitrov on 8/29/16.
 */

public interface CrimeApiInterface {
    String DATE_QUERY_TAG = "date";

    @GET("/resource/ritf-b9ki.json")
    Call<List<CrimeApi>> getCrimeJson(@Query(DATE_QUERY_TAG) String date);
}
