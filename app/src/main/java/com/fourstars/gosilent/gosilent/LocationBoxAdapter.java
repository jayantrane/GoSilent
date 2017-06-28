package com.fourstars.gosilent.gosilent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jayant on 28-06-2017.
 */

public class LocationBoxAdapter extends BaseAdapter{

    private static ArrayList<LocationBox> searchArrayList;

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
    public View getView(int p, View v, ViewGroup parent) {
        ViewHolder holder;

        if (v == null) {
            v = mInflater
                    .inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.id = (TextView) v.findViewById(R.id.outid);
            holder.name = (TextView) v.findViewById(R.id.outname);
            holder.coordinates = (TextView) v.findViewById(R.id.outcoordinates);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.id.setText(String.valueOf(searchArrayList.get(p).getId()));
        holder.name.setText(searchArrayList.get(p).getName());
        holder.coordinates.setText(searchArrayList.get(p).getPoint1());
        return v;
    }

    static class ViewHolder {
        TextView id,name, coordinates;

    }

}

