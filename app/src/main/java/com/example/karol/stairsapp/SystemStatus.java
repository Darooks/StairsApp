package com.example.karol.stairsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

/**
 * Created by Karol on 2016-11-30.
 */

public class SystemStatus {

    private int passageCount;
    private int sensorsCount;
    private int ledsCount;
    private int espAvailableRam;
    private int avrAvailableRam;

    public SystemStatus() {
        this.passageCount = 0;
        this.sensorsCount = 0;
        this.espAvailableRam = 0;
        this.ledsCount = 0;
        this.avrAvailableRam = 0;
    }

    public int getAvrAvailableRam() {
        return avrAvailableRam;
    }

    public int getEspAvailableRam() {
        return espAvailableRam;
    }

    public int getLedsCount() {
        return ledsCount;
    }

    public int getSensorsCount() {
        return sensorsCount;
    }

    public int getPassageCount() {
        return passageCount;
    }

    public void setPassageCount(int passageCount) {
        this.passageCount = passageCount;
    }

    public void setSensorsCount(int sensorsCount) {
        this.sensorsCount = sensorsCount;
    }

    public void setLedsCount(int ledsCount) {
        this.ledsCount = ledsCount;
    }

    public void setEspAvailableRam(int espAvailableRam) {
        this.espAvailableRam = espAvailableRam;
    }

    public void setAvrAvailableRam(int avrAvailableRam) {
        this.avrAvailableRam = avrAvailableRam;
    }

    public void fromTreeMap(LinkedTreeMap<Object, Object> object) {
        double tempValue;
        if (object.containsKey("sensorsCount")) {
            tempValue = (double) object.get("sensorsCount");
            this.sensorsCount = (int) tempValue;
        }
        if (object.containsKey("passageCount")) {
            tempValue = (double) object.get("passageCount");
            this.passageCount = (int) tempValue;
        }
        if (object.containsKey("ledsCount")) {
            tempValue = (double) object.get("ledsCount");
            this.ledsCount = (int) tempValue;
        }
        if (object.containsKey("espAvailableRam")) {
            tempValue = (double) object.get("espAvailableRam");
            this.espAvailableRam = (int) tempValue;
        }
        if (object.containsKey("avrAvailableRam")) {
            tempValue = (double) object.get("avrAvailableRam");
            this.avrAvailableRam = (int) tempValue;
        }
    }
}