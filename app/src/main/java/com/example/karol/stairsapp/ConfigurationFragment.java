package com.example.karol.stairsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    static String MQTTHOST = "tcp://m20.cloudmqtt.com:19951";
    static String USERNAME = "hjlgqbkb";
    static String PASSWORD = "k_fHdTL-RXmW";
    String topicStr = "esp-mqtt-topic";
    MqttAndroidClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.konfiguracja_layout, container, false);


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

        Button publish_button = (Button) myView.findViewById(R.id.button_publish);
        publish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Publish(myView);
            }
        });

        return myView;
    }

    public void Publish(View v) {
        String topic = topicStr;
        String payload = "Hello from MQTT";
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message); // client.publish(topic, message.getBytes(), 0, false);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "Sent the message", Toast.LENGTH_LONG).show();
    }
}
