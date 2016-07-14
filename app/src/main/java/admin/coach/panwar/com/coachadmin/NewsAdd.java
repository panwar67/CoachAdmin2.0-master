package admin.coach.panwar.com.coachadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsAdd extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper;
    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dbHelper = new DBHelper(getApplicationContext());


        listView = (ListView)findViewById(R.id.listViewnews);
        data = dbHelper.getNewsTitle();

CustomNewAdapter customNewAdapter = new CustomNewAdapter(NewsAdd.this,data);

        listView.setAdapter(customNewAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(NewsAdd.this,addnews.class));
                finish();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = data.get(position).get("title");
                Intent delete = new Intent(NewsAdd.this,DeleteNews.class);
                delete.putExtra("news",title);
                startActivity(delete);


            }
        });
    }





}
