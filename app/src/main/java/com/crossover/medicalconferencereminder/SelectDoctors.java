package com.crossover.medicalconferencereminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

public class SelectDoctors extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int confid=0;
    DBhelper D;
    ArrayList<person>P;
    ListAdapter listAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Available Doctors");

        MyApplication mApp = ((MyApplication) getApplicationContext());

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

        confid=getIntent().getIntExtra("id",0);

        D=new DBhelper(getApplicationContext());
        P=D.getAllPerson();
        if(P!=null && P.size()>0) {
            ArrayList<String> status=new ArrayList<String>();
            for(int i=0;i<P.size();i++){
                person topic=P.get(i);
                long val=0;
                int tempid=D.getidbyemail(topic.getEmail());
                DBhelperInvitedPeople Db=new DBhelperInvitedPeople(getApplicationContext());
                ArrayList<InvitedPeople> N;
                N=Db.getInvitation(tempid,confid);
                if(N!=null && N.size()>0) {
//                    status.add("Already Invited");
                    status.add(N.get(0).getStatus());
                }else{
                    status.add("Invite");
                }
            }
            listAdapter=new CustomeAdapter_person(this,P,status);
            listView=(ListView) findViewById(R.id.listviewbasic);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final person topic=P.get(position);
                    long val=0;
                    final int tempid=D.getidbyemail(topic.getEmail());
                    final DBhelperInvitedPeople Db=new DBhelperInvitedPeople(getApplicationContext());
                    ArrayList<InvitedPeople> N;
                    N=Db.getInvitation(tempid,confid);
                    if(N!=null && N.size()>0) {
//                        Toast.makeText(getApplicationContext(), "Already Invited", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SelectDoctors.this);
                        builder1.setMessage("Already Invited");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SelectDoctors.this);
                        builder1.setMessage("Do you want to invite Dr. "+topic.getName());
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Invite",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        long val1=0;
                                        InvitedPeople a=new InvitedPeople(0,tempid,confid,"Pending");
                                        val1 = Db.InsertInvitation(a);
                                        if(val1>0){
//                                          Toast.makeText(getApplicationContext(), "You Invited Dr. "+topic.getName(), Toast.LENGTH_LONG).show();
                                            AlertDialog.Builder builder2 = new AlertDialog.Builder(SelectDoctors.this);
                                            builder2.setMessage("You Invited Dr. "+topic.getName());
                                            builder2.setCancelable(true);

                                            builder2.setPositiveButton(
                                                    "Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                            Intent goToNextActivity = new Intent(getApplicationContext(), SelectDoctors.class);
                                                            goToNextActivity.putExtra("id", confid);
                                                            startActivity(goToNextActivity);
                                                            finish();
                                                        }
                                                    });

                                            AlertDialog alert12 = builder2.create();
                                            alert12.show();
                                        }
                                        else Toast.makeText(getApplicationContext(), "Invitation Failed", Toast.LENGTH_LONG).show();
                                    }
                                });
                        builder1.setNegativeButton(
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            });
        }
        else {
//            Toast.makeText(getApplicationContext(), "No Doctor Available", Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(SelectDoctors.this);
            builder1.setMessage("No Doctor Available");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
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
        getMenuInflater().inflate(R.menu.selecte_doctors, menu);
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
