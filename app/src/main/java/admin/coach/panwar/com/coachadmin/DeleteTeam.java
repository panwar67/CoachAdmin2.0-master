package admin.coach.panwar.com.coachadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DeleteTeam extends AppCompatActivity {


    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    LinearLayout linearLayout;
    DBHelper dbHelper;
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
                startActivity(new Intent(DeleteTeam.this,AddTeam.class));
                finish();



                Snackbar.make(view, "Team Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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




}
