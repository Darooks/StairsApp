package com.example.karol.stairsapp.SensorTabs;

/**
 * Created by Karol on 2016-11-23.
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.karol.stairsapp.MainActivity;
import com.example.karol.stairsapp.R;
import com.example.karol.stairsapp.Sensor;
import com.example.karol.stairsapp.SensorAdapter;

import java.util.ArrayList;

public class SensorTab extends Fragment {
    public static ListView sensorListView;
    public static ArrayList<Sensor> sensorList;
    public static SensorAdapter sensorAdapter;

    public static View myView;

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            sensorListView = (ListView) myView.findViewById(R.id.sensor_listView);
            sensorList = MainActivity.SENSORS_ARRAY;
            sensorAdapter = new SensorAdapter(sensorList, myView.getContext());
            sensorListView.setAdapter(sensorAdapter);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.tab_sensor_layout, container, false);
        sensorListView = (ListView) myView.findViewById(R.id.sensor_listView);
        sensorList = MainActivity.SENSORS_ARRAY;
        sensorAdapter = new SensorAdapter(sensorList, getActivity());
        sensorListView.setAdapter(sensorAdapter);
        MainActivity.isSensorTabInited = true;

        return myView;
    }
}
