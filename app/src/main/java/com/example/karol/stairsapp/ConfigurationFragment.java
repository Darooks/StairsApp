package com.example.karol.stairsapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

import static android.content.ContentValues.TAG;

/**
 * Created by Karol on 2016-10-22.
 */

public class ConfigurationFragment extends Fragment{

    View myView;
    private int brightness = 0;

    static String MQTTHOST = "tcp://m20.cloudmqtt.com:19951";
    static String USERNAME = "hjlgqbkb";
    static String PASSWORD = "k_fHdTL-RXmW";
    static String topicStr = "esp-mqtt-topic";
    MqttAndroidClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.configuration_layout, container, false);
        MakeConnection();
        final SeekBar light_seekbar = (SeekBar) myView.findViewById(R.id.seekbar_brightness);
        light_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightness = light_seekbar.getProgress();
                SendBrightness();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        try {
            client.disconnect();
        }
        catch (Exception e) {

        }
        return myView;
    }

    private void MakeConnection() {
        /* <MQTT connection> */
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getActivity(), MQTTHOST,
                "pahomqttpublish1");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    Toast.makeText(getActivity(), "Connected to mqtt", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure" + exception);
                    Toast.makeText(getActivity(), "Failed to connect to mqtt! " + exception, Toast.LENGTH_LONG).show();
                    System.out.print(exception);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        /* </MQTT connection> */
    }
    public void SendBrightness() {
        /* Prepare message to send */
        String string_message = "{\"msgType\":\"BRIGHTNESS\", \"object\":{\"value\":" + brightness + "}}";

        String topic = topicStr;
        //String payload = "Hello from MQTT";
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
