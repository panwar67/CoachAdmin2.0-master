package admin.coach.panwar.com.coachadmin;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FinalTeam extends AppCompatActivity {

    LinearLayout mLayout;
    AutoCompleteTextView autoCompleteTextView;
    DBHelper dbHelper;
    String DOWN_URL ="http://www.whydoweplay.com/CoachData/CreateTeam.php";
    String teamurl = "http://www.whydoweplay.com/CoachData/addteams.php";


    Calendar myCalendar = Calendar.getInstance();
    ArrayList<HashMap<String,String>> names;
    ArrayList<String> Listadapter = new ArrayList<String>();
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<HashMap<String,String>> SelectedNames = new ArrayList<HashMap<String,String>>();
    EditText date;
    EditText title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLayout = (LinearLayout) findViewById(R.id.real);
        date = (EditText)findViewById(R.id.editText3);
        title = (EditText)findViewById(R.id.editText);
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(FinalTeam.this, datePickerDialog, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });



        dbHelper = new DBHelper(getApplicationContext());
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.addplayer);
        names = dbHelper.composeJSONfromSQLite();
        for(int i=0;i<names.size();i++){


            String name = names.get(i).get("name");
            Log.d("full names", name );

            Listadapter.add(name);

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.listviewitems,R.id.plyhead);
        adapter.addAll(Listadapter);
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setAdapter(adapter);


//        autoCompleteTextView.showDropDown();


        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    String titles = title.getText().toString();
                    String dates = date.getText().toString();
                    if(!titles.isEmpty()&&!dates.isEmpty())
                    {
                            dbHelper.CreateCustomTeam(titles,SelectedNames);

                            dbHelper.InsertTeam(titles, dates);
                        Gson gson = new GsonBuilder().create();

                       String data =  gson.toJson(SelectedNames);
                        Log.d("json send data", data);
                        InsertIntoTeams(titles,dates);
                        setUpCustomTeam(data,titles);

                    }
                    else
                    {
                        Toast.makeText(FinalTeam.this, "All the fields are mandatory", Toast.LENGTH_SHORT).show();

                    }




                }
            });
        }


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);

                Log.d("id selection", ""+id);
                Log.d("selection string",selection);
                int pos= Listadapter.indexOf(selection);
                SelectedNames.add(names.get(pos));


                autoCompleteTextView.setText("");
                Log.d("selection",names.get(pos).toString());
                mLayout.addView(createNewTextView(names.get(pos).get("name")));


 }
        });


    }


    DatePickerDialog.OnDateSetListener datePickerDialog = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    view.setSpinnersShown(true);
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };


    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        String dob = sdf.format(myCalendar.getTime());
        date.setText(dob);

    }


    private TextView createNewTextView(String text) {
        final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);


        return textView;
    }

    public boolean setUpCustomTeam(final String data, final String title)
    {

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading Team...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DOWN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {



                            Toast.makeText(FinalTeam.this, s.toString(), Toast.LENGTH_LONG).show();
                            Log.d("response", s.toString());


                            loading.dismiss();
                            Intent intent = new Intent(FinalTeam.this,TeamAdd.class);
                            startActivity(intent);
                            finish();




                        }

                        else
                            Toast.makeText(FinalTeam.this, "Error null", Toast.LENGTH_LONG).show();





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
                        Toast.makeText(FinalTeam.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String


                
                HashMap<String,String> Keyvalue = new HashMap<String,String>();

                Keyvalue.put("UserJson",data);
                Keyvalue.put("table_name",title);


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

    public boolean InsertIntoTeams(final String title, final String date)
    {

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading Team...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, teamurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        if (s!=null)
                        {

                            Toast.makeText(FinalTeam.this, "Team added Successfully", Toast.LENGTH_LONG).show();







                        }

                        else
                            Toast.makeText(FinalTeam.this, "Error null", Toast.LENGTH_LONG).show();





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
                        Toast.makeText(FinalTeam.this, "Error In Connectivity", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String



                HashMap<String,String> Keyvalue = new HashMap<String,String>();

                Keyvalue.put("title",title);
                Keyvalue.put("date",date);


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
        startActivity(new Intent(FinalTeam.this,TeamAdd.class));
        finish();
    }
}
