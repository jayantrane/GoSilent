package com.fourstars.gosilent.gosilent.activities;


import android.content.Intent;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.fourstars.gosilent.gosilent.services.LocationService;
import com.fourstars.gosilent.gosilent.databaseanddao.MyApplication;
import com.fourstars.gosilent.gosilent.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button mbutton;
    private Button mbutton2;
    private Button mbutton3;
    private TextView mtextView;
    private TextView mtextView2;
    private boolean runninginback=false;

    LatLng myhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mbutton = (Button) findViewById(R.id.button);
        mbutton2 = (Button) findViewById(R.id.button2);
        mbutton3 = (Button) findViewById(R.id.button3);
        mtextView = (TextView) findViewById(R.id.textView6);
/*        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MyLocationActivity.class);
                startActivity(intent);
            }
        });*/



        mbutton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PolygonActivity.class);
                startActivity(intent);
            }
        });

        mbutton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, LocationBoxListActivity.class);
                startActivity(intent);
            }
        });
//        mbutton7.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(MainActivity.this, LocationService.class);
//                stopService(i);
//            }
//        });
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            myhome= ((MyApplication)getApplication()).getMyLocation();
            addresses = geocoder.getFromLocation(myhome.latitude,myhome.longitude,1);
            mtextView.setText(addresses.toString());
            String address="" ;
            /*= addresses.get(0).getAddressLine(0);*/
            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
/*            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();*/
            for(int i = 0; i <= addresses.get(0).getMaxAddressLineIndex(); i++) {
                address=address.concat(addresses.get(0).getAddressLine(i)+"\n");
            }
            mtextView.setText(address);
            Log.e("Address",addresses.toString());
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e("geocoder",
                    "Latitude = " + myhome.latitude +
                    ", Longitude = " +
                    myhome.longitude, illegalArgumentException);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.runappinback:
                runappinback();
                return true;
            case R.id.shutdown:
                finish();
                Intent i = new Intent(MainActivity.this, LocationService.class);
                stopService(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void runappinback(){
        runninginback=true;

        finish();
        Intent intent = new Intent(MainActivity.this,LocationService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(runninginback==false) {

            Intent i = new Intent(MainActivity.this, LocationService.class);
            stopService(i);
        }
/*        Intent i = new Intent(MainActivity.this, NewActivity.class);
// set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);*/

    }
}
