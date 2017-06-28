package com.fourstars.gosilent.gosilent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Jayant on 28-06-2017.
 */

public class LocationBoxListActivity extends AppCompatActivity{
    LocationBox mLocationBox;
    ArrayList<LocationBox> mAlLocationBox = new ArrayList<LocationBox>();

    private ListView listcontent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationboxlist);
        DatabaseHandler databaseHandler=new DatabaseHandler(this);

        listcontent=(ListView) findViewById(R.id.listView1);
        filllist();

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
            mLocationBox.setId(p+1);
            mLocationBox.setName(temp.get(1).toString());
            mLocationBox.setPoint1(temp.get(2).toString());
            mAlLocationBox.add(mLocationBox);
        }
        LocationBoxAdapter locationBoxAdapter = new LocationBoxAdapter(LocationBoxListActivity.this,mAlLocationBox);
        listcontent.setAdapter(locationBoxAdapter);
    }
}