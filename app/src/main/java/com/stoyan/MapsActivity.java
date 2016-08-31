package com.stoyan;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.stoyan.databinding.ActivityMapsBinding;
import com.stoyan.models.CrimeApi;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.stoyan.utilities.DateUtilities.dateToStringIso8601;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Callback<List<CrimeApi>> {
    private static final String TAG = "MapsActivity";

    public static final Map<String, LatLng> DistrictMap;

    static {
        HashMap<String, LatLng> aMap = new HashMap<>();
        aMap.put("CENTRAL", new LatLng(37.799999999999113, -122.409862909291093));
        aMap.put("SOUTHERN", new LatLng(37.774988899469427, -122.40432830154468));
        aMap.put("BAYVIEW", new LatLng(37.729641718322, -122.398086554936313));
        aMap.put("MISSION", new LatLng(37.762680702642363, -122.421969665401335));
        aMap.put("NORTHERN", new LatLng(37.780161140378134, -122.432390435178931));
        aMap.put("PARK", new LatLng(37.767848624283403, -122.475266597187119));
        aMap.put("RICHMOND", new LatLng(37.780001292570731, -122.464460728646259));
        aMap.put("INGLESIDE", new LatLng(37.724770134356135, -122.446251045020546));
        aMap.put("TARAVAL", new LatLng(37.743733794624994, -122.5074808671522));
        aMap.put("TENDERLOIN", new LatLng(37.783704058130724, -122.412895752224273));
        DistrictMap = Collections.unmodifiableMap(aMap);
    }

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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sf = new LatLng(37.781833, -122.416316);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sf, 11.0f));
        // Add a marker in Sydney and move the camera
        MapApp.getCrimeData(this, getQueryDateParams(1));
    }

    @Override
    public void onResponse(Call<List<CrimeApi>> call, Response<List<CrimeApi>> response) {
        if (response != null && response.body() != null) {
            Log.i(TAG, "Response size : " + response.body().size());
            mapCrimePoints(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<CrimeApi>> call, Throwable t) {
        if (call != null) {
            Log.i(TAG, "call : " + call.request());
        }
    }


    private void mapCrimePoints(@NonNull List<CrimeApi> crimeArray) {
        int[] colors = getResources().getIntArray(R.array.crime_district_colors);

        final Map<String, Integer> counter = new HashMap<String, Integer>() {
            @Override
            public Integer put(String key, Integer value) {
                if (!super.containsKey(key)) {
                    return super.put(key, 1);
                }
                return super.put(key, super.get(key) + 1);
            }
        };

        for (CrimeApi api : crimeArray) {
            if (api == null) {
                continue;
            }
            counter.put(api.getDistrict(), 1);
        }

        Map<String, Integer> sorted = sortByValue(counter);
        int i = 0;
        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
            if (i > 6) {
                entry.setValue(colors[7]);
                i++;
                continue;
            }
            entry.setValue(colors[i]);
            i++;
        }

        for (Map.Entry<String, LatLng> entry : DistrictMap.entrySet()) {
            MarkerOptions marker = new MarkerOptions().position(entry.getValue());
            marker.title(entry.getKey());
            marker.icon(getMarkerIcon(sorted.get(entry.getKey())));
            mMap.addMarker(marker);
        }
    }

    public static String getQueryDateParams(int monthsBetween) {
        //Return today's date formatted
        if (monthsBetween < 1) {
            return dateToStringIso8601(new Date());
        }

        String prefix = "date between '";
        String mid = "' and '";
        String suffix = "'";

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1 * monthsBetween);
        Date monthsAgo = cal.getTime();
        return prefix + dateToStringIso8601(monthsAgo) + mid + dateToStringIso8601(now) + suffix;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
//                return (e1.getValue()).compareTo(e2.getValue());
                return (e2.getValue()).compareTo(e1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    // method definition
    public BitmapDescriptor getMarkerIcon(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
