package mh.smenovykalendararcelormittal20;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import hirondelle.date4j.DateTime;

/**
 * Created by Martin on 03.02.2016.
 */
public class CalendarCustomAdapter extends CaldroidGridAdapter {


    /**
     * Constructor
     *
     * @param context
     * @param month
     * @param year
     * @param caldroidData
     * @param extraData
     *
     */

    int index;

    public CalendarCustomAdapter(Context context, int month, int year, HashMap<String, Object> caldroidData, HashMap<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);







    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = convertView;

        // For reuse
        if (convertView == null) {
            cellView = inflater.inflate(R.layout.calendar_day_layout, null);
        }

        SharedPreferences pref = context.getSharedPreferences("shift", Context.MODE_PRIVATE);
        index = pref.getInt("listPosition", -2);
        String radio = pref.getString("shortTitle", "");


        String darkGrayColor = "#AAAAAA";
        String lightGrayColor = "#EEEEEE";



        TextView tv1 = (TextView) cellView.findViewById(R.id.textView_date);
        TextView tv2 = (TextView) cellView.findViewById(R.id.textView_shift);
        cellView.setBackgroundColor(Color.parseColor(lightGrayColor));
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);




        // Get dateTime of this cell
        DateTime dateTime = this.datetimeList.get(position);

        ShiftCalculate calculate = new ShiftCalculate(index, radio);
        calculate.setDate(dateTime.getDay(), dateTime.getMonth(), dateTime.getYear());
        // Set color of the dates in previous / next month
        if (dateTime.getMonth() != month) {
            tv1.setTextColor(Color.parseColor(darkGrayColor));
            tv2.setTextColor(Color.parseColor(darkGrayColor));
            cellView.setBackgroundColor(Color.WHITE);
        }


        tv2.setText(calculate.getShiftByDate());


        tv1.setText("" + dateTime.getDay());


        return cellView;
    }


}
