package admin.coach.panwar.com.coachadmin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sparsh23 on 15/07/16.
 */public class CustomNewAdapter extends BaseAdapter {

    Context context;



    ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public CustomNewAdapter(NewsAdd mainActivity, ArrayList<HashMap<String,String>> teamsdata) {
        // TODO Auto-generated constructor stub
        result=teamsdata;
        context=mainActivity;

        Log.d("CustomAdapter , ", teamsdata.toString() );
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        ;








    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv1;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.newsrow, null);
        holder.tv1=(TextView) rowView.findViewById(R.id.newrowsitem);
        holder.tv1.setText(result.get(position).get("title"));

        Log.d("adapter",result.get(position).get("title"));



        return rowView;
    }

}


