package com.crossover.medicalconferencereminder;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ImageUploadActivity extends AppCompatActivity {
    String img="",email="";
    DBhelper D;
    ImageView UpIm=null;
    private static final int RESULT_LOAD_IMAGE=1;
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        setTitle("Medical Conference Reminder");

        UpIm=(ImageView) findViewById(R.id.imageid);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            try{
                UpIm.getLayoutParams().height=300;
                UpIm.getLayoutParams().width=300;
            }catch (NullPointerException e){}
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            try{
                UpIm.getLayoutParams().height=500;
                UpIm.getLayoutParams().width=500;
            }catch (Exception e){}
        }
        D=new DBhelper(getApplicationContext());
        email=getIntent().getStringExtra("email");
    }
    public String random() {
        String id=Integer.toString(D.getidbyemail(email));
        id="image"+id;
        return id;
    }

    private String saveToInternalStorage(Bitmap bitmapImage) throws IOException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        String random=random();
        random+=".jpg";
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,random);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return random;
    }
    public void browsepic(View V){

        Intent gallaryInsert=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryInsert,RESULT_LOAD_IMAGE);
    }
    public void uploadClicked(View view){
        if(!UpIm.equals(null))
        {
            Bitmap imm=((BitmapDrawable)UpIm.getDrawable()).getBitmap();
            try{
                if(flag==1) {
//                    Toast.makeText(getBaseContext(), "Flag1", Toast.LENGTH_LONG).show();
                    img = saveToInternalStorage(imm);
                }
            }catch (IOException e){
                Toast.makeText(getBaseContext(), "Image Failed", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            String id=Integer.toString(D.getidbyemail(email));
            D.updateprofile(img,id);
            Intent goToNextActivity = new Intent(getApplicationContext(), Dashboard.class);
            goToNextActivity.putExtra("email", email);
            startActivity(goToNextActivity);
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE&&resultCode==RESULT_OK&&data!=null)
        {
            Uri select=data.getData();
            UpIm.setImageURI(select);
            flag=1;
        }
    }
}
