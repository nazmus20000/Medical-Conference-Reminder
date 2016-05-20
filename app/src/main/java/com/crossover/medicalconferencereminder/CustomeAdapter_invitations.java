package com.crossover.medicalconferencereminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nazmu on 5/7/2016.
 */
public class CustomeAdapter_invitations extends ArrayAdapter<Particularinvitation>{

    public CustomeAdapter_invitations(Context context, ArrayList<Particularinvitation> resource) {
        super(context, R.layout.custom_row ,resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.custom_row_invitations,parent,false);
        Particularinvitation singletopic=getItem(position);
        TextView location=(TextView) view.findViewById(R.id.location);
        TextView title=(TextView) view.findViewById(R.id.title);
        location.setText("At "+singletopic.getLocation());
        title.setText(singletopic.getTitle());
        TextView status=(TextView) view.findViewById(R.id.status);
        status.setText(singletopic.getStatus());
        if(singletopic.getStatus().equals("Not Going"))status.setTextColor(view.getResources().getColor(R.color.ntgoing));
        else if(singletopic.getStatus().equals("Going"))status.setTextColor(view.getResources().getColor(R.color.going));
        else status.setTextColor(view.getResources().getColor(R.color.pending));
        return view;
    }
}
