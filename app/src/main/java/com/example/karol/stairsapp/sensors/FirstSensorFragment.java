package com.example.karol.stairsapp.sensors;

/**
 * Created by Karol on 2016-10-23.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.karol.stairsapp.R;

public class FirstSensorFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.single_sensor_layout,null);

        /* Konfiguracja spinnera */
//        Spinner spinner_order = (Spinner) view.findViewById(R.id.spinner_sensor_order);
//        String[] order_items = getResources().getStringArray(R.array.sensor_order_array);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.single_sensor_layout, order_items);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_order.setAdapter(adapter);


        return view;
    }

}
