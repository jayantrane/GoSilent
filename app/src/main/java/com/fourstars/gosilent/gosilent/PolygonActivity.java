package com.fourstars.gosilent.gosilent;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jayant on 23-06-2017.
 */
public class PolygonActivity extends AppCompatActivity
        implements  GoogleMap.OnMyLocationButtonClickListener,OnMapReadyCallback {

    private /*static final*/ LatLng CENTER /*= new LatLng(-20, 130)*/;

    private Polygon mMutablePolygon;

    private LatLng myhome;
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectangle);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        myhome = new LatLng(location.getLatitude(), location.getLongitude());
                        CENTER = myhome;
                        Log.e("msg","Center and myhome are ser");


                        if (location != null) {
                            // ...
                        }
                    }
                });
    }


    @Override
    public void onMapReady( GoogleMap map) {

        mMap = map;
        CENTER =  ((MyApplication) this.getApplication()).getMyLocation();
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setMyLocationEnabled(true);
        drawRectangle(map);

    }

    public void drawRectangle(GoogleMap map){
        // Create a rectangle with two rectangular holes.
        mMutablePolygon = map.addPolygon(new PolygonOptions()
                        .addAll(createRectangle(CENTER, .002, .002))

                /*.addHole(createRectangle(new LatLng(-22, 128), 1, 1))
                .addHole(createRectangle(new LatLng(-18, 133), 0.5, 1.5))*/
                /*.fillColor(fillColorArgb)
                .strokeColor(strokeColorArgb)
                .strokeWidth(mStrokeWidthBar.getProgress())
                .clickable(mClickabilityCheckbox.isChecked())*/);
        Log.e("msg","map parameter are set");

        //      mMutablePolygon.setStrokeJointType(getSelectedJointType(mStrokeJointTypeSpinner.getSelectedItemPosition()));
        //      mMutablePolygon.setStrokePattern(getSelectedPattern(mStrokePatternSpinner.getSelectedItemPosition()));

        // Move the map so that it is centered on the mutable polygon.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTER, 16));

        // Add a listener for polygon clicks that changes the clicked polygon's stroke color.
        map.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                // Flip the red, green and blue components of the polygon's stroke color.
                polygon.setStrokeColor(polygon.getStrokeColor() ^ 0x00ffffff);
            }
        });


    }

    /**
     * Creates a List of LatLngs that form a rectangle with the given dimensions.
     */
    private List<LatLng> createRectangle(LatLng center, double halfWidth, double halfHeight) {
        return Arrays.asList(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        mMap.addMarker(new MarkerOptions().position(myhome).title("My House"));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myhome, zoomLevel));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(myhome));

        return false;
    }


}
