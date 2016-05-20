package com.crossover.medicalconferencereminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.InjectView;

public class suggestTopic extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBhelperTopic D;
    EditText _title;
    EditText _description;
    String email="";
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_topic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Suggest Topics");

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

        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.mainlayout);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayout.setPadding(0,100,0,0);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            linearLayout.setPadding(0,200,0,0);
        }

        D=new DBhelperTopic(getApplicationContext());
        _title=(EditText)findViewById(R.id.title);
        _description=(EditText)findViewById(R.id.description);
        email=getIntent().getStringExtra("email");
        if(getIntent().hasExtra("title")){
            _title.setText(getIntent().getStringExtra("title"));
        }
        if(getIntent().hasExtra("description")){
            _description.setText(getIntent().getStringExtra("description"));
            Button btnsubmit=(Button) findViewById(R.id.btnsubmit);
            btnsubmit.setText("Update");
        }
        id=-1;
        if(getIntent().hasExtra("id")){
            id=getIntent().getIntExtra("id",0);
            Button btnsubmit=(Button) findViewById(R.id.btnsubmit);
            btnsubmit.setText("Update");
        }
    }

    public void submitButton(View view){
        final String title = _title.getText().toString();
        final String description = _description.getText().toString();
        long val=0;
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//        Toast.makeText(getBaseContext(), mydate, Toast.LENGTH_LONG).show();
        Topic a=new Topic(0,title,description,email,mydate);
        if(id==-1){
            val = D.InsertTopic(a);
        }
        else val = D.updatetopic(title,description,Integer.toString(id));
        if(val>0){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(suggestTopic.this);
            if(id==-1)builder1.setMessage("Topic Added.");
            else builder1.setMessage("Topic updated.");
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
//            Toast.makeText(getBaseContext(), "Topic Added", Toast.LENGTH_LONG).show();

        }
        else Toast.makeText(getBaseContext(), "Topic Insert Faild!", Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.suggest_topic, menu);
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
