package com.example.karol.stairsapp.ConfigurationTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.karol.stairsapp.LED;
import com.example.karol.stairsapp.MainActivity;
import com.example.karol.stairsapp.R;
import com.example.karol.stairsapp.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2016-11-23.
 */

public class OrderTab extends Fragment {

    View myView;

    private ArrayList<Sensor> sensors_array = MainActivity.SENSORS_ARRAY;
    private ArrayList<LED> led_array = MainActivity.LED_ARRAY;
    private List<String> components_order_array = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.tab_order_layout, null);
        orderComponents();

        ListView orderLV = (ListView) myView.findViewById(R.id.order_ListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, components_order_array);
        orderLV.setAdapter(adapter);

        return myView;
    }

    private void orderComponents() {
        int active_sensors = 0;
        int active_led = 0;

        for (Sensor it : sensors_array) {
            if (it.active)
                active_sensors++;
        }

        for (LED it : led_array) {
            if (it.active)
                active_led++;
        }

        if (active_sensors == 0)
            return;

        int median;
        if (active_led != 0)
            median = active_led / active_sensors;
        else
            median = 0;

        int counter = 0;
        int led_counter = 0;

        if (sensors_array.size() > 0) {
            for (Sensor sensor : sensors_array) {
                if (sensor.active) {
                    components_order_array.add(sensor.sensorId);
                    for (int i = led_counter; i < led_array.size(); i++) {
                        if (led_array.get(i).active) {
                            components_order_array.add(led_array.get(i).ledId);
                            counter++;
                            led_counter++;
                        }

                        if (counter >= median)
                            break;
                    }
                }
            }

            if (led_counter < led_array.size())
                for (int i = led_counter; i < led_array.size(); i++)
                    if (led_array.get(i).active)
                        components_order_array.add(led_array.get(i).ledId);
        }
        MainActivity.COMPONENTS_ORDER_ARRAY.clear();
        MainActivity.COMPONENTS_ORDER_ARRAY = components_order_array;

    }

}