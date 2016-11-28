package com.example.karol.stairsapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karol.stairsapp.SensorTabs.SensorTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2016-11-23.
 */

public class SensorAdapter extends ArrayAdapter<Sensor> {

    private List<Sensor> sensorsList;
    private Context context;

    public SensorAdapter(ArrayList<Sensor> sensorsList, Context context) {
        super(context, R.layout.tab_sensor_layout, sensorsList);
        this.sensorsList = sensorsList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        sensorsList = MainActivity.SENSORS_ARRAY;

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.name_chk_box);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.chk_box);

        name.setText(sensorsList.get(position).sensorId);
        cb.setChecked(sensorsList.get(position).active);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.SENSORS_ARRAY.get(position).active)
                    MainActivity.SENSORS_ARRAY.get(position).active = false;
                else
                    MainActivity.SENSORS_ARRAY.get(position).active = true;

                Log.d("ck", MainActivity.SENSORS_ARRAY.get(position).sensorId.toString() + " " + String.valueOf(MainActivity.SENSORS_ARRAY.get(position).active));
            }
        });

        return convertView;
    }
}
