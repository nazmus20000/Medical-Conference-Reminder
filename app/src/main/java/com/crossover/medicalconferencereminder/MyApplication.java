package com.crossover.medicalconferencereminder;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by nazmu on 5/19/2016.
 */
public class MyApplication extends Application {

    private String logotextstring;
    private String bp;

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getSomeVariable() {
        return logotextstring;
    }

    public void setSomeVariable(String someVariable) {
        this.logotextstring = someVariable;
    }
}
