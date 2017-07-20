package com.fourstars.gosilent.gosilent.backup;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Jayant on 15-07-2017.
 */

public class LocationBoxListActivity2 extends ListActivity {
/*
    LocationBox mLocationBox;
    ArrayList<LocationBox> mAlLocationBox = new ArrayList<LocationBox>();

    private ListView listcontent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationboxlist);

        listcontent=(ListView) findViewById(R.id.listView1);
        filllist();

        // initiate the listadapter
        ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this,
                R.layout.activity_locationboxlist, R.id.list_item, mAlLocationBox);

        // assign the list adapter
        setListAdapter(myAdapter);

    }

    // when an item of the list is clicked
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        String selectedItem = (String) getListView().getItemAtPosition(position);
        //String selectedItem = (String) getListAdapter().getItem(position);

        text.setText("You clicked " + selectedItem + " at position " + position);
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
            mLocationBox.setId(p+1);
            mLocationBox.setName(temp.get(1).toString());
            mLocationBox.setMode(temp.get(2).toString());
            mLocationBox.setStatus(temp.get(3).toString());
            mAlLocationBox.add(mLocationBox);
        }
        LocationBoxAdapter locationBoxAdapter = new LocationBoxAdapter(LocationBoxListActivity2.this,mAlLocationBox);
        listcontent.setAdapter(locationBoxAdapter);
    }*/
}
