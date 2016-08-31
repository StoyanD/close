package com.stoyan;

import android.app.Application;
import android.support.annotation.NonNull;

import com.stoyan.interfaces.CrimeApiInterface;
import com.stoyan.models.CrimeApi;

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


    public static synchronized
    @NonNull
    Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(CRIME_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    /**
     * "2010-01-02T00:00:00.000"
     *
     * @param listCallback
     * @param since
     */
    public static void getCrimeData(@NonNull Callback<List<CrimeApi>> listCallback, @NonNull String since) {
        CrimeApiInterface crimeApiInterface = getRetrofit().create(CrimeApiInterface.class);
        Call<List<CrimeApi>> crimeCall = crimeApiInterface.getCrimeJson(since);
        crimeCall.enqueue(listCallback);
    }
}
