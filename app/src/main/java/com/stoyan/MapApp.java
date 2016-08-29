package com.stoyan;

import android.app.Application;

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
}
