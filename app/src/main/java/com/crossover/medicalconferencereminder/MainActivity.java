package com.crossover.medicalconferencereminder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.email) EditText _emailText;
    @InjectView(R.id.password) EditText _passwordText;
    DBhelper D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.mainlayout);

        setTitle("Medical Conference Reminder");

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayout.setPadding(0,80,0,0);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            linearLayout.setPadding(0,200,0,0);
        }

        D=new DBhelper(getApplicationContext());
        ArrayList<person> P;
        P=D.getSelectedPersonByEmail("admin@g.com");
        if(P!=null && P.size()>0);
        else
        {
            person a=new person("admin@g.com","12345678","admin","","admin");
            D.InsertPerson(a);
        }

        _emailText=(EditText)findViewById(R.id.email);
        _passwordText=(EditText)findViewById(R.id.password);
    }

    void notAmember(View view)
    {
        Intent intent=new Intent(getApplicationContext(),Registration.class);
        startActivity(intent);
        finish();
    }

    void loginClicked(View view)
    {
        login();
    }
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        long val=0;
                        D=new DBhelper(getApplicationContext());
                        ArrayList<person> P;
                        P=D.getSelectedPersonByEmailPass(email,password);
                        if(P!=null && P.size()>0) {
                            onLoginSuccess();
                            Intent goToNextActivity = new Intent(getApplicationContext(), Dashboard.class);
                            goToNextActivity.putExtra("email", email);
                            startActivity(goToNextActivity);
                        }
                        else
                        {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
