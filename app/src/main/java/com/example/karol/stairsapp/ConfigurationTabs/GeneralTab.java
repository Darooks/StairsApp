package com.example.karol.stairsapp.ConfigurationTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.karol.stairsapp.Container;
import com.example.karol.stairsapp.GlobalLedConfiguration;
import com.example.karol.stairsapp.MainActivity;
import com.example.karol.stairsapp.R;
import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

/**
 * Created by Karol on 2016-11-23.
 */

public class GeneralTab extends Fragment {

    View myView;

    Spinner lightType_Spinner;
    SeekBar brightness_SeekBar;
    EditText delay;

    GlobalLedConfiguration globalLedConfiguration;

    MqttAndroidClient client = MainActivity.clientPublisher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.tab_generalconfig_layout, container, false);
        globalLedConfiguration = new GlobalLedConfiguration();

        /* Spinner */
        lightType_Spinner = (Spinner) myView.findViewById(R.id.lightType_spinner);
        ArrayAdapter<CharSequence> lightType_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.typeOfLight_list, android.R.layout.simple_spinner_dropdown_item);
        lightType_Spinner.setAdapter(lightType_adapter);
        lightType_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (lightType_Spinner.getSelectedItem().toString().matches("Pełny"))
                    globalLedConfiguration.setLightnessMode("FULL");
                else if (lightType_Spinner.getSelectedItem().toString().matches("Sekwencyjny"))
                    globalLedConfiguration.setLightnessMode("PARTIAL");
                PublishData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /* SeekBar */
        brightness_SeekBar = (SeekBar) myView.findViewById(R.id.brightness_SeekBar);
        brightness_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                globalLedConfiguration.setMaximalBrightness(brightness_SeekBar.getProgress());
                PublishData();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /* EditText */
        delay = (EditText) myView.findViewById(R.id.delay_editText);
        delay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!delay.getText().toString().matches("")) {
                    globalLedConfiguration.setDelay(Integer.parseInt(delay.getText().toString()));
                    PublishData();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return myView;
    }

    private void PublishData() {
        Container container = new Container();
        container.setMsgType("GLOBAL_LED_CONFIG");
        container.setObject(globalLedConfiguration);

        String messageToSend = container.getStringJson();
        Toast.makeText(getActivity(), messageToSend, Toast.LENGTH_LONG).show();

        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = messageToSend.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(MainActivity.publisherTopic, message); // client.publish(topic, message.getBytes(), 0, false);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }
}