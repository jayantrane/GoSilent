package com.fourstars.gosilent.gosilent.backup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fourstars.gosilent.gosilent.R;
import com.fourstars.gosilent.gosilent.databaseanddao.LocationBox;

import java.util.ArrayList;

/**
 * Created by Jayant on 28-06-2017.
 */

public class LocationBoxAdapter_old extends BaseAdapter implements View.OnClickListener{

    private static ArrayList<LocationBox> searchArrayList;

    private LayoutInflater mInflater;

    public LocationBoxAdapter_old(Context context, ArrayList<LocationBox> results) {
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
         ViewHolder holder;

        if (v == null) {
            v = mInflater
                    .inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.id = (TextView) v.findViewById(R.id.outid);
            holder.name = (TextView) v.findViewById(R.id.outname);
            holder.mode = (TextView) v.findViewById(R.id.outmode);
            holder.status = (RadioGroup) v.findViewById(R.id.radiogroup2);
            holder.on = (RadioButton) v.findViewById(R.id.radioButton5) ;
            holder.off = (RadioButton) v.findViewById(R.id.radioButton6) ;
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
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

        switch (v.getId())
        {case R.id.tableRow1:
            Log.e("LocationBoxAdapter","On Item Clicked");

        }
    }

    static class ViewHolder {
        TextView id,name, mode;
        RadioGroup status;
        RadioButton on,off;

    }

}

