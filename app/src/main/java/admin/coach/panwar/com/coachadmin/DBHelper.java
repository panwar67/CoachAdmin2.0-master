package admin.coach.panwar.com.coachadmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;


import admin.coach.panwar.com.coachadmin.Team_Strut;

/**
 * Created by Sparsh23 on 30/06/16.
 */
public class DBHelper extends SQLiteOpenHelper
{


    public static final String DATABASE_NAME = "coach.db";
    public String table_name = "NEWS";
    public String uid = "UID";
    public String namecol = "NAME";
    public String Title = "TITLE";
    public String start_date = "START_DATE";
    public String description = "DESCRIPTION";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + " (" +Title+ " text, " +start_date+ " text, " +description+ " text)");
        db.execSQL("CREATE TABLE "+Profile_Strut.profile_table+" ( "+Profile_Strut.uid+" text, "+Profile_Strut.fname+" text, "+Profile_Strut.lname+" text, "+Profile_Strut.fatname+" text, "+Profile_Strut.motname+" text, "+Profile_Strut.mob+" text, "+Profile_Strut.tel+" text, "+Profile_Strut.email+" text, "+Profile_Strut.ADDR+" text, "+Profile_Strut.city+" text, "+Profile_Strut.dob+" date, "+Profile_Strut.pob+" text, "+Profile_Strut.dist+" text, "+Profile_Strut.state+" text, "+Profile_Strut.bld+" text, "+Profile_Strut.bat+" text, "+Profile_Strut.bwl+" text, "+Profile_Strut.bwlpro+" text, "+Profile_Strut.wk+" text, "+Profile_Strut.images+" text)"  );
        db.execSQL("CREATE TABLE " + Team_Strut.team_table + " (" + Team_Strut.team_title + " text, " + Team_Strut.team_date + " text)");



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
    public boolean Initpro(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+Profile_Strut.profile_table+"");
        Log.d("sql team ", "Deleted");

        return true;
    }
    public boolean Initteam(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+Team_Strut.team_table+"");
        Log.d("sql team ", "Deleted");


        return true;
    }








    public ArrayList<String> GetPlayers(){

        ArrayList<String> names = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Profile_Strut.profile_table+"",null);
        res.moveToFirst();
        while (!res.isAfterLast()){

            String fname = res.getString(res.getColumnIndex(Profile_Strut.fname));
            String lname = res.getString(res.getColumnIndex(Profile_Strut.lname));
            Log.d("AutocompleteList",fname+""+lname);
            names.add(fname+" "+lname);

        }
        return names; }

        public boolean CreateCustomTeam(String tablename, ArrayList<HashMap<String,String>> data)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("CREATE TABLE IF NOT EXISTS "+tablename+"(UID text, NAME text)");
            for(int i=0;i<data.size();i++)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put(uid, data.get(i).get("uid")  );
                contentValues.put("NAME",data.get(i).get("name") );
                long row = db.insertWithOnConflict(tablename, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                Log.d(tablename, String.valueOf(row) + "inserted");



            }





            return  true;
        }







    public boolean InsertTeam(String title, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Team_Strut.team_title, title);
        contentValues.put(Team_Strut.team_date, date);
        long row = db.insertWithOnConflict(Team_Strut.team_table, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        Log.d(Team_Strut.team_table, String.valueOf(row) + "inserted");
        return true;
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



    public ArrayList<HashMap<String,String>> getTeamsData(){

        ArrayList<HashMap<String,String>> titles = new ArrayList<HashMap<String,String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+Team_Strut.team_table,null);
        res.moveToFirst();

        if(res!=null){

            while(!res.isAfterLast()){

                HashMap<String,String> temp = new HashMap<String, String>();
                String names = res.getString(res.getColumnIndex(Team_Strut.team_title));
                String date = res.getString(res.getColumnIndex(Team_Strut.team_date));

                temp.put("title",names);
                temp.put("date",date);
                Log.d("DB team list",names);

                titles.add(temp);

                res.moveToNext();

            }}else {


        }

        return titles;

    }







    public ArrayList<HashMap<String, String>> composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList =  new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getReadableDatabase();


        Cursor cursor = database.rawQuery("select * from PROFILE",null);

        cursor.moveToFirst();


        while(!cursor.isAfterLast()) {


                HashMap<String, String> map = new HashMap<String, String>();
                map.put("uid", cursor.getString(cursor.getColumnIndex("UID")));
                String fname = cursor.getString(cursor.getColumnIndex("FNAME"));

            Log.d("fname",fname);

                String lname = cursor.getString(cursor.getColumnIndex("LNAME"));
            Log.d("lname",lname);
                map.put("name",fname+" "+lname );

                wordList.add(map);
             cursor.moveToNext();
        }


        Log.d("Wordlist size",""+wordList.size());
        //Use GSON to serialize Array List to JSON
        //return gson.toJson(wordList);
    return wordList;
    }



    public boolean deleteteam(String deltab){
        SQLiteDatabase database = this.getWritableDatabase();


        Cursor cursor = database.rawQuery("DROP TABLE "+deltab,null);
        int n= database.delete(Team_Strut.team_table, "TEAM_TITLE=?", new String[]{deltab});
        Log.d("deleted row team"," "+n );





        return  true;
    }

    public boolean deleteNews(String delnews){
        SQLiteDatabase database = this.getWritableDatabase();


        int n= database.delete("NEWS", "TITLE=?", new String[]{delnews});
        Log.d("deleted row news"," "+n );





        return  true;
    }









    public ArrayList<HashMap<String, String>> GetCustomTeam(String table_name){
        ArrayList<HashMap<String, String>> wordList =  new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getReadableDatabase();


        Cursor cursor = database.rawQuery("select * from "+table_name,null);

        cursor.moveToFirst();


        while(!cursor.isAfterLast()) {


            HashMap<String, String> map = new HashMap<String, String>();

            String fname = cursor.getString(cursor.getColumnIndex("NAME"));



            map.put("uid", cursor.getString(cursor.getColumnIndex("UID")));

            map.put("name",cursor.getString(cursor.getColumnIndex("NAME")));
            Log.d("table for delete",fname);

            wordList.add(map);
            cursor.moveToNext();
        }


        Log.d("Wordlist size",""+wordList.size());
        //Use GSON to serialize Array List to JSON
        //return gson.toJson(wordList);
        return wordList;
    }

    public ArrayList<HashMap<String, String>> GetNews(String table_name) {
        ArrayList<HashMap<String, String>> wordList =  new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getReadableDatabase();


        Cursor cursor = database.rawQuery("select * from NEWS where "+Title+"= '"+table_name+"'",null);


        cursor.moveToFirst();


        while(!cursor.isAfterLast()) {


            HashMap<String, String> map = new HashMap<String, String>();

            String fname = cursor.getString(cursor.getColumnIndex("TITLE"));



            map.put("title", cursor.getString(cursor.getColumnIndex("TITLE")));
            map.put("date", cursor.getString(cursor.getColumnIndex("START_DATE")));

            map.put("des",cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
            Log.d("news for delete", ""+cursor.getString(cursor.getColumnIndex("DESCRIPTION")));

            wordList.add(map);
            cursor.moveToNext();
        }


        Log.d("Wordlist size",""+wordList.size());
        //Use GSON to serialize Array List to JSON
        //return gson.toJson(wordList);
        return wordList;
    }














    public ArrayList<HashMap<String,String>> getNewsTitle(){

        ArrayList<HashMap<String,String>> titles = new ArrayList<HashMap<String,String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+table_name+"",null);
        res.moveToFirst();

        if(res!=null)
        {

        while(!res.isAfterLast())
        {

            HashMap<String, String> map = new HashMap<String, String>();
            String names = res.getString(res.getColumnIndex(Title));


            map.put("title",res.getString(res.getColumnIndex(Title)));
            map.put("date",res.getString(res.getColumnIndex(start_date)));
            map.put("des",res.getString(res.getColumnIndex(description)));
            Log.d("dbfor listviewget",names);


            titles.add(map);
            res.moveToNext();

        }
        }

        return titles;

    }
}
