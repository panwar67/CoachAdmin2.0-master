package admin.coach.panwar.com.coachadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class TeamAdd extends AppCompatActivity {


    ListView listView;
    DBHelper dbHelper;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                startActivity(new Intent(TeamAdd.this,FinalTeam.class));
                finish();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });


        dbHelper = new DBHelper(getApplicationContext());

        data = dbHelper.getTeamsData();
        Log.d("from team add", data.toString());
        listView = (ListView)findViewById(R.id.listViewteam);

        CustomTeamAdapter customTeamAdapter = new CustomTeamAdapter(TeamAdd.this,data);



        listView.setAdapter(customTeamAdapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String table = data.get(position).get("title");
                Log.d("Team To Delete",table);
                ArrayList<HashMap<String,String>> desteam = dbHelper.GetCustomTeam(table);
                Intent intent = new Intent(TeamAdd.this,DeleteTeam.class);
                intent.putExtra("tablename",table);
                startActivity(intent);
                finish();




            }
        });

    }

    /*@Override
  protected void onResume() {
        super.onResume();
        data = dbHelper.getNewsTitle();
        listView.setAdapter(new CustomTeamAdapter(this,data));


    }*/


    /*@Override
    protected void onRestart() {
        super.onRestart();
        data = dbHelper.getNewsTitle();
        listView.setAdapter(new CustomTeamAdapter(this,data));
    }*/



}
