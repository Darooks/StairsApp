package com.example.karol.stairsapp;

import java.io.Serializable;

import static android.R.attr.value;

/**
 * Created by Karol on 2016-11-09.
 */

public class LightConfiguration implements Serializable{

    private int value;

    public LightConfiguration() { value = 0; };

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
