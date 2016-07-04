package admin.coach.panwar.com.coachadmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import admin.coach.panwar.com.coachadmin.Team_Strut;

/**
 * Created by Sparsh23 on 30/06/16.
 */
public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "coach.db";
    public String table_name = "NEWS";
    public String Title = "TITLE";
    public String start_date = "START_DATE";
    public String description = "DESCRIPTION";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE " + table_name + " (" + Title + " text, " + start_date + " text, " + description + " text)");
        db.execSQL("CREATE TABLE "+Profile_Strut.profile_table+" ( "+Profile_Strut.uid+" text, "+Profile_Strut.fname+" text, "+Profile_Strut.lname+" text, "+Profile_Strut.fatname+" text, "+Profile_Strut.motname+" text, "+Profile_Strut.mob+" text, "+Profile_Strut.tel+" text, "+Profile_Strut.email+" text, "+Profile_Strut.ADDR+" text, "+Profile_Strut.city+" text, "+Profile_Strut.dob+" date, "+Profile_Strut.pob+" text, "+Profile_Strut.dist+" text, "+Profile_Strut.state+" text, "+Profile_Strut.bld+" text, "+Profile_Strut.bat+" text, "+Profile_Strut.bwl+" text,"+Profile_Strut.bwlpro+" text, "+Profile_Strut.wk+" text, "+Profile_Strut.images+" text,"  );


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+table_name+"");
        onCreate(db);
    }


    public boolean InitLogin() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+table_name+"");
        Log.d("sql", "Deleted");


        return true;
    }


    public Cursor GetPlayers(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "++" from "+table_name+"",null);
        res.moveToFirst();




    }




    public boolean InsertNews(String title, String date, String Description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Title, title);

        contentValues.put(start_date, date);
        contentValues.put(description, Description);
        long row = db.insertWithOnConflict(table_name, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        Log.d(table_name, String.valueOf(row) + "inserted");


        return true;
    }

    public boolean InsertProfileTable  (String uid,String fname, String lname, String fat,String mot,String dob, String pob, String add, String city, String dist, String state, String mob, String tel, String email, String bathand, String bwlhand, String bwlpro, String wck, String blood, String image)
    {
        InitLogin();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UID", uid);
        contentValues.put("FNAME", fname);
        contentValues.put("LNAME", lname);
        contentValues.put("FATNAME", fat);
        contentValues.put("MOTNAME", mot);
        contentValues.put("DOB", dob);
        contentValues.put("POB", pob);
        contentValues.put("ADDR", add);
        contentValues.put("CITY", city);
        contentValues.put("DIST", dist);
        contentValues.put("STATE", state);
        contentValues.put("MOB", mob);
        contentValues.put("TEL", tel);
        contentValues.put("EMAIL", email);
        contentValues.put("BAT_TYPE", bathand);
        contentValues.put("BWL_TYPE", bwlhand);
        contentValues.put("BWL_TYPE", bwlpro);
        contentValues.put("WK", wck);
        contentValues.put("IMAGE", image );
        contentValues.put("BLDGRP", blood);
        long row = db.insertWithOnConflict(Profile_Strut.profile_table, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        Log.d(Profile_Strut.profile_table, String.valueOf(row)+"inserted");


        return true;
    }







    public String getimageData() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from PROFILE ", null);

        res.moveToFirst();
        String pass = null;
        while (res.isAfterLast() == false) {
            pass = res.getString(res.getColumnIndex("IMAGE"));


            res.moveToNext();
        }

        return pass;
    }


    public ArrayList<String> getNewsTitle(){

        ArrayList<String> titles = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+Title+" from "+table_name+"",null);
        res.moveToFirst();

        if(res!=null){

        while(!res.isAfterLast()){

            String names = res.getString(res.getColumnIndex(Title));


            titles.add(names);

            Log.d("dbfor listviewget",names);

            res.moveToNext();

        }}else {


            titles.add("No new News");
        }

        return titles;

    }

    public Cursor GetNewsData() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+table_name+"", null);

        res.moveToFirst();
//        cursor.moveToFirst();
        return res;
    }
}
