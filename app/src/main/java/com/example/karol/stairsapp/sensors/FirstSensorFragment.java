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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.karol.stairsapp.R;

public class FirstSensorFragment extends Fragment{

    static final String ORDER_CHECKED = "spinner_order_checked";
    static final String TYPE_CHECKED  = "spinner_type_checked";

    private String spinner_order_checked;
    private String spinner_type_checked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.single_sensor_layout,null);

        /* Konfiguracja spinnerów */
        final Spinner spinner_order = (Spinner) view.findViewById(R.id.spinner_sensor_order);
        ArrayAdapter<CharSequence> adapter_spinner_order = ArrayAdapter.createFromResource(getActivity(),
                R.array.sensor_order_array, android.R.layout.simple_spinner_item);
        adapter_spinner_order.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_order.setAdapter(adapter_spinner_order);

        if (savedInstanceState != null) {
//            spinner_order_checked = savedInstanceState.getString(ORDER_CHECKED, "test one");
//
//            spinner_order.setSelection(adapter_spinner_order.getPosition(spinner_order_checked));
        }

        spinner_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_order_checked = spinner_order.getSelectedItem().toString();
                Toast.makeText(getActivity(), spinner_order_checked, Toast.LENGTH_LONG).show();
//                onSaveInstanceState(savedInstanceState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinner_type = (Spinner) view.findViewById(R.id.spinner_sensor_type);
        ArrayAdapter<CharSequence> adapter_spinner_type = ArrayAdapter.createFromResource(getActivity(),
                R.array.sensor_type_array, android.R.layout.simple_spinner_dropdown_item);
        adapter_spinner_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter_spinner_type);

        return view;
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putString(ORDER_CHECKED, spinner_order_checked);
//
//        super.onSaveInstanceState(savedInstanceState);
//    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState != null)
//            spinner_order_checked = savedInstanceState.getString(ORDER_CHECKED, "test one");
//    }
}
