package admin.coach.panwar.com.coachadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class addnews extends AppCompatActivity {



    String DOWN_URL = "http://www.whydoweplay.com/CoachData/addnews.php";
    DBHelper dbHelper;
    EditText title;
    EditText description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (EditText)findViewById(R.id.titleedit);
        description = (EditText)findViewById(R.id.desedit);
        dbHelper = new DBHelper(getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button = (Button)findViewById(R.id.addnews);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titles = title.getText().toString();
                String descript = description.getText().toString();


                EnterNews(titles,descript);



            }
        });

    }



    public boolean EnterNews(final String titlees, final String des){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading News...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {

                            Toast.makeText(addnews.this, s.toString(), Toast.LENGTH_LONG).show();
                            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                            dbHelper.InsertNews(titlees,des,date);
                            startActivity(new Intent(addnews.this,NewsUpdate.class));


                        }
                        else{

                            Toast.makeText(addnews.this, "Error", Toast.LENGTH_LONG).show();

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
                        Toast.makeText(addnews.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                HashMap<String,String> Keyvalue = new HashMap<String,String>();
                Keyvalue.put("title",titlees);
                Keyvalue.put("des",des);
                Keyvalue.put("date",date);

                Log.d("des", des);



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
