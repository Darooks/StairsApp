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

        /* Konfiguracja spinnerów */
        Spinner spinner_order = (Spinner) view.findViewById(R.id.spinner_sensor_order);
        ArrayAdapter<CharSequence> adapter_spinner_order = ArrayAdapter.createFromResource(getActivity(),
                R.array.sensor_order_array, android.R.layout.simple_spinner_item);
        adapter_spinner_order.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_order.setAdapter(adapter_spinner_order);

        Spinner spinner_type = (Spinner) view.findViewById(R.id.spinner_sensor_type);
        ArrayAdapter<CharSequence> adapter_spinner_type = ArrayAdapter.createFromResource(getActivity(),
                R.array.sensor_type_array, android.R.layout.simple_spinner_dropdown_item);
        adapter_spinner_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter_spinner_type);

        return view;
    }

}
