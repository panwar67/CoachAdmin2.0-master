package admin.coach.panwar.com.coachadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class DeleteTeam extends AppCompatActivity {


    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    LinearLayout linearLayout;
    DBHelper dbHelper;
    String DOWN_URL = "http://www.whydoweplay.com/CoachData/deletefromteam.php";
    String Downurl2 = "http://www.whydoweplay.com/CoachData/deleteteam.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout)findViewById(R.id.deletelayout);
        dbHelper = new DBHelper(getApplicationContext());


        Intent delete = getIntent();
        final String team = delete.getStringExtra("tablename");

        data = dbHelper.GetCustomTeam(team);

        for(int i=0;i<data.size();i++){

            linearLayout.addView(createNewTextView(data.get(i).get("name")));


        }





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                dbHelper.deleteteam(team);
                deleteTeam(team);
                deleteCustomTeam(team);
                startActivity(new Intent(DeleteTeam.this,TeamAdd.class));
                finish();




            }
        });
    }

    private TextView createNewTextView(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);


        return textView;
    }



    public boolean deleteTeam(final  String teamname)


    {

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Deleting Team...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {

                            loading.dismiss();
                            Toast.makeText(DeleteTeam.this, s, Toast.LENGTH_LONG).show();




                                Log.d("team data", s);



                        }

                        else
                            Toast.makeText(DeleteTeam.this, "Error null", Toast.LENGTH_LONG).show();





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(DeleteTeam.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String




                HashMap<String,String> Keyvalue = new HashMap<String,String>();

                Keyvalue.put("title",teamname);


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




    public boolean deleteCustomTeam(final  String teamname)


    {

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Deleting Team...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Downurl2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {

                            loading.dismiss();
                            Toast.makeText(DeleteTeam.this, s, Toast.LENGTH_LONG).show();



                            Log.d("Custom team", s);



                        }

                        else
                            Toast.makeText(DeleteTeam.this, "Error null", Toast.LENGTH_LONG).show();





                        //Disimissing the progress dialog

                        //Showing toast message of the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(DeleteTeam.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String




                HashMap<String,String> Keyvalue = new HashMap<String,String>();

                Keyvalue.put("title",teamname);


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
