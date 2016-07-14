package admin.coach.panwar.com.coachadmin;

/**
 * Created by Sparsh23 on 14/07/16.
 */

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import android.content.Context;
        import android.graphics.Bitmap;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
        import com.nostra13.universalimageloader.core.DisplayImageOptions;
        import com.nostra13.universalimageloader.core.ImageLoader;
        import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
        import com.nostra13.universalimageloader.core.assist.ImageScaleType;
        import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

        import java.util.ArrayList;
        import java.util.HashMap;

public class CustomTeamAdapter extends BaseAdapter {

    Context context;



    ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();
    private static LayoutInflater inflater=null;
    public CustomTeamAdapter(TeamAdd mainActivity, ArrayList<HashMap<String,String>> teamsdata) {
        // TODO Auto-generated constructor stub

        Log.d("CustomAdapter , ", teamsdata.toString() );

        result=teamsdata;
        context=mainActivity;

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
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.teamrow, null);
        holder.tv=(TextView) rowView.findViewById(R.id.teamrowitem);
        holder.tv.setText(result.get(position).get("title"));
        Log.d("adapter",result.get(position).get("title"));




        return rowView;
    }

}

