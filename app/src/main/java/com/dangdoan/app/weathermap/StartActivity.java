package com.dangdoan.app.weathermap;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.dangdoan.app.weathermap.loader.JsonVolleyLoader;
import com.dangdoan.app.weathermap.loader.VolleyLoader;
import com.dangdoan.app.weathermap.loader.VolleyLoaderData;
import com.dangdoan.app.weathermap.webservices.GetFiveDaysForecastRequest;
import com.dangdoan.app.weathermap.webservices.WeatherMapHttpRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

import static com.dangdoan.app.weathermap.Utils.Constant.KEY_PARAM_LAT;
import static com.dangdoan.app.weathermap.Utils.Constant.KEY_PARAM_LON;
import static com.dangdoan.app.weathermap.Utils.Constant.REQUEST_CODE_ZOOM_INTO_CURRENT_LOCATION;
import static com.dangdoan.app.weathermap.Utils.Constant.URL_FORECAST;

public class StartActivity extends AppCompatActivity{
    private static final String TAG = StartActivity.class.getSimpleName();
    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLocation();
    }

    private void initLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "onConnected no permissions");
            checkPermission(REQUEST_CODE_ZOOM_INTO_CURRENT_LOCATION);
            return;
        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient
                (this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            mCurrentLocation = location;
                            Log.v(TAG, "***--- My location: " + location.getLatitude() + "/" + location.getLongitude
                                    ());
                            loadFiveDaysForecastData();
                        }
                    }
                });
    }

    public void checkPermission(int reqCode) {
        Log.v(TAG, "checkPermission");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is not granted");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // TODO:
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.v(TAG, "Should show the explanation of the needs of the permission");
                buildAlertMessageNoGps(reqCode);
            } else {
                // No explanation needed, we can request the permission.
                Log.v(TAG, "Ignore explanation, go ahead and request the permission");
                requestLocationPermission(reqCode);
            }
        }
    }

    private void buildAlertMessageNoGps(final int reqCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Log.v(TAG, "Request for permission");
                        requestLocationPermission(reqCode);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void requestLocationPermission(int tag){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                tag);
    }

    private void loadFiveDaysForecastData(){
        if(mCurrentLocation == null){
            return;
        }
        final GetFiveDaysForecastRequest request = new GetFiveDaysForecastRequest(URL_FORECAST, getRequestParams
                (mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
        JsonVolleyLoader loader = new JsonVolleyLoader(this, request, "", new VolleyLoader.LoaderListener() {
            @Override
            public void onResponse(VolleyLoaderData volleyLoaderData) {
                Log.v(TAG, "onResponse");
            }
        }, new VolleyLoader.LoaderErrorListener() {
            @Override
            public void onResponseError(VolleyError error) {
                Log.v(TAG, "onResponseError");
            }
        });
        loader.loadJsonObjectData();
    }

    private Map<String, String> getRequestParams(double lat, double lon){
        Map<String, String> params = new HashMap<>();
        params.put(KEY_PARAM_LAT, String.valueOf(lat));
        params.put(KEY_PARAM_LON, String.valueOf(lon));
        return params;
    }
}
