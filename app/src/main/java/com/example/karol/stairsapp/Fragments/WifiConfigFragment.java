package com.example.karol.stairsapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.karol.stairsapp.R;

/**
 * Created by Karol on 2016-12-05.
 */

public class WifiConfigFragment extends Fragment {

    View myView;
    Button redirectionBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.wifi_config_layout, container, false);

        redirectionBtn = (Button) myView.findViewById(R.id.redirection_button);
        redirectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse("http://192.168.4.1"));

                startActivity(httpIntent);
            }
        });

        return myView;
    }
}
