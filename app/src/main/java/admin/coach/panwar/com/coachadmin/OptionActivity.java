package admin.coach.panwar.com.coachadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class OptionActivity extends AppCompatActivity {

    DBHelper dbHelper;
    String DOWN_URL="http://www.whydoweplay.com/CoachData/getnews.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbHelper = new DBHelper(getApplicationContext());
        setUpProfile();

        Button button = (Button)findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionActivity.this,NewsList.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }









    public boolean setUpProfile(){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Your Profile...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {



                            try {
                                JSONObject profile = new JSONObject(s);
                                JSONArray data = profile.getJSONArray("NEWS");

                                dbHelper.InitLogin();
                                for(int i=0;i<data.length();i++){

                                    JSONObject details = data.getJSONObject(i);
                                    String title = details.getString("TITLE");
                                    String des = details.getString("DES");
                                    String date = details.getString("DATE");

                                    dbHelper.InsertNews(title,des,date);

                                }

                                Log.d("profile data", s);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        else
                            Toast.makeText(OptionActivity.this, "Error null", Toast.LENGTH_LONG).show();





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
                        Toast.makeText(OptionActivity.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
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

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Boolean> {

        private String resp;

        @Override
        protected Boolean doInBackground(Void... params) {

            final ProgressDialog loading = ProgressDialog.show(getApplicationContext(),"Setting Up Your Profile...","Please wait...",false,false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {


                            if (s!=null)
                            {


                                try {
                                    JSONObject profile = new JSONObject(s);
                                    JSONArray data = profile.getJSONArray("NEWS");

                                    for(int i=0;i<data.length();i++){

                                        JSONObject details = data.getJSONObject(i);
                                        String title = details.getString("TITLE");
                                        String des = details.getString("DES");
                                        String date = details.getString("DATE");

                                        dbHelper.InsertNews(title,des,date);

                                    }

                                    Log.d("profile data", s);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            else
                                Toast.makeText(OptionActivity.this, "Error null", Toast.LENGTH_LONG).show();





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
                            Toast.makeText(OptionActivity.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            //Adding request to the queue
            requestQueue.add(stringRequest);
            return Boolean.valueOf(DOWN_URL);


        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */

    }

}
