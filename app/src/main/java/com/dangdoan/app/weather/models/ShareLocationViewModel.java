package com.dangdoan.app.weather.models;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import static com.dangdoan.app.weather.utils.Constant.REQUEST_CODE_ZOOM_INTO_CURRENT_LOCATION;

public class ShareLocationViewModel extends ViewModel {
    private static final String TAG = ShareLocationViewModel.class.getSimpleName();
    private MutableLiveData<Location> mLocation;

    public LiveData<Location> getLocation(Activity activity) {
        if (mLocation == null) {
            mLocation = new MutableLiveData<>();
            loadLocation(activity);
        }
        return mLocation;
    }


    private void loadLocation(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "onConnected no permissions");
            checkPermission(REQUEST_CODE_ZOOM_INTO_CURRENT_LOCATION, activity);
            return;
        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient
                (activity);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(activity, location -> {
                    // Got last known location. In some rare situations, this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation.setValue(location);
                        Log.v(TAG, "My location: " + location.getLatitude() + "/" + location.getLongitude());

                    }
                });
    }

    private void checkPermission(int reqCode, Activity activity) {
        Log.v(TAG, "checkPermission");
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is not granted");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // TODO:
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.v(TAG, "Should show the explanation of the needs of the permission");
                buildAlertMessageNoGps(reqCode, activity);
            } else {
                // No explanation needed, we can request the permission.
                Log.v(TAG, "Ignore explanation, go ahead and request the permission");
                requestLocationPermission(reqCode, activity);
            }
        }
    }

    private void buildAlertMessageNoGps(final int reqCode, Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Log.v(TAG, "Request for permission");
                        requestLocationPermission(reqCode, activity);
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

    public void requestLocationPermission(int tag, Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, tag);
    }

}
