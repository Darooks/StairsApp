package com.example.karol.stairsapp.Fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.karol.stairsapp.ConfigurationTabs.OrderTab;
import com.example.karol.stairsapp.ConfigurationTabs.GeneralTab;
import com.example.karol.stairsapp.ConfigurationTabs.DebugTab;
import com.example.karol.stairsapp.R;

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
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

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

        tabLayout = (TabLayout) myView.findViewById(R.id.tabsConfiguration);
        viewPager = (ViewPager) myView.findViewById(R.id.viewpagerConfiguration);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return myView;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new OrderTab();
                case 1 : return new GeneralTab();
                case 2 : return new DebugTab();
            }

            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Kolejność";
                case 1 :
                    return "Ogólne";
                case 2 :
                    return "Debug";
            }
            return null;
        }
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
