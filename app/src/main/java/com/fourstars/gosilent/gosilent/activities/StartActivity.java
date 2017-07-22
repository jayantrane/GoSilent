package com.fourstars.gosilent.gosilent.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fourstars.gosilent.gosilent.services.LocationService;
import com.fourstars.gosilent.gosilent.databaseanddao.MyApplication;
import com.fourstars.gosilent.gosilent.permissions.PermissionUtils;
import com.fourstars.gosilent.gosilent.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;

/**
 * Created by Jayant on 18-07-2017.
 */

public class StartActivity extends AppCompatActivity {

    LocationRequest mLocationRequest;
    final static int REQUEST_LOCATION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    volatile boolean setLocation=false;
    private FusedLocationProviderClient mFusedLocationClient;

    private boolean mPermissionDenied = false;
    private boolean mLocationEnabled = false;
    private boolean mRequestingLocationUpdates=false;
    private LatLng myhome;


    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        final Thread  welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    while(setLocation==false) {
                        Log.e("Start","Location Start Accessing");
                        sleep(5000);
                        Log.e("Start","Location accessing");
              //          Toast.makeText(StartActivity.this,"Location Accesed",Toast.LENGTH_SHORT).show();
                    }//Delay of 10 seconds
                } catch (Exception e) {
                    Log.e("Start","Location catch accessing");
                } finally {
                    Log.e("Start","Location finally accessing");
                    Intent i = new Intent(StartActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.e("Start"," new thread Location accessing");
                Location location =locationResult.getLastLocation();
                myhome = new LatLng(location.getLatitude(), location.getLongitude());
                setLocation=true;
                Log.e("msg","Location has been set");
                ((MyApplication) StartActivity.this.getApplication()).setMyLocation(myhome);
                welcomeThread.start();


            }
        };
        enableMyLocation();
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void tasker(){
        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        Log.e("msg","GPS and Location permissions will be requested.");


        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());



        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                mLocationEnabled=true;
                Log.e("msg","GPS task success");
                //  enableMyLocation();
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(StartActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            Log.e("msg","Task failed in catch task");
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });

    }
    private void enableMyLocation() {

        Log.e("msg","EnableMyLocation requested.");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else {
            // Access to the location has been granted to the app.
            tasker();
            mRequestingLocationUpdates=true;
            startLocationUpdates();
            Log.e("msg","Permission is granted.");

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if(!mPermissionDenied && mLocationEnabled) {

                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(StartActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    //   mtextView.setText("Lat : " + location.getLatitude() + " Lng : " + location.getLongitude());
                                    Log.e("msg","FusedLocationClient location is not null.");
                                }
                                else{
                                    Log.e("msg","FusedLocationClient location is null.");
                                }
                            }
                        });
            }
            //  mMap.setMyLocationEnabled(true);
        }
    }

    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            mPermissionDenied = false;
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            Log.e("msg","Location Permission is Denied.");
            mPermissionDenied = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("onActivityResult()", Integer.toString(resultCode));
        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode)
        {

            case REQUEST_LOCATION:

                switch (resultCode)
                {
                    case Activity.RESULT_OK:
                    {
                        // All required changes were successfully made
                        Log.e("msg","GPS enabled.");
                        mLocationEnabled=true;
                        //   enableMyLocation();
                      //  Toast.makeText(StartActivity.this, "Location enabled by user!", Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(StartActivity.this, "Please wait for process to complete", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED:
                    {
                        // The user was asked to change settings, but chose not to
                        Log.e("msg","GPS not enabled.");
                    //    Toast.makeText(StartActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
