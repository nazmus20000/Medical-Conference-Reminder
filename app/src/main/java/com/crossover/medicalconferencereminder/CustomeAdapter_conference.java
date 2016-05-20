package com.crossover.medicalconferencereminder;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by nazmu on 5/7/2016.
 */
public class CustomeAdapter_conference extends ArrayAdapter<invitation>{

    public CustomeAdapter_conference(Context context, ArrayList<invitation> resource) {
        super(context, R.layout.custom_row ,resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.custom_row_conference,parent,false);
        invitation singletopic=getItem(position);
        TextView location=(TextView) view.findViewById(R.id.location);
        TextView title=(TextView) view.findViewById(R.id.title);
        location.setText("At "+singletopic.getLocation());
        title.setText(singletopic.getTitle());

        return view;
    }
}
