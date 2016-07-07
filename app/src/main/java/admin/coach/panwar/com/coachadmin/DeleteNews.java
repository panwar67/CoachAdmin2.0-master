package admin.coach.panwar.com.coachadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DeleteNews extends AppCompatActivity {


    ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String, String>>();
    LinearLayout linearLayout;
    DBHelper dbHelper;
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "News Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                dbHelper.deleteNews(name);

                Intent intent1 = new Intent(DeleteNews.this,NewsList.class);
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




}
