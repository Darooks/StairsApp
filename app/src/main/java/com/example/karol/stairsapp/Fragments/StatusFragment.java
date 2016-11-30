package com.example.karol.stairsapp.Fragments;

import android.net.wifi.p2p.WifiP2pManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karol.stairsapp.Container;
import com.example.karol.stairsapp.MainActivity;
import com.example.karol.stairsapp.R;
import com.example.karol.stairsapp.SystemStatus;
import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.util.Strings;

import java.io.UnsupportedEncodingException;

import static android.content.ContentValues.TAG;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Karol on 2016-10-22.
 */

public class StatusFragment extends Fragment {

    public static View myView;

    static String MQTTHOST = "tcp://m20.cloudmqtt.com:19951";
    static String USERNAME = "hjlgqbkb";
    static String PASSWORD = "k_fHdTL-RXmW";
    String topicStr = "esp-mqtt-topic";

    private TextView sensorState_tv;
    private TextView connectionState_tv;

    private TextView reactionCount_tv;
    private TextView activeSensor_tv;
    private TextView ramEsp_tv;
    private TextView ramAvr_tv;

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            TextView sensorState_tv = (TextView) myView.findViewById(R.id.sensorState_textView);
            TextView connectionState_tv = (TextView) myView.findViewById(R.id.connectionState_textView);

            TextView reactionCount_tv = (TextView) myView.findViewById(R.id.reactionCount_textView);
            TextView activeSensor_tv = (TextView) myView.findViewById(R.id.activeSensor_textView);
            TextView ramEsp_tv = (TextView) myView.findViewById(R.id.ramESP_textView);
            TextView ramAvr_tv = (TextView) myView.findViewById(R.id.ramAVRtextView);

            if (MainActivity.isConnected) {
                connectionState_tv.setText("Połączono");
            } else {
                connectionState_tv.setText("Brak połączenia");
            }

            reactionCount_tv.setText(String.valueOf(MainActivity.systemStatus.getPassageCount()));
            activeSensor_tv.setText(String.valueOf(MainActivity.systemStatus.getSensorsCount()));
            ramEsp_tv.setText(String.valueOf(MainActivity.systemStatus.getEspAvailableRam()));
            ramAvr_tv.setText(String.valueOf(MainActivity.systemStatus.getAvrAvailableRam()));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.status_layout, container, false);
        PublishRequest();
        InitValues();

        handler.sendEmptyMessage(0);
        UpdateData();

        return myView;
    }

    private void InitValues() {
        sensorState_tv = (TextView) myView.findViewById(R.id.sensorState_textView);
        connectionState_tv = (TextView) myView.findViewById(R.id.connectionState_textView);

        reactionCount_tv = (TextView) myView.findViewById(R.id.reactionCount_textView);
        activeSensor_tv = (TextView) myView.findViewById(R.id.activeSensor_textView);
        ramEsp_tv = (TextView) myView.findViewById(R.id.ramESP_textView);
        ramAvr_tv = (TextView) myView.findViewById(R.id.ramAVRtextView);

        MainActivity.isStateInited = true;
    }

    public void UpdateData() {
        if (MainActivity.isConnected) {
            connectionState_tv.setText("Połączono");
        } else {
            connectionState_tv.setText("Brak połączenia");
        }

        reactionCount_tv.setText(String.valueOf(MainActivity.systemStatus.getPassageCount()));
        activeSensor_tv.setText(String.valueOf(MainActivity.systemStatus.getSensorsCount()));
        ramEsp_tv.setText(String.valueOf(MainActivity.systemStatus.getEspAvailableRam()));
        ramAvr_tv.setText(String.valueOf(MainActivity.systemStatus.getAvrAvailableRam()));
    }

    private void PublishRequest() {
        Container container = new Container();
        container.setMsgType("SYSTEM_STATUS_REQUEST");

        String messageToSend = container.getStringJson();

        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = messageToSend.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            MainActivity.clientPublisher.publish(MainActivity.publisherTopic, message); // client.publish(topic, message.getBytes(), 0, false);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }
}
