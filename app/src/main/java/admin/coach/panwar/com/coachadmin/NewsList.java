package admin.coach.panwar.com.coachadmin;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class NewsList extends ListActivity {


    DBHelper dbHelper;
    ArrayList<String> newstitles = new ArrayList<String>();
    String DOWN_URL = "http://www.whydoweplay.com/CoachData/getnews.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        dbHelper = new DBHelper(getApplicationContext());
        //setUpProfile();
        newstitles = dbHelper.getNewsTitle();
        Log.d("Listactivity",""+newstitles.size());


        setListAdapter(new ArrayAdapter<String>(NewsList.this,
                android.R.layout.simple_list_item_1 ,newstitles));





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


                            dbHelper.InitLogin();


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
                            Toast.makeText(NewsList.this, "Error null", Toast.LENGTH_LONG).show();





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
                        Toast.makeText(NewsList.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
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






        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.addnews){
            startActivity(new Intent(NewsList.this,addnews.class));

        }
        return super.onOptionsItemSelected(item);
    }


}
