package com.crossover.medicalconferencereminder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by acer on 2/13/2016.
 */
public class DBhelper extends SQLiteOpenHelper {

    public static final String DB_NAME="profile";
    public static final int version=1;

    public static final String TABLE_NAME="profile";
    final static String ITEM_ID_COLUMN="id";
    final static String ITEM_NAME_COLUMN="name";
    final static String ITEM_EMAIL_COLUMN="email";
    final static String ITEM_PASSWORD_COLUMN="password";
    final static String ITEM_TYPE_COLUMN="type";
    final static String ITEM_IMAGE_COLUMN="image";

    public static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME
                    +" ("+ITEM_ID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ITEM_NAME_COLUMN+" TEXT, "+ITEM_EMAIL_COLUMN+" TEXT, "+ITEM_PASSWORD_COLUMN+" TEXT, "
                    +ITEM_TYPE_COLUMN+" TEXT, "
                    +ITEM_IMAGE_COLUMN+" TEXT)";

    public DBhelper(Context context) {
        super(context, DB_NAME, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void updateprofile(String image,String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_IMAGE_COLUMN,image);
        String idnumber="id="+id;
        db.update(DB_NAME, cv, idnumber , null);
    }

    public long InsertPerson(person p){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues C=new ContentValues();
        C.put(ITEM_NAME_COLUMN, p.getName());
        C.put(ITEM_EMAIL_COLUMN, p.getEmail());
        C.put(ITEM_PASSWORD_COLUMN, p.getPassword());
        C.put(ITEM_IMAGE_COLUMN, p.getImage());
        C.put(ITEM_TYPE_COLUMN, p.getType());
        long val=db.insert(TABLE_NAME, null, C);
        db.close();
        return val;
    }

    public ArrayList<person> getSelectedPersonByEmailPass(String InEmail,String InPassword){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<person>P=new ArrayList<person>();

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                String name =C.getString(C.getColumnIndex(ITEM_NAME_COLUMN));
                String email =C.getString(C.getColumnIndex(ITEM_EMAIL_COLUMN));
                String password=C.getString(C.getColumnIndex(ITEM_PASSWORD_COLUMN));
                String image=C.getString(C.getColumnIndex(ITEM_IMAGE_COLUMN));
                String type=C.getString(C.getColumnIndex(ITEM_TYPE_COLUMN));
                person p=new person(email,password,name,image,type);
                if(email.equals(InEmail)&&password.equals(InPassword))P.add(p);
                C.moveToNext();
            }
        }
        else{
            return null;
        }
        return P;
    }

    public ArrayList<person> getSelectedPersonByEmail(String InEmail){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<person>P=new ArrayList<person>();

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                String name =C.getString(C.getColumnIndex(ITEM_NAME_COLUMN));
                String email =C.getString(C.getColumnIndex(ITEM_EMAIL_COLUMN));
                String password=C.getString(C.getColumnIndex(ITEM_PASSWORD_COLUMN));
                String image=C.getString(C.getColumnIndex(ITEM_IMAGE_COLUMN));
                String type=C.getString(C.getColumnIndex(ITEM_TYPE_COLUMN));
                person p=new person(email,password,name,image,type);
                if(email.equals(InEmail))P.add(p);
                C.moveToNext();
            }
        }
        else{
            return null;
        }
        return P;
    }
    public ArrayList<person> getAllPerson(){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<person>P=new ArrayList<person>();

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                String name =C.getString(C.getColumnIndex(ITEM_NAME_COLUMN));
                String email =C.getString(C.getColumnIndex(ITEM_EMAIL_COLUMN));
                String password=C.getString(C.getColumnIndex(ITEM_PASSWORD_COLUMN));
                String image=C.getString(C.getColumnIndex(ITEM_IMAGE_COLUMN));
                String type=C.getString(C.getColumnIndex(ITEM_TYPE_COLUMN));
                if(!type.equals("admin")){
                    person p=new person(email,password,name,image,type);
                    P.add(p);
                }
                C.moveToNext();
            }
        }
        else{
            return null;
        }
        return P;
    }
    public int getidbyemail(String InEmail){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);

        int id=-1;
        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int idfromdb =C.getInt(C.getColumnIndex(ITEM_ID_COLUMN));
                String email =C.getString(C.getColumnIndex(ITEM_EMAIL_COLUMN));
                if(email.equals(InEmail))id=idfromdb;
                C.moveToNext();
            }
        }
        else{
            return id;
        }
        return id;
    }

    public String gettypebyemail(String InEmail){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);

        String type="";
        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                String typefromdb =C.getString(C.getColumnIndex(ITEM_TYPE_COLUMN));
                String email =C.getString(C.getColumnIndex(ITEM_EMAIL_COLUMN));
                if(email.equals(InEmail))type=typefromdb;
                C.moveToNext();
            }
        }
        else{
            return type;
        }
        return type;
    }
}
