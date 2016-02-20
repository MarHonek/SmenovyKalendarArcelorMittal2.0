package mh.shiftcalendaram;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mh.shiftcalendaram.adapters.ShiftListViewAdapter;
import mh.shiftcalendaram.templates.ListTemplates;
import mh.shiftcalendaram.templates.ShiftSymbolTemplates;
import mh.shiftcalendaram.templates.ShiftTemplate;
import mh.shiftcalendaram.templates.StaticShiftTemplate;

/**
 * Created by Martin on 19.02.2016.
 */


public class ShiftHeaderFragment extends Fragment {

    ArrayList<ShiftTemplate> shifts;
    ShiftListViewAdapter adapter;
    Database database;
    SharedPreferences pref;
    TextView title, shorTitle, desc;
    LinearLayout icon, shiftListItem;

    int positionOfCustom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shift_listview_item_layout, null);

        database = new Database(getActivity());
        shifts = database.getAllShifts();
        pref = getActivity().getSharedPreferences("shift", Context.MODE_PRIVATE);


        shiftListItem = (LinearLayout)v.findViewById(R.id.linear_shift_listview_item);
        ArrayList<ListTemplates> adapterList = (ArrayList<ListTemplates>) shifts.clone();
        adapter = new ShiftListViewAdapter(getActivity(), adapterList);

        title = (TextView)v.findViewById(R.id.textView_shift_listview_title);
        shorTitle = (TextView)v.findViewById(R.id.textView2);
        desc = (TextView)v.findViewById(R.id.textView_shift_listview_desc);
        icon = (LinearLayout)v.findViewById(R.id.linearLayout_circle);



        if(shifts.size() > 0)
        {
            title.setText(shifts.get(0).getTitle());
            shorTitle.setText(shifts.get(0).getShortTitle());
            GradientDrawable background = (GradientDrawable) icon.getBackground();
            background.setColor(shifts.get(0).getColor());
            desc.setText(StaticShiftTemplate.getStringArray().get(shifts.get(0).getPosition()));
        }
        else
        {
            icon.setVisibility(View.GONE);
            title.setGravity(View.TEXT_ALIGNMENT_CENTER);
            title.setText("Nemáš vytvořené vlastní schéma");
        }


        shiftListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view =  getActivity().getLayoutInflater().inflate(R.layout.fragment_shift, null);

                ListView listView = (ListView) view.findViewById(R.id.listView_shift);
                listView.setAdapter(adapter);

                builder.setTitle("Vyberte schéma");
                builder.setView(view);
                final AlertDialog alert = builder.create();
                alert.show();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0,
                                            View arg1, int arg2, long arg3) {

                        positionOfCustom = arg2;

                        title.setText(shifts.get(positionOfCustom).getTitle());
                        shorTitle.setText(shifts.get(positionOfCustom).getShortTitle());
                        GradientDrawable background = (GradientDrawable) icon.getBackground();
                        background.setColor(shifts.get(positionOfCustom).getColor());
                        StaticShiftTemplate.getStringArray().get(shifts.get(positionOfCustom).getPosition());
                        desc.setText(shifts.get(positionOfCustom).getDesc());
                        ((StatisticActivity)getActivity()).statisticListview(positionOfCustom);
                        alert.dismiss();



                    }

                });

            }
        });


        return v;
    }


}
