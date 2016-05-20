package com.crossover.medicalconferencereminder;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBhelper D;
    String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.mainlayout);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayout.setPadding(0,100,0,0);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            linearLayout.setPadding(0,200,0,0);
        }

        Button suggestTopicConferences=(Button) findViewById(R.id.suggestTopicConferences);
        Button newsendinvitation=(Button) findViewById(R.id.newsendinvitation);
        Button newoldtopic=(Button) findViewById(R.id.newoldtopic);

        D=new DBhelper(getApplicationContext());

        email=getIntent().getStringExtra("email");

        ArrayList<person> logohealper=new ArrayList<person>();
        logohealper=D.getSelectedPersonByEmail(email);

        MyApplication mApp = ((MyApplication) getApplicationContext());

        mApp.setSomeVariable(logohealper.get(0).getName());
        mApp.setBp(logohealper.get(0).getImage());

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_all_topics);
        TextView logotext=(TextView) headerView.findViewById(R.id.logotext);
        logotext.setText(mApp.getSomeVariable());
        ImageView logoimg=(ImageView) headerView.findViewById(R.id.logoimage);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        try {
            File f=new File(directory.getAbsolutePath(), mApp.getSomeVariable());
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            logoimg.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        if(D.gettypebyemail(email).equals("admin")){
            suggestTopicConferences.setText("Add Invitations");
            newsendinvitation.setText("Send Invitations");
        }
    }
    public void suggestTopicConferencesClicked(View view){
        if(D.gettypebyemail(email).equals("admin")){
            Intent goToNextActivity = new Intent(getApplicationContext(), AddInvitations.class);
            startActivity(goToNextActivity);
        }
        else{
            Intent goToNextActivity = new Intent(getApplicationContext(), suggestTopic.class);
            goToNextActivity.putExtra("email", email);
            startActivity(goToNextActivity);
        }
    }

    public void newsendinvitationClicked(View view){
        if(D.gettypebyemail(email).equals("admin")){
            Intent goToNextActivity = new Intent(getApplicationContext(), Conference_Show.class);
            startActivity(goToNextActivity);
        }
        else{
            Intent goToNextActivity = new Intent(getApplicationContext(), RecentInvitation.class);
            goToNextActivity.putExtra("email", email);
            startActivity(goToNextActivity);
        }
    }

    public void newoldtopicClicked(View view){
        Intent goToNextActivity = new Intent(getApplicationContext(), AllTopics.class);
        goToNextActivity.putExtra("email", email);
        startActivity(goToNextActivity);
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
        getMenuInflater().inflate(R.menu.dashboard, menu);
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
