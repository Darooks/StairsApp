package com.example.karol.stairsapp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2016-11-23.
 */

public class Sensor {
    public String sensorId;
    public boolean active;

    public Sensor(String sensorId) {
        this.sensorId = sensorId;
        this.active = true;
    }
}
