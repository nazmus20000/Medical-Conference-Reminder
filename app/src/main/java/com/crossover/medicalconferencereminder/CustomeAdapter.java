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
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by nazmu on 5/7/2016.
 */
public class CustomeAdapter extends ArrayAdapter<Topic>{

    public CustomeAdapter(Context context, ArrayList<Topic> resource) {
        super(context, R.layout.custom_row ,resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(getContext());
        View view=layoutInflater.inflate(R.layout.custom_row,parent,false);
        Topic singletopic=getItem(position);
        TextView date=(TextView) view.findViewById(R.id.date);
        TextView name=(TextView) view.findViewById(R.id.name);
        TextView title=(TextView) view.findViewById(R.id.title);
        ImageView impimage= (ImageView) view.findViewById(R.id.impimage);
        String email=singletopic.getDoctoremail();

        DBhelper D=new DBhelper(getContext());
        ArrayList<person> P;
        P=D.getSelectedPersonByEmail(email);
        person pp=P.get(0);
        if(P!=null && P.size()>0) {
            if(pp.getImage().equals("")) impimage.setImageResource(R.drawable.noimage);
            else{
                ContextWrapper cw = new ContextWrapper(getContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

                try {
                    File f=new File(directory.getAbsolutePath(), pp.getImage());
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    impimage.setImageBitmap(b);
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }

            }
            name.setText(pp.getName());
        }
        date.setText(singletopic.getDate());
        title.setText(singletopic.getTitle());

        return view;
    }
}
