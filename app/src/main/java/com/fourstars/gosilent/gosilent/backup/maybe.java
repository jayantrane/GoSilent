package com.fourstars.gosilent.gosilent.backup;

/**
 * Created by Jayant on 18-07-2017.
 */

public class maybe {

   /* MainActivity implements ActivityCompat.OnRequestPermissionsResultCallback /*,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener
    private FusedLocationProviderClient mFusedLocationClient;
    // public int REQUEST_CHECK_SETTINGS =1;
    LocationRequest mLocationRequest;
    final static int REQUEST_LOCATION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    DatabaseHandler databaseHandler;

    private boolean mPermissionDenied = false;
    private boolean mLocationEnabled = false;
    private boolean mRequestingLocationUpdates=false;
    private LatLng myhome;

    private GoogleMap mMap;

    private LocationCallback mLocationCallback;

    *//*  LocationRequest mLocationRequest;
      GoogleApiClient mGoogleApiClient;
      PendingResult<LocationSettingsResult> result;
      final static int REQUEST_LOCATION = 1;*//*
    in create method
    *//*

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                    Location location =locationResult.getLastLocation();
                mtextView.setText("Lat : " + location.getLatitude() + " Lng : " + location.getLongitude());
                mtextView2.setText("Your Location is accessed successfully!!");
                Toast.makeText(MainActivity.this, "Good To Go", Toast.LENGTH_LONG).show();
                LatLng temp = new LatLng(location.getLatitude(), location.getLongitude());
                Log.e("msg","Location has been set");
                ((MyApplication) MainActivity.this.getApplication()).setMyLocation(temp);


                *//*
*//*for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    mtextView.setText("Lat : " + location.getLatitude() + " Lng : " + location.getLongitude());
                }*//**//*

            };
        };
        enableMyLocation();
*//*

   *//*     mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
*//*



    //    enableMyLocation();


    in rest

*//*    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
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
                            resolvable.startResolutionForResult(MainActivity.this,
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
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                 //   mtextView.setText("Lat : " + location.getLatitude() + " Lng : " + location.getLongitude());

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
                        Toast.makeText(MainActivity.this, "Location enabled by user!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Please wait for process to complete", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED:
                    {
                        // The user was asked to change settings, but chose not to
                        Log.e("msg","GPS not enabled.");
                        Toast.makeText(MainActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
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
    }*//*

 *//*   @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(2 * 1000);
        mLocationRequest.setFastestInterval(1 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                //final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //...
                        mLocationEnabled=true;
                    //    enableMyLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    MainActivity.this,
                                    REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        //...
                        break;
                }
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
     @Override
    public void onLocationChanged(Location location) {
                Log.e("msg","I am Location Listener");
    }

*//*
*/
}
