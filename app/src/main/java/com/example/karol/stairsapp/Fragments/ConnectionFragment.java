package com.example.karol.stairsapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.karol.stairsapp.MainActivity;
import com.example.karol.stairsapp.R;

/**
 * Created by Karol on 2016-11-23.
 */

public class ConnectionFragment extends Fragment {
    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.connection_layout, container, false);

        EditText serverTxt = (EditText) myView.findViewById(R.id.serverText);
        EditText userTxt = (EditText) myView.findViewById(R.id.userText);
        EditText passwordTxt = (EditText) myView.findViewById(R.id.passwordText);

        serverTxt.setText(MainActivity.MQTTHOST);
        userTxt.setText(MainActivity.USERNAME);
        passwordTxt.setText(MainActivity.PASSWORD);

        Button connectBtn = (Button) myView.findViewById(R.id.connect_btn);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText serverTxt = (EditText) myView.findViewById(R.id.serverText);
                EditText userTxt = (EditText) myView.findViewById(R.id.userText);
                EditText passwordTxt = (EditText) myView.findViewById(R.id.passwordText);

                MainActivity.MQTTHOST = serverTxt.getText().toString();
                MainActivity.USERNAME = userTxt.getText().toString();
                MainActivity.PASSWORD = passwordTxt.getText().toString();

                ((MainActivity) getActivity()).Reconnect();
            }
        });

        return myView;
    }
}
