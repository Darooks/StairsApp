package com.example.karol.stairsapp;

/**
 * Created by Karol on 2016-11-28.
 */

public class GlobalLedConfiguration {
    private int maximalBrightness;
    private String lightnessMode;
    private int delay;

    public GlobalLedConfiguration() {  }

    public int getMaximalBrightness() {
        return maximalBrightness;
    }

    public void setMaximalBrightness(int maximalBrightness) {
        this.maximalBrightness = maximalBrightness;
    }

    public String getLightnessMode() {
        return lightnessMode;
    }

    public void setLightnessMode(String lightnessMode) {
        this.lightnessMode = lightnessMode;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
