package com.example.karol.stairsapp.SensorTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.karol.stairsapp.LED;
import com.example.karol.stairsapp.LedAdapter;
import com.example.karol.stairsapp.MainActivity;
import com.example.karol.stairsapp.R;
import com.example.karol.stairsapp.SensorAdapter;

import java.util.ArrayList;

/**
 * Created by Karol on 2016-11-23.
 */

public class LedTab extends Fragment {

    ListView ledListView;
    ArrayList<LED> ledList;
    LedAdapter ledAdapter;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.tab_led_layout, container, false);

        ledListView = (ListView) myView.findViewById(R.id.led_listView);
        ledList = MainActivity.LED_ARRAY;
        ledAdapter = new LedAdapter(ledList, getActivity());
        ledListView.setAdapter(ledAdapter);

        return myView;
    }

}
