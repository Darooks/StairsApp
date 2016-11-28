package com.example.karol.stairsapp;

//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.Fragment;
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

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<Sensor> SENSORS_ARRAY = new ArrayList<Sensor>();
    public static ArrayList<LED> LED_ARRAY = new ArrayList<LED>();

    public static SensorAdapter sensorAdapter;

    public LightConfiguration lightConfiguration = new LightConfiguration();

    static String MQTTHOST = "tcp://m20.cloudmqtt.com:19951";
    static String USERNAME = "hjlgqbkb";
    static String PASSWORD = "k_fHdTL-RXmW";
    public static String publisherTopic = "esp-mqtt-topic";
    public static MqttAndroidClient clientPublisher = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitVariables();
        MakeConnectionToMqtt();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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

        /* Wykonianie obiektu ktory ma byc przesylany pomiedzy fragmentami */
//        android.support.v4.app.FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

//        Bundle bundle = new Bundle();

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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void MakeConnectionToMqtt() {
        /* <MQTT connection> */
        String clientId = MqttClient.generateClientId();
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
                    Toast.makeText(getApplicationContext(), "Connected to mqtt", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure" + exception);
                    Toast.makeText(getApplicationContext(), "Failed to connect to mqtt! " + exception, Toast.LENGTH_LONG).show();
                    System.out.print(exception);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        /* </MQTT connection> */
    }
}
