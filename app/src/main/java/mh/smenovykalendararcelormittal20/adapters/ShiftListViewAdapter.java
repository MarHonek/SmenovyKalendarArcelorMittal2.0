package mh.smenovykalendararcelormittal20.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mh.smenovykalendararcelormittal20.R;
import mh.smenovykalendararcelormittal20.templates.ListTemplates;

/**
 * Created by Martin on 31.01.2016.
 */
public class ShiftListViewAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ListTemplates> list;
    TextView title, shorTitle, desc;
    LinearLayout icon;

    public ShiftListViewAdapter(Context context, ArrayList<ListTemplates> list)
    {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.shift_listview_item_layout, null);

            title = (TextView)v.findViewById(R.id.textView_shift_listview_title);
            shorTitle = (TextView)v.findViewById(R.id.textView2);
            desc = (TextView)v.findViewById(R.id.textView_shift_listview_desc);
            icon = (LinearLayout)v.findViewById(R.id.linearLayout_circle);


            title.setText(list.get(position).getTitle());
            shorTitle.setText(list.get(position).getShortTitle());
            desc.setText(list.get(position).getDesc());
            GradientDrawable background = (GradientDrawable) icon.getBackground();
            background.setColor(list.get(position).getColor());


        return v;
    }
}
