package com.crossover.medicalconferencereminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.DatabaseErrorHandler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ShowTopic extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String email="";
    int id=0;
    DBhelperTopic D;
    DBhelper Db;
    Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_topic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Topic Details");

        MyApplication mApp = ((MyApplication) getApplicationContext());

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_all_topics);
        TextView logotext=(TextView) headerView.findViewById(R.id.logotext);
        logotext.setText(mApp.getSomeVariable());
        ImageView logoimg=(ImageView) headerView.findViewById(R.id.logoimage);
        ContextWrapper cw1 = new ContextWrapper(getApplicationContext());
        File directory1 = cw1.getDir("imageDir", Context.MODE_PRIVATE);

        try {
            File f=new File(directory1.getAbsolutePath(), mApp.getSomeVariable());
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            logoimg.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.mainlayout);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayout.setPadding(0,100,0,0);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            linearLayout.setPadding(0,200,0,0);
        }
        email=getIntent().getStringExtra("email");
        id=getIntent().getIntExtra("id",0);

        TextView title=(TextView) findViewById(R.id.title);
        TextView description=(TextView) findViewById(R.id.description);
        TextView name=(TextView) findViewById(R.id.name);
        TextView date=(TextView) findViewById(R.id.date);
        Button addcaledit=(Button) findViewById(R.id.addcaledit);
        Button btndlt=(Button) findViewById(R.id.btndlt);
        ImageView impimage=(ImageView) findViewById(R.id.impimage);

        D=new DBhelperTopic(getApplicationContext());
        topic=D.getTopicByID(id);

        Db=new DBhelper(getApplicationContext());
        ArrayList<person> P;
        P=Db.getSelectedPersonByEmail(topic.getDoctoremail());
        person pp=P.get(0);
        if (title != null) {
            title.setText(topic.getTitle());
        }
        if (description != null) {
            description.setText(topic.getDescription());
        }
        if (name != null) {
            name.setText(pp.getName());
        }
        if (date != null) {
            date.setText(topic.getDate());
        }
        if(P!=null && P.size()>0) {
            if(pp.getImage().equals("")) impimage.setImageResource(R.drawable.noimage);
            else{
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

                try {
                    File f=new File(directory.getAbsolutePath(), pp.getImage());
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    if (impimage != null) {
                        impimage.setImageBitmap(b);
                    }
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }

            }
        }
        if(Db.gettypebyemail(email).equals("admin"))addcaledit.setText("Add To Calender");
        else if(!topic.getDoctoremail().equals(email)){
            btndlt.setVisibility(View.INVISIBLE);
            addcaledit.setVisibility(View.INVISIBLE);
        }
    }

    void addcaleditButton(View view){
        if(Db.gettypebyemail(email).equals("admin")){
            Intent goToNextActivity = new Intent(getApplicationContext(), AddInvitations.class);
            goToNextActivity.putExtra("title", topic.getTitle());
            goToNextActivity.putExtra("description", topic.getDescription());
            startActivity(goToNextActivity);
        }
        else {
            Intent goToNextActivity = new Intent(getApplicationContext(), suggestTopic.class);
            goToNextActivity.putExtra("title", topic.getTitle());
            goToNextActivity.putExtra("description", topic.getDescription());
            goToNextActivity.putExtra("email", email);
            goToNextActivity.putExtra("id", topic.getId());
            startActivity(goToNextActivity);
        }
    }

    void dlttopicButton(View view){
        try{
            D.deleteTopic(id);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowTopic.this);
            builder1.setMessage("Topic Deleted.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            finish();
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTitle("Topic Details");

        TextView title=(TextView) findViewById(R.id.title);
        TextView description=(TextView) findViewById(R.id.description);
        TextView name=(TextView) findViewById(R.id.name);
        TextView date=(TextView) findViewById(R.id.date);
        ImageView impimage=(ImageView) findViewById(R.id.impimage);

        D=new DBhelperTopic(getApplicationContext());
        topic=D.getTopicByID(id);

        Db=new DBhelper(getApplicationContext());
        ArrayList<person> P;
        P=Db.getSelectedPersonByEmail(topic.getDoctoremail());
        person pp=P.get(0);
        if (title != null) {
            title.setText(topic.getTitle());
        }
        if (description != null) {
            description.setText(topic.getDescription());
        }
        if (name != null) {
            name.setText(pp.getName());
        }
        if (date != null) {
            date.setText(topic.getDate());
        }
        if(P!=null && P.size()>0) {
            if(pp.getImage().equals("")) impimage.setImageResource(R.drawable.noimage);
            else{
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

                try {
                    File f=new File(directory.getAbsolutePath(), pp.getImage());
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    if (impimage != null) {
                        impimage.setImageBitmap(b);
                    }
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }

            }
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
        getMenuInflater().inflate(R.menu.show_topic, menu);
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

        if (id == R.id.sign_out) {
            // Handle the camera action
            Intent mStartActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mStartActivity);
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
