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
import java.util.Collections;

public class RecentInvitation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBhelperInvitedPeople db;
    String email="";
    ArrayList<Particularinvitation> P;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_invitation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        db=new DBhelperInvitedPeople(getApplicationContext());
        email=getIntent().getStringExtra("email");

        DBhelper D=new DBhelper(getApplicationContext());
        int id=D.getidbyemail(email);

        ArrayList<InvitedPeople> tt=new ArrayList<InvitedPeople>();
        tt=db.getAllInvitedPeoplebyid(id);
        P=new ArrayList<Particularinvitation>();
        if(tt!=null && tt.size()>0) {
            DBhelperInvitation Db=new DBhelperInvitation(getApplicationContext());

            for(int i=0;i<tt.size();i++){
                InvitedPeople invitedPeople=tt.get(i);
                invitation inv=Db.getInvitation(invitedPeople.getInvitationid());
                Particularinvitation particularinvitation=new Particularinvitation(inv.getTitle(),inv.getDescription(),inv.getLocation(),inv.getDate(),invitedPeople.getStatus());
                P.add(particularinvitation);
            }

            ListAdapter listAdapter=new CustomeAdapter_invitations(this,P);
            ListView listView=(ListView) findViewById(R.id.listviewbasic);
            listView.setAdapter(listAdapter);
            final ArrayList<InvitedPeople> finalTt = tt;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    final Particularinvitation topic=P.get(position);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RecentInvitation.this);
                    builder1.setMessage("Do you want to add this event to your calender?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Going",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    db.updatestatus("Going",Integer.toString(finalTt.get(position).getId()));
                                    finish();
                                    dialog.cancel();
                                    Intent goToNextActivity = new Intent(getApplicationContext(), RecentInvitation.class);
                                    goToNextActivity.putExtra("email", email);
                                    startActivity(goToNextActivity);
                                    Calendar cal = Calendar.getInstance();
                                    Intent intent = new Intent(Intent.ACTION_EDIT);
                                    intent.setType("vnd.android.cursor.item/event");
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, topic.getDate());
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,topic.getDate());
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                                    intent.putExtra(CalendarContract.Events.TITLE, topic.getTitle());
                                    intent.putExtra(CalendarContract.Events.DESCRIPTION, topic.getDescription());
                                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, topic.getLocation());
                                    intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
                                    startActivity(intent);
                                }
                            });

                    builder1.setNegativeButton(
                            "Not Interested",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    db.updatestatus("Not Going",Integer.toString(finalTt.get(position).getId()));
                                    finish();
                                    dialog.cancel();
                                    Intent goToNextActivity = new Intent(getApplicationContext(), RecentInvitation.class);
                                    goToNextActivity.putExtra("email", email);
                                    startActivity(goToNextActivity);
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No New Invitation", Toast.LENGTH_LONG).show();
            finish();
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
        getMenuInflater().inflate(R.menu.recent_invitation, menu);
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
