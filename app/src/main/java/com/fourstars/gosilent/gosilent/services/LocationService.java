package com.fourstars.gosilent.gosilent.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.fourstars.gosilent.gosilent.checklocation.LocationChecker;
import com.fourstars.gosilent.gosilent.databaseanddao.DatabaseHandler;
import com.fourstars.gosilent.gosilent.databaseanddao.LocationBox;
import com.fourstars.gosilent.gosilent.databaseanddao.MyApplication;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Jayant on 20-07-2017.
 */

public class LocationService extends Service {
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    LatLng myhome;

    private class LocationListener implements android.location.LocationListener
    {

        Location mLastLocation;
        DatabaseHandler databaseHandler = new DatabaseHandler(LocationService.this);

        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            Toast.makeText(LocationService.this,"Location changed",Toast.LENGTH_SHORT).show();
            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            myhome=new LatLng(location.getLatitude(),location.getLongitude());
            ((MyApplication)getApplication()).setMyLocation(myhome);
            List<LocationBox> locationBoxList=databaseHandler.locationBoxStatusON();
            for(LocationBox locationBox:locationBoxList) {
                LocationChecker locationChecker = new LocationChecker(locationBox,myhome);
                if(locationChecker.checkif()) {
                    if (locationBox.getMode().equalsIgnoreCase("Silent")) {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        Toast.makeText(LocationService.this, "Now Silent!!!", Toast.LENGTH_SHORT).show();
                    } else if (locationBox.getMode().equalsIgnoreCase("Vibrate")) {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        Toast.makeText(LocationService.this, "Now Vibrate!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        Toast.makeText(LocationService.this, "Now Normal!!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(LocationService.this,"Service Started",Toast.LENGTH_SHORT).show();
        Log.e("Service", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        Toast.makeText(LocationService.this,"Service Destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
