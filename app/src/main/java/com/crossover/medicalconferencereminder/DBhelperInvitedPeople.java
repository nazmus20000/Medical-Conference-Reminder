package com.crossover.medicalconferencereminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by acer on 2/13/2016.
 */
public class DBhelperInvitedPeople extends SQLiteOpenHelper {

    public static final String DB_NAME="invitedpeople";
    public static final int version=1;

    public static final String TABLE_NAME="invitedpeople";
    final static String ITEM_ID_COLUMN="id";
    final static String ITEM_USERID_COLUMN="userid";
    final static String ITEM_INVITATIONID_COLUMN="invitationid";
    final static String ITEM_STATUS_COLUMN="status";

    public static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME
                    +" ("+ITEM_ID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ITEM_USERID_COLUMN+" INTEGER, "+ITEM_INVITATIONID_COLUMN+" INTEGER, "
                    +ITEM_STATUS_COLUMN+" TEXT)";

    public DBhelperInvitedPeople(Context context) {
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

    public long updatestatus(String status,String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_STATUS_COLUMN,status);
        String idnumber="id="+id;
        try{
            db.update(DB_NAME, cv, idnumber , null);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public long InsertInvitation(InvitedPeople p){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues C=new ContentValues();
        C.put(ITEM_USERID_COLUMN, p.getUserid());
        C.put(ITEM_INVITATIONID_COLUMN, p.getInvitationid());
        C.put(ITEM_STATUS_COLUMN, p.getStatus());
        long val=db.insert(TABLE_NAME, null, C);
        db.close();
        return val;
    }

    public ArrayList<InvitedPeople> getAllInvitation(){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<InvitedPeople>P=new ArrayList<InvitedPeople>();

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int id=C.getInt(C.getColumnIndex(ITEM_ID_COLUMN));
                int userid =C.getInt(C.getColumnIndex(ITEM_USERID_COLUMN));
                int invitationid =C.getInt(C.getColumnIndex(ITEM_INVITATIONID_COLUMN));
                String status=C.getString(C.getColumnIndex(ITEM_STATUS_COLUMN));
                InvitedPeople p=new InvitedPeople(id,userid,invitationid,status);
                P.add(p);
                C.moveToNext();
            }
        }
        else{
            return null;
        }
        return P;
    }
    public ArrayList<InvitedPeople> getInvitation(int useid,int invid){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<InvitedPeople>P=new ArrayList<InvitedPeople>();

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int id=C.getInt(C.getColumnIndex(ITEM_ID_COLUMN));
                int userid =C.getInt(C.getColumnIndex(ITEM_USERID_COLUMN));
                int invitationid =C.getInt(C.getColumnIndex(ITEM_INVITATIONID_COLUMN));
                String status=C.getString(C.getColumnIndex(ITEM_STATUS_COLUMN));
                if(useid==userid&&invid==invitationid){
                    InvitedPeople p=new InvitedPeople(id,userid,invitationid,status);
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

    public ArrayList<Integer> getidbyid(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Integer>P=new ArrayList<Integer>();
        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int userid =C.getInt(C.getColumnIndex(ITEM_USERID_COLUMN));
                int invitid =C.getInt(C.getColumnIndex(ITEM_INVITATIONID_COLUMN));
                if(userid==id)P.add(invitid);
                C.moveToNext();
            }
        }
        else{
            return P;
        }
        return P;
    }

    public ArrayList<String> getstatusbyid(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<String>P=new ArrayList<String>();
        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int userid =C.getInt(C.getColumnIndex(ITEM_USERID_COLUMN));
                int invitid =C.getInt(C.getColumnIndex(ITEM_INVITATIONID_COLUMN));
                String status=C.getString(C.getColumnIndex(ITEM_STATUS_COLUMN));
                if(userid==id)P.add(status);
                C.moveToNext();
            }
        }
        else{
            return P;
        }
        return P;
    }

    public ArrayList<InvitedPeople> getAllInvitedPeoplebyid(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<InvitedPeople>P=new ArrayList<InvitedPeople>();
        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int invitationid =C.getInt(C.getColumnIndex(ITEM_ID_COLUMN));
                int userid =C.getInt(C.getColumnIndex(ITEM_USERID_COLUMN));
                int invitid =C.getInt(C.getColumnIndex(ITEM_INVITATIONID_COLUMN));
                String status=C.getString(C.getColumnIndex(ITEM_STATUS_COLUMN));
                InvitedPeople invitedPeople=new InvitedPeople(invitationid,userid,invitid,status);
                if(userid==id)P.add(invitedPeople);
                C.moveToNext();
            }
        }
        else{
            return P;
        }
        return P;
    }
}
