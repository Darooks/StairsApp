package com.example.karol.stairsapp.ConfigurationTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karol.stairsapp.R;

/**
 * Created by Karol on 2016-11-23.
 */

public class OrderTab extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.tab_order_layout, null);

        return myView;
    }

}