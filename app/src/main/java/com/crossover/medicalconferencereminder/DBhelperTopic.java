package com.crossover.medicalconferencereminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by acer on 2/13/2016.
 */
public class DBhelperTopic extends SQLiteOpenHelper {

    public static final String DB_NAME="topic";
    public static final int version=1;

    public static final String TABLE_NAME="topic";
    final static String ITEM_ID_COLUMN="id";
    final static String ITEM_DOCTOREMAIL_COLUMN="doctoremail";
    final static String ITEM_DATE_COLUMN="date";
    final static String ITEM_TITLE_COLUMN="title";
    final static String ITEM_DESCRIPTION_COLUMN="description";

    public static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME
                    +" ("+ITEM_ID_COLUMN+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ITEM_DOCTOREMAIL_COLUMN+" TEXT, "+ITEM_DATE_COLUMN+" TEXT, "
                    +ITEM_TITLE_COLUMN+" TEXT, "
                    +ITEM_DESCRIPTION_COLUMN+" TEXT)";

    public DBhelperTopic(Context context) {
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

    public long InsertTopic(Topic p){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues C=new ContentValues();
        C.put(ITEM_DOCTOREMAIL_COLUMN, p.getDoctoremail());
        C.put(ITEM_DATE_COLUMN, p.getDate());
        C.put(ITEM_TITLE_COLUMN, p.getTitle());
        C.put(ITEM_DESCRIPTION_COLUMN, p.getDescription());
        long val=db.insert(TABLE_NAME, null, C);
        db.close();
        return val;
    }

    public ArrayList<Topic> getAllTopic(){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Topic>P=new ArrayList<Topic>();

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                int id=C.getInt(C.getColumnIndex(ITEM_ID_COLUMN));
                String title =C.getString(C.getColumnIndex(ITEM_TITLE_COLUMN));
                String email =C.getString(C.getColumnIndex(ITEM_DOCTOREMAIL_COLUMN));
                String description=C.getString(C.getColumnIndex(ITEM_DESCRIPTION_COLUMN));
                String date=C.getString(C.getColumnIndex(ITEM_DATE_COLUMN));
                Topic p=new Topic(id,title,description,email,date);
                P.add(p);
                C.moveToNext();
            }
        }
        else{
            return null;
        }
        return P;
    }

    public Topic getTopicByID(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor C;
        C=db.query(TABLE_NAME, null, null, null, null, null, null);
        Topic P=null;

        if(C!=null && C.getCount()>0){
            C.moveToFirst();
            for(int i=0;i<C.getCount();i++){
                String title =C.getString(C.getColumnIndex(ITEM_TITLE_COLUMN));
                String email =C.getString(C.getColumnIndex(ITEM_DOCTOREMAIL_COLUMN));
                String description=C.getString(C.getColumnIndex(ITEM_DESCRIPTION_COLUMN));
                String date=C.getString(C.getColumnIndex(ITEM_DATE_COLUMN));
                if(id==C.getInt(C.getColumnIndex(ITEM_ID_COLUMN))){
                    Topic p=new Topic(id,title,description,email,date);
                    P=p;
                    break;
                }
                C.moveToNext();
            }
        }
        else{
            return null;
        }
        return P;
    }

    public long updatetopic(String title,String description,String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE_COLUMN,title);
        cv.put(ITEM_DESCRIPTION_COLUMN,description);
        String idnumber="id="+id;
        try{
            db.update(DB_NAME, cv, idnumber , null);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public boolean deleteTopic(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.delete(TABLE_NAME, ITEM_ID_COLUMN + "=" + id, null) > 0;
    }
}
