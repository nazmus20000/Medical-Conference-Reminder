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
public class CustomeAdapter_person extends ArrayAdapter<person>{

    ArrayList<String> statuses;

    public CustomeAdapter_person(Context context, ArrayList<person> resource, ArrayList<String> status) {
        super(context, R.layout.custom_row ,resource);
        this.statuses=status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.custom_row_person,parent,false);
        person singletopic=getItem(position);
        TextView name=(TextView) view.findViewById(R.id.name);
        ImageView impimage= (ImageView) view.findViewById(R.id.impimage);
        if(singletopic.getImage().equals("")) impimage.setImageResource(R.drawable.noimage);
        else{
            ContextWrapper cw = new ContextWrapper(getContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

            try {
                File f=new File(directory.getAbsolutePath(), singletopic.getImage());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                impimage.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        name.setText(singletopic.getName());
        TextView status=(TextView) view.findViewById(R.id.status);
        status.setText(statuses.get(position));
        if(statuses.get(position).equals("Invite"))status.setTextColor(view.getResources().getColor(R.color.white));
        else if(statuses.get(position).equals("Not Going"))status.setTextColor(view.getResources().getColor(R.color.ntgoing));
        else if(statuses.get(position).equals("Going"))status.setTextColor(view.getResources().getColor(R.color.going));
        else status.setTextColor(view.getResources().getColor(R.color.pending));
        return view;
    }
}
