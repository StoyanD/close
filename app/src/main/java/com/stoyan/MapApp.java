package com.stoyan;

import android.app.Application;

import com.stoyan.interfaces.CrimeApiInterface;
import com.stoyan.models.CrimeApi;
import com.stoyan.models.CrimeListApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by stoyan.dimitrov on 8/29/16.
 */

public class MapApp extends Application {
    private static Retrofit mRetrofit;

    private static final String CRIME_BASE_URL = "https://data.sfgov.org";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static synchronized Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(CRIME_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public static void getCrimeData(Callback<CrimeListApi> listCallback){
        CrimeApiInterface crimeApiInterface = getRetrofit().create(CrimeApiInterface.class);

        Call<CrimeListApi> crimeCall = crimeApiInterface.getCrimeJson("10");
        crimeCall.enqueue(listCallback);
    }
}
