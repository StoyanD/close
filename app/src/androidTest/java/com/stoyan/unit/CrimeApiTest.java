package com.stoyan.unit;

import android.test.AndroidTestCase;

import com.stoyan.MapApp;
import com.stoyan.MapsActivity;
import com.stoyan.models.CrimeApi;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by stoyan.dimitrov on 8/29/16.
 */

public class CrimeApiTest extends AndroidTestCase {

    private static final long DELAY_MEDIUM = 5000;

    public void testApiCall() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        MapApp.getCrimeData(new Callback<List<CrimeApi>>() {
            @Override
            public void onResponse(Call<List<CrimeApi>> call, Response<List<CrimeApi>> response) {
                assertNotNull(call);
                assertNotNull(response);
                assertNotNull(response.body());
                assertTrue(response.body().size() > 1000);
                latch.countDown();
            }

            @Override
            public void onFailure(Call<List<CrimeApi>> call, Throwable t) {
                fail();
            }
        }, MapsActivity.getQueryDateParams(1));

        //Check call succeeded
        assertTrue(latch.await(DELAY_MEDIUM, TimeUnit.MILLISECONDS));
    }
}
