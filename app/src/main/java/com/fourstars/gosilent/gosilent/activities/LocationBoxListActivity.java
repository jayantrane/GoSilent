package com.fourstars.gosilent.gosilent.activities;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fourstars.gosilent.gosilent.databaseanddao.DatabaseHandler;
import com.fourstars.gosilent.gosilent.databaseanddao.LocationBox;
import com.fourstars.gosilent.gosilent.databaseanddao.MyApplication;
import com.fourstars.gosilent.gosilent.R;
import com.fourstars.gosilent.gosilent.checklocation.LocationChecker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Jayant on 28-06-2017.
 */

public class LocationBoxListActivity extends AppCompatActivity{
    LocationBox mLocationBox;
    ArrayList<LocationBox> mAlLocationBox = new ArrayList<LocationBox>();
    private RadioGroup mradiogroup;
    private RadioButton mradiobutton;
    LocationChecker locationChecker;
    LatLng myLocation;

    Parcelable state;

    private ListView listcontent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationboxlist);
        myLocation=((MyApplication) this.getApplication()).getMyLocation();
        listcontent=(ListView) findViewById(R.id.listView1);
        mAlLocationBox.clear();
        final DatabaseHandler databaseHandler=new DatabaseHandler(this);
        ArrayList<ArrayList<Object>> data =  databaseHandler.listIitems();
        // Restore previous state (including selected item index and scroll position)

        for (int p = 0; p < data.size(); p++) {
            mLocationBox = new LocationBox();
            ArrayList<Object> temp = data.get(p);
            Log.e("List", temp.get(0).toString());
            Log.e("List", temp.get(1).toString());
            Log.e("List", temp.get(2).toString());
            Log.e("List", temp.get(3).toString());
            mLocationBox.setId(Integer.valueOf(temp.get(0).toString()));
            mLocationBox.setName(temp.get(1).toString());
           /* mLocationBox.setPoint1(temp.get(2).toString());
            mLocationBox.setPoint2(temp.get(3).toString());
            mLocationBox.setPoint3(temp.get(4).toString());
            mLocationBox.setPoint4(temp.get(5).toString());*/
            mLocationBox.setMode(temp.get(2).toString());
            mLocationBox.setStatus(temp.get(3).toString());
            mAlLocationBox.add(mLocationBox);
        }
        final LocationBoxAdapter locationBoxAdapter = new LocationBoxAdapter(LocationBoxListActivity.this,mAlLocationBox);
        listcontent.setAdapter(locationBoxAdapter);
        listcontent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                LocationBox locationBox= mAlLocationBox.get(position);
                locationBox=databaseHandler.findOne(locationBox.getId());

                mradiogroup = (RadioGroup) findViewById(R.id.radiogroup2);
                int selectedid=mradiogroup.getCheckedRadioButtonId();
                mradiobutton=(RadioButton)findViewById(selectedid);

                if(mradiobutton.getText().toString().equals("OFF")){
                    mradiogroup.check(R.id.radioButton5);
                    Log.e("mlocation", myLocation.latitude + " | "+ myLocation.longitude);
                    Log.e("mlocation",locationBox.getName()+ " | "+locationBox.getPoint1()+ " | "+locationBox.getPoint2()+ " | "+locationBox.getPoint3()+ " | "+locationBox.getPoint4()+ " | "+locationBox.getStatus());
                    locationChecker = new LocationChecker( locationBox , myLocation);
                    if(locationChecker.checkif()) {
                        if (locationBox.getMode().equalsIgnoreCase("Silent")) {
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            Toast.makeText(LocationBoxListActivity.this, "Now Silent!!!", Toast.LENGTH_SHORT).show();
                        } else if (locationBox.getMode().equalsIgnoreCase("Vibrate")) {
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            Toast.makeText(LocationBoxListActivity.this, "Now Vibrate!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            Toast.makeText(LocationBoxListActivity.this, "Now Normal!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    locationBox.setStatus("ON");
                    Log.e("mlocation",locationBox.getName()+ " | "+locationBox.getPoint1()+ " | "+locationBox.getPoint2()+ " | "+locationBox.getPoint3()+ " | "+locationBox.getPoint4()+ " | "+locationBox.getStatus());
                    databaseHandler.update(locationBox);
                    finish();
                    startActivity(getIntent());
                //    locationBoxAdapter.notifyDataSetChanged();

                }else{
                    mradiogroup.check(R.id.radioButton6);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    locationBox.setStatus("OFF");
                    Log.e("mlocation",locationBox.getName()+ " | "+locationBox.getPoint1()+ " | "+locationBox.getPoint2()+ " | "+locationBox.getPoint3()+ " | "+locationBox.getPoint4()+ " | "+locationBox.getStatus());
                    databaseHandler.update(locationBox);
                    Toast.makeText(LocationBoxListActivity.this, "Now Normal!!!",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                   // locationBoxAdapter.notifyDataSetChanged();
                }


            }
        });

    }
    private void filllist() {
        // TODO Auto-generated method stub
        mAlLocationBox.clear();
        DatabaseHandler databaseHandler=new DatabaseHandler(this);
        ArrayList<ArrayList<Object>> data =  databaseHandler.listIitems();

        for (int p = 0; p < data.size(); p++) {
            mLocationBox = new LocationBox();
            ArrayList<Object> temp = data.get(p);
            Log.e("List", temp.get(0).toString());
            Log.e("List", temp.get(1).toString());
            Log.e("List", temp.get(2).toString());
            Log.e("List", temp.get(3).toString());
            mLocationBox.setId(Integer.valueOf(temp.get(0).toString()));
            mLocationBox.setName(temp.get(1).toString());
            mLocationBox.setMode(temp.get(2).toString());
            mLocationBox.setStatus(temp.get(3).toString());
            mAlLocationBox.add(mLocationBox);
        }
        LocationBoxAdapter locationBoxAdapter = new LocationBoxAdapter(LocationBoxListActivity.this,mAlLocationBox);
        listcontent.setAdapter(locationBoxAdapter);
    }
    public class LocationBoxAdapter extends BaseAdapter implements View.OnClickListener{

        private  ArrayList<LocationBox> searchArrayList;

        private LayoutInflater mInflater;

        public LocationBoxAdapter(Context context, ArrayList<LocationBox> results) {
            searchArrayList = results;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return searchArrayList.size();
        }

        @Override
        public Object getItem(int p) {
            return searchArrayList.get(p);
        }

        @Override
        public long getItemId(int p) {
            return p;
        }

        @Override
        public View getView( int p,  View v, ViewGroup parent) {
            LocationBoxAdapter.ViewHolder holder;
            DatabaseHandler databaseHandler=new DatabaseHandler(LocationBoxListActivity.this);

            if (v == null) {
                v = mInflater
                        .inflate(R.layout.list_item, null);
                holder = new LocationBoxAdapter.ViewHolder();
                holder.id = (TextView) v.findViewById(R.id.outid);
                holder.name = (TextView) v.findViewById(R.id.outname);
                holder.mode = (TextView) v.findViewById(R.id.outmode);
                holder.status = (RadioGroup) v.findViewById(R.id.radiogroup2);
                holder.on = (RadioButton) v.findViewById(R.id.radioButton5) ;
                holder.off = (RadioButton) v.findViewById(R.id.radioButton6) ;
                v.setTag(holder);
            } else {
                holder = (LocationBoxAdapter.ViewHolder) v.getTag();
            }
            holder.id.setText(String.valueOf(searchArrayList.get(p).getId()));
            holder.name.setText(searchArrayList.get(p).getName());
            holder.mode.setText(searchArrayList.get(p).getMode());
            String s  = searchArrayList.get(p).getStatus();
            if(s.equals("ON")){
                holder.status.check(holder.on.getId());

            }else {
                holder.status.check(holder.off.getId());
            }

            return v;
        }

        @Override
        public void onClick(View v) {
            int position=(Integer) v.getTag();
            Object object= getItem(position);
            LocationBox locationBox=(LocationBox) object;
            Toast.makeText(LocationBoxListActivity.this,"ON Clicked",Toast.LENGTH_SHORT).show();

            switch (v.getId())
            {case R.id.tableRow1:
                Log.e("LocationBoxAdapter","On Item Clicked");

            }
        }

         class ViewHolder {
            TextView id,name, mode;
            RadioGroup status;
            RadioButton on,off;

        }

    }

}