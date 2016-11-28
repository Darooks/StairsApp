package com.example.karol.stairsapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2016-11-23.
 */

public class LedAdapter extends ArrayAdapter<LED> {

    private List<LED> ledList;
    private Context context;

    public LedAdapter(ArrayList<LED> ledList, Context context) {
        super(context, R.layout.tab_led_layout, ledList);
        this.ledList = ledList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ledList = MainActivity.LED_ARRAY;

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.row, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.name_chk_box);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.chk_box);

        name.setText(ledList.get(position).ledId);
        cb.setChecked(ledList.get(position).active);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.LED_ARRAY.get(position).active)
                    MainActivity.LED_ARRAY.get(position).active = true;
                else
                    MainActivity.LED_ARRAY.get(position).active = false;
            }
        });

        return convertView;
    }
}
