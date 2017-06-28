package com.fourstars.gosilent.gosilent;

import android.content.Context;
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

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jayant on 23-06-2017.
 */
public class PolygonActivity extends AppCompatActivity
        implements  GoogleMap.OnMyLocationButtonClickListener,OnMapReadyCallback ,GoogleMap.OnMapLongClickListener{

    private /*static final*/ LatLng mLocation /*= new LatLng(-20, 130)*/;
    private LatLng[] myBox;

    private int count=0;

    Context context;
    File file;
    private boolean filecreated=false;

    DatabaseHandler databaseHandler;
    LocationBox mLocationBox;


    private Polygon mMutablePolygon;

    private LatLng myhome;
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectangle);

        myBox = new LatLng[4];

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseHandler =new DatabaseHandler(this);

        context=getApplicationContext();
        Log.e("msg",context.toString());


        context=getBaseContext();
        Log.e("msg",context.toString());


        if(!filecreated) {
            file = new File(context.getFilesDir(), "LocationData");
        }

/*        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        myhome = new LatLng(location.getLatitude(), location.getLongitude());
                        mLocation = myhome;
                        Log.e("msg","mLocation and myhome are set");


                        if (location != null) {
                            // ...
                        }
                    }
                });*/
    }


    @Override
    public void onMapReady( final GoogleMap map) {

        mMap = map;
        mLocation =  ((MyApplication) this.getApplication()).getMyLocation();
    //    mLocation = new LatLng(18.4542944,73.8662839);
        Log.e("msg","mLocation has been retrieved.");
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setMyLocationEnabled(true);
      //  drawRectangle(map);
        Log.e("reading","Reading in on map ready");
        List<LocationBox> listlocationBox=databaseHandler.findAll();
        for(LocationBox b:listlocationBox){
            Log.e("data", "ID :"+b.getId()+" | Name :"+b.getName()+" | Point1 :"+b.getPoint1()
                    +" | Point2 :"+b.getPoint2()+" | Point3 :"+b.getPoint3()+" | Point4 :"+b.getPoint4());
        }


        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                myBox[count]=point;
                count++;
                Toast.makeText(PolygonActivity.this,"Point "+count+" has been Set.",Toast.LENGTH_SHORT).show();
                Log.e("msg","Point"+count+" has been Set.");
                mMap.addMarker(new MarkerOptions().position(point).title(count+". Point"));
                if(count==4){
                    Toast.makeText(PolygonActivity.this,"All points are Set.",Toast.LENGTH_SHORT).show();
                    Log.e("msg","All points are Set.");
                    drawBox(map);

                    Log.e("insert", "Insertion Initiated");
                    mLocationBox = new LocationBox();
                //    mLocationBox.setId(3);
                    mLocationBox.setName("1.Location Box");
                    mLocationBox.setPoint1(myBox[0].latitude+","+myBox[0].longitude);
                    mLocationBox.setPoint2(myBox[1].latitude+","+myBox[1].longitude);
                    mLocationBox.setPoint3(myBox[2].latitude+","+myBox[2].longitude);
                    mLocationBox.setPoint4(myBox[3].latitude+","+myBox[3].longitude);
                    databaseHandler.save(mLocationBox);
                    Log.e("insert","Insertion Completed");

                    Log.e("reading","Reading in MapLongClick Listener");
                    LocationBox b=databaseHandler.findOne(databaseHandler.getIdCount());
                    Log.e("data", "ID :"+b.getId()+" | Name :"+b.getName()+" | Point1 :"+b.getPoint1()
                            +" | Point2 :"+b.getPoint2()+" | Point3 :"+b.getPoint3()+" | Point4 :"+b.getPoint4());
                }
            }
        });



    }

    public void drawBox(GoogleMap map){
        Log.e("msg","Draw Box has been called");
        mMutablePolygon = map.addPolygon(new PolygonOptions().add(myBox[0],myBox[1],myBox[2],myBox[3]));

    }

    public void drawRectangle(GoogleMap map){
        // Create a rectangle with two rectangular holes.
        mMutablePolygon = map.addPolygon(new PolygonOptions()
                        .addAll(createRectangle(mLocation, .002, .002))

                    .addHole(createRectangle(new LatLng(mLocation.latitude+.002, mLocation.longitude+.002), 1, 1))
                .addHole(createRectangle(new LatLng(mLocation.latitude-.002, mLocation.longitude-.002), 0.5, 1.5))
               /* .fillColor(fillColorArgb)
                .strokeColor(strokeColorArgb)
                .strokeWidth(mStrokeWidthBar.getProgress())
                .clickable(mClickabilityCheckbox.isChecked())*/);
        Log.e("msg","Polygon has been drawn.");

        //      mMutablePolygon.setStrokeJointType(getSelectedJointType(mStrokeJointTypeSpinner.getSelectedItemPosition()));
        //      mMutablePolygon.setStrokePattern(getSelectedPattern(mStrokePatternSpinner.getSelectedItemPosition()));

        // Move the map so that it is centered on the mutable polygon.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 16));

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
        return Arrays.asList(new LatLng(center.latitude - halfHeight, mLocation.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Your Location", Toast.LENGTH_SHORT).show();

        mMap.addMarker(new MarkerOptions().position(mLocation).title("My House"));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, zoomLevel));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(myhome));

        return false;
    }


    @Override
    public void onMapLongClick(LatLng latLng) {

    }
}
