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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeleteNews extends AppCompatActivity {


    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    LinearLayout linearLayout;
    DBHelper dbHelper;
    String downurl = "http://www.whydoweplay.com/CoachData/deletenews.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout)findViewById(R.id.delnews);
        dbHelper = new DBHelper(getApplicationContext());
        Intent intent = getIntent();
        final String name = intent.getStringExtra("news");
        data = dbHelper.GetNews(name);


        for(int i=0;i<data.size();i++){

            linearLayout.addView(createNewTextView(data.get(i).get("title")));

            linearLayout.addView(createNewTextView(data.get(i).get("date")));

            linearLayout.addView(createNewTextView(data.get(i).get("des")));
 }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "News Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                dbHelper.deleteNews(name);
                deleteNews(name);

                Intent intent1 = new Intent(DeleteNews.this,NewsAdd.class);
                startActivity(intent1);
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




    public boolean deleteNews(final String title){

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Setting Up Profile...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, downurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {



                            loading.dismiss();

                            Toast.makeText(DeleteNews.this, s, Toast.LENGTH_LONG).show();




                        }





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
                        Toast.makeText(DeleteNews.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                HashMap<String,String> Keyvalue = new HashMap<String,String>();

                Keyvalue.put("title",title);


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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DeleteNews.this,NewsAdd.class));
    }
}
