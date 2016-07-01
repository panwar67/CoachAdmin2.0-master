package admin.coach.panwar.com.coachadmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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


    public String getimageData() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from PROFILE ", null);

        res.moveToFirst();
        String pass = null;
        while (res.isAfterLast() == false) {
            pass = res.getString(res.getColumnIndex("IMAGE"));


            Log.d("getting passwords", res.getString(res.getColumnIndex("PASS")));
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
