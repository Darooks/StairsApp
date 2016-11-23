package com.example.karol.stairsapp.SensorTabs;

/**
 * Created by Karol on 2016-11-23.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karol.stairsapp.R;

public class SensorTab extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.single_sensor_layout, null);
    }

}
