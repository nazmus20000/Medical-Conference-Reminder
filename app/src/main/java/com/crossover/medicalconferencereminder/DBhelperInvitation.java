package com.crossover.medicalconferencereminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by acer on 2/13/2016.
 */
public class DBhelperInvitation extends SQLiteOpenHelper {

    public static final String DB_NAME="invitation";
    public static final int version=1;

    public static final String TABLE_NAME="invitation";
    final static String ITEM_ID_COLUMN="id";
    final static String ITEM_LOCATION_COLUMN="location";
    final static String ITEM_DATE_COLUMN="date";
    final static String ITEM_TITLE_COLUMN="title";
    final static String ITEM_DESCRIPTION_COLUMN="description";

    public static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME
                    +" ("+ITEM_ID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ITEM_LOCATION_COLUMN+" TEXT, "+ITEM_DATE_COLUMN+" TEXT, "
                    +ITEM_TITLE_COLUMN+" TEXT, "
                    +ITEM_DESCRIPTION_COLUMN+" TEXT)";

    public DBhelperInvitation(Context context) {
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

    public long InsertInvitation(invitation p){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues C=new ContentValues();
        C.put(ITEM_LOCATION_COLUMN, p.getLocation());
        C.put(ITEM_DATE_COLUMN, p.getDate());
        C.put(ITEM_TITLE_COLUMN, p.getTitle());
        C.put(ITEM_DESCRIPTION_COLUMN, p.getDescription());
        long val=db.insert(TABLE_NAME, null, C);
        db.close();
        return val;
    }

    public ArrayList<invitation> getAllInvitation(){
        SQLiteDatabase db=this.getReadableDatabase();
        Calendar calendar;
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        formatter.setLenient(false);
        String oldTime = Integer.toString(day)+"."+Integer.toString(month+1)+"."+Integer.toString(year);
        Date oldDate=null;
        try{
            oldDate = formatter.parse(oldTime);
        }catch (ParseException e){}
        long oldMillis = oldDate.getTime();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<invitation>P=new ArrayList<invitation>();

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int id=C.getInt(C.getColumnIndex(ITEM_ID_COLUMN));
                String title =C.getString(C.getColumnIndex(ITEM_TITLE_COLUMN));
                String location =C.getString(C.getColumnIndex(ITEM_LOCATION_COLUMN));
                String description=C.getString(C.getColumnIndex(ITEM_DESCRIPTION_COLUMN));
                String date=C.getString(C.getColumnIndex(ITEM_DATE_COLUMN));
                long milidate=Long.parseLong(date);
                if(milidate>=oldMillis){
                    invitation p=new invitation(title,description,location,date,id);
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
    public invitation getInvitation(Integer id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        invitation inv=null;

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int idd=C.getInt(C.getColumnIndex(ITEM_ID_COLUMN));
                String title =C.getString(C.getColumnIndex(ITEM_TITLE_COLUMN));
                String location =C.getString(C.getColumnIndex(ITEM_LOCATION_COLUMN));
                String description=C.getString(C.getColumnIndex(ITEM_DESCRIPTION_COLUMN));
                String date=C.getString(C.getColumnIndex(ITEM_DATE_COLUMN));
                if(id==idd){
                    inv=new invitation(title,description,location,date,id);
                }
                C.moveToNext();
            }
        }
        else{
            return null;
        }
        return inv;
    }
}
