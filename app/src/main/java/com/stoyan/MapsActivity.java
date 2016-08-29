package com.stoyan;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stoyan.databinding.ActivityMapsBinding;
import com.stoyan.models.CrimeListApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Callback<CrimeListApi> {
    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;

    ActivityMapsBinding mBinding;
    SupportMapFragment mMapFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        setActionBar(mBinding.toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mMapFrag.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(37.781833, -122.416316);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        MapApp.getCrimeData(this);
    }

    @Override
    public void onResponse(Call<CrimeListApi> call, Response<CrimeListApi> response) {
        if(response != null){
            Log.i(TAG, "Response size : " + response.body().getCrimeApiList().size());
        }
    }

    @Override
    public void onFailure(Call<CrimeListApi> call, Throwable t) {

    }
}
