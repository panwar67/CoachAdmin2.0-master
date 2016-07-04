package admin.coach.panwar.com.coachadmin;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddTeam extends ListActivity {


    DBHelper dbHelper;
    ArrayList<String> teamtitles = new ArrayList<String>();
    String DOWN_URL = "http://www.whydoweplay.com/CoachData/getnews.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dbHelper = new DBHelper(getApplicationContext());
        //setUpProfile();


        teamtitles = dbHelper.getTeamTitle();

        setListAdapter(new ArrayAdapter<String>(AddTeam.this,
                android.R.layout.simple_list_item_1 ,teamtitles));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_team, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.addteam){
            startActivity(new Intent(AddTeam.this,FinalTeam.class));

        }
        return super.onOptionsItemSelected(item);
    }





    public boolean setUpProfile(final String uid){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Profile...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {


                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("Registration");

                                for(int i=0;i<data.length();i++)
                                {
                                JSONObject details = data.getJSONObject(i);

                                String uid = details.getString("UID");
                                String fname = details.getString("FNAME");
                                String lname = details.getString("LNAME");
                                String fatname = details.getString("FATNAME");
                                String motname = details.getString("MOTNAME");
                                String dob = details.getString("DOB");
                                String pob = details.getString("POB");
                                String add = details.getString("ADDR");
                                String city = details.getString("CITY");
                                String dist = details.getString("DISTRICT");
                                String state = details.getString("STATE");
                                String email = details.getString("EMAIL");
                                String mob = details.getString("MOB");
                                String tel = details.getString("TEL");
                                String bat = details.getString("BAT_TYPE");
                                String bwl = details.getString("BWL_TYPE");
                                String bwlatt = details.getString("BWL_ATT");
                                String wck = details.getString("WK");
                                String blood = details.getString("BLDGRP");
                                String image = details.getString("IMAGE");

                                    dbHelper.InsertProfileTable(uid,fname,lname,fatname,motname,dob,pob,add,city,dist,state,mob,tel,email,bat,bwl,bwlatt,wck,blood,image);

                                }
                                Log.d("profile data", s);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }








                        }





                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(AddTeam.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();


                //returning parameters
                return Keyvalue;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding request to the queue
        requestQueue.add(stringRequest);
        return false;
    }



}
