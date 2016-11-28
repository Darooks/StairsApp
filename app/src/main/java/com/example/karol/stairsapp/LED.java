package com.example.karol.stairsapp;

/**
 * Created by Karol on 2016-11-23.
 */

public class LED {
    public String ledId;
    public boolean active;

    public LED(String ledId) {
        this.ledId = ledId;
        this.active = false;
    }
}
