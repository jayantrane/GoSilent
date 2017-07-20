package com.fourstars.gosilent.gosilent.databaseanddao;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jayant on 24-06-2017.
 */

public class MyApplication extends Application {

    private LatLng myLocation;
    private LocationBox myLocationBox;

    public LatLng getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(LatLng myLocation) {
        this.myLocation = myLocation;
    }

    public LocationBox getMyLocationBox() {
        return myLocationBox;
    }

    public void setMyLocationBox(LocationBox myLocationBox) {
        this.myLocationBox = myLocationBox;
    }
}
