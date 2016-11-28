package com.example.karol.stairsapp;

import com.google.gson.Gson;

/**
 * Created by Karol on 2016-11-28.
 */

public class Container {
    private String msgType;
    private Object object;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getStringJson() {
        Gson gson = new Gson();

        return  gson.toJson(this);
    }
}
