package com.example.karol.stairsapp;

//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.karol.stairsapp.Fragments.ActivityFragment;
import com.example.karol.stairsapp.Fragments.ConfigurationFragment;
import com.example.karol.stairsapp.Fragments.ConnectionFragment;
import com.example.karol.stairsapp.Fragments.StatusFragment;
import com.example.karol.stairsapp.Fragments.WifiConfigFragment;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<Sensor> SENSORS_ARRAY = new ArrayList<Sensor>();
    public static ArrayList<LED> LED_ARRAY = new ArrayList<LED>();
    public static List<String> COMPONENTS_ORDER_ARRAY = new ArrayList<String>();
    public static boolean isConnected = false;
    public static boolean isStateInited = false;

    public static SensorAdapter sensorAdapter;

    public LightConfiguration lightConfiguration = new LightConfiguration();
    public static SystemStatus systemStatus = new SystemStatus();

    public static String MQTTHOST = "tcp://m20.cloudmqtt.com:19951";
    public static String USERNAME = "hjlgqbkb";
    public static String PASSWORD = "k_fHdTL-RXmW";

    public static String publisherTopic = "esp-mqtt-topic";
    public static String subscriberTopic = "android-mqtt-topic";

    public static MqttAndroidClient clientPublisher = null;
    public static MqttAndroidClient clientSubscriber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitVariables();
        MakeConnectionToMqtt();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("Config", 0);

        MQTTHOST = sharedPreferences.getString("MQTTHOST", "tcp://m20.cloudmqtt.com:19951");
        USERNAME = sharedPreferences.getString("USERNAME", "hjlgqbkb");
        PASSWORD = sharedPreferences.getString("PASSWORD", "k_fHdTL-RXmW");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    public void InitVariables() {
        for (int i = 1; i <= 6; i++) {
            Sensor sensor = new Sensor("Sensor " + String.valueOf(i));
            SENSORS_ARRAY.add(sensor);
        }
        for (int i = 1; i <= 16; i++) {
            LED led = new LED("LED " + String.valueOf(i));
            LED_ARRAY.add(led);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_sensors) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ActivityFragment()).commit();
        } else if (id == R.id.nav_status) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ConfigurationFragment()).commit();
        } else if (id == R.id.nav_configuration) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new StatusFragment()).commit();
        } else if (id == R.id.nav_connection) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ConnectionFragment()).commit();
        } else if (id == R.id.nav_wificonfig) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new WifiConfigFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void MakeConnectionToMqtt() {
        /* <MQTT publisher> */
        clientPublisher = new MqttAndroidClient(this, MQTTHOST,
                "pahomqttpublish1");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = clientPublisher.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    if (isStateInited)
                        StatusFragment.handler.sendEmptyMessage(0);
                    isConnected = true;
                    Toast.makeText(getApplicationContext(), "Connected to mqtt", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure" + exception);
                    Toast.makeText(getApplicationContext(), "Publisher failed to connect to mqtt! " + exception, Toast.LENGTH_LONG).show();
                    System.out.print(exception);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        /* </MQTT publisher> */

        /* <MQTT subscriber> */
        clientSubscriber = new MqttAndroidClient(this, MQTTHOST,
                "pahomqttsubscribe1");
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = clientSubscriber.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    isConnected = true;
                    if (isStateInited)
                        StatusFragment.handler.sendEmptyMessage(0);
                    Log.d(TAG, "onSuccess");
                    SubscribeMqtt();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure" + exception);
                    Toast.makeText(getApplicationContext(), "Subscriber failed to connect to mqtt! " + exception, Toast.LENGTH_LONG).show();
                    System.out.print(exception);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        clientSubscriber.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Gson gson = new Gson();
                String messageString = new String(message.getPayload());
                Container container = gson.fromJson(messageString, Container.class);
                LinkedTreeMap map = (LinkedTreeMap) container.getObject();

                switch (container.getMsgType()) {
                    case "SYSTEM_STATUS":
                        systemStatus.fromTreeMap(map);
                        if (isStateInited)
                            StatusFragment.handler.sendEmptyMessage(0);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        /* <MQTT subscriber> */


    }

    private void SubscribeMqtt() {
        try {
            clientSubscriber.subscribe(subscriberTopic, 0);
        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void Reconnect() {
        Toast.makeText(this, "Reconnecting...", Toast.LENGTH_SHORT).show();
        try {
            clientPublisher.disconnect();
        } catch (MqttException e) {
            Toast.makeText(this, "Failed disconnection from MQTT publisher", Toast.LENGTH_LONG).show();
        }

        try {
            clientSubscriber.disconnect();
        } catch (MqttException e) {
            Toast.makeText(this, "Failed disconnection from MQTT subscriber", Toast.LENGTH_LONG).show();
        }

        clientPublisher.unregisterResources();
        clientSubscriber.unregisterResources();

        MakeConnectionToMqtt();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("MQTTHOST", MQTTHOST);
        outState.putString("USERNAME", USERNAME);
        outState.putString("PASSWORD", PASSWORD);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        MQTTHOST = savedInstanceState.getString("MQTTHOST", "tcp://m20.cloudmqtt.com:19951");
        USERNAME = savedInstanceState.getString("USERNAME", "hjlgqbkb");
        PASSWORD = savedInstanceState.getString("PASSWORD", "k_fHdTL-RXmW");

        Toast.makeText(this, "Saved instance " + MQTTHOST, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences("Config", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("MQTTHOST", MQTTHOST);
        editor.putString("USERNAME", USERNAME);
        editor.putString("PASSWORD", PASSWORD);
        editor.commit();
    }
}
