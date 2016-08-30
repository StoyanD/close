package com.stoyan.unit;

import android.test.AndroidTestCase;

import com.stoyan.MapApp;
import com.stoyan.models.CrimeListApi;

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

        MapApp.getCrimeData(new Callback<CrimeListApi>() {
            @Override
            public void onResponse(Call<CrimeListApi> call, Response<CrimeListApi> response) {
                assertNotNull(call);
                assertNotNull(response);
                assertNotNull(response.body());
                assertNotNull(response.body().getCrimeApiList());
                assertTrue(response.body().getCrimeApiList().size() > 10);
                latch.countDown();
            }

            @Override
            public void onFailure(Call<CrimeListApi> call, Throwable t) {
                fail();
            }
        });

        //Check call succeeded
        assertTrue(latch.await(DELAY_MEDIUM, TimeUnit.MILLISECONDS));
    }
}
