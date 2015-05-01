package json.ign.lee.com.igncode;

/**
 * Created by Drake Lee on 5/1/2015.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

public class SpecialAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private int mResource;
    private int mDropDownResource;
    private int mFieldId;
    private HashMap<Integer, String[]> data;

    public SpecialAdapter(Context a, int resource, int textViewResourceID, HashMap<Integer,String[]> data) {
       init(a, resource, textViewResourceID, data);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    private void init(Context context, int resource, int textViewResourceId, HashMap<Integer, String[]> objects) {
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResource = mDropDownResource = resource;
        data = objects;
        mFieldId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent,
                                        int resource) {
        View view;
        TextView text;

        if (convertView == null) {
            view = mInflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        try {
            if (mFieldId == 0) {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = (TextView) view;
            } else {
                //  Otherwise, find the TextView field within the layout
                text = (TextView) view.findViewById(mFieldId);
            }
        } catch (ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "ArrayAdapter requires the resource ID to be a TextView", e);
        }

        TextView index = (TextView)view.findViewById(R.id.index);
        TextView list_content = (TextView)view.findViewById(R.id.list_content);
        TextView time = (TextView)view.findViewById(R.id.time);
        int temp = position;
        index.setText((temp+1)+"");
        list_content.setText(data.get(temp)[0]);
        if(data.get(temp).length > 1 && !data.get(temp)[1].equals("")) {
            time.setText(data.get(temp)[1]);
        } else {
            time.setText("");
        }

        return view;
    }
}