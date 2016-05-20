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

public class Registration extends AppCompatActivity {

    private static final String TAG = "Registration";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.email) EditText _emailText;
    @InjectView(R.id.password) EditText _passwordText;
    @InjectView(R.id.name) EditText _nameText;
    DBhelper D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle("Medical Conference Reminder");

        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.mainlayout);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayout.setPadding(0,80,0,0);

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            linearLayout.setPadding(0,150,0,0);
        }
        _emailText=(EditText)findViewById(R.id.email);
        _passwordText=(EditText)findViewById(R.id.password);
        _nameText=(EditText)findViewById(R.id.name);
    }
    void alreadyRegButton(View view)
    {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    void regButton(View view)
    {
        signup();
    }
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(Registration.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        long val=0;
                        D=new DBhelper(getApplicationContext());
                        ArrayList<person> P;
                        P=D.getSelectedPersonByEmail(email);
                        if(P!=null && P.size()>0) {
                            Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            person a=new person(email,password,name,"","doctor");
                            val = D.InsertPerson(a);
                            if(val>0){
                                onSignupSuccess();
                                Intent goToNextActivity = new Intent(getApplicationContext(), ImageUploadActivity.class);
                                goToNextActivity.putExtra("email", email);
                                startActivity(goToNextActivity);
                            }
                            else onSignupFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        Toast.makeText(getApplicationContext(), "Your Information is Successfully Saved", Toast.LENGTH_LONG).show();
        //setResult(RESULT_OK, null);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

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
