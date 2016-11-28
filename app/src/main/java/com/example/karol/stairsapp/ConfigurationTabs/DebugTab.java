package com.example.karol.stairsapp.ConfigurationTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.karol.stairsapp.MainActivity;
import com.example.karol.stairsapp.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

/**
 * Created by Karol on 2016-11-23.
 */

public class DebugTab extends Fragment {

//    static String MQTTHOST = "tcp://m20.cloudmqtt.com:19951";
//    static String USERNAME = "hjlgqbkb";
//    static String PASSWORD = "k_fHdTL-RXmW";
    static String topicStr = "esp-mqtt-topic";
    MqttAndroidClient client = MainActivity.clientPublisher;
    private int brightness = 0;

    View myView;
    private SeekBar brightnessSeekBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.tab_debug_layout, container, false);

        brightnessSeekBar = (SeekBar) myView.findViewById(R.id.brightnessSeekBarDebugMode);

        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightness = brightnessSeekBar.getProgress();
                SendBrightness();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return myView;
    }

    public void SendBrightness() {
        /* Prepare message to send */
        String string_message = "{\"msgType\":\"BRIGHTNESS\", \"object\":{\"value\":" + brightness + "}}";

        String topic = topicStr;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = string_message.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message); // client.publish(topic, message.getBytes(), 0, false);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }
}