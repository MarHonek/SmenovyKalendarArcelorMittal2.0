package mh.smenovykalendararcelormittal20;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;


import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
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

    ShiftCalculate calculate;


    public CalendarCustomAdapter(Context context, int month, int year, HashMap<String, Object> caldroidData, HashMap<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
        aVoid();

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



        TextView tv1;
        TextView tv2;

        tv1 = (TextView) cellView.findViewById(R.id.textView_date);
        tv2 = (TextView) cellView.findViewById(R.id.textView_shift);

        DateTime dateTime = this.datetimeList.get(position);

        new TestAsync(calculate,tv2,dateTime).execute();

        tv1.setText("" + dateTime.getDay());


        String lightGrayColor = "#EEEEEE";
        String darkGrayColor = "#AAAAAA";


        cellView.setBackgroundColor(Color.parseColor(lightGrayColor));
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);


        if (dateTime.getMonth() != month) {
            tv1.setTextColor(Color.parseColor(darkGrayColor));
            tv2.setTextColor(Color.parseColor(darkGrayColor));
            cellView.setBackgroundColor(Color.WHITE);
        }

        if (dateTime.equals(getToday())) {
            cellView.setBackgroundResource(com.caldroid.R.drawable.red_border_gray_bg);
        }



        return cellView;
    }

    class TestAsync extends AsyncTask<Void, Integer, String>
    {
        ShiftCalculate calculate;
        TextView tv2;
        int day,monthS,yearS;
        DateTime dateTime;

        public TestAsync(ShiftCalculate calculate, TextView tv2, DateTime dateTime)
        {
            this.calculate = calculate;
            this.tv2 = tv2;
            this.dateTime = dateTime;
        }

        protected void onPreExecute (){
        }

        protected String doInBackground(Void...arg0) {

            day = dateTime.getDay();
            int monthS = dateTime.getMonth();
            int yearS = dateTime.getYear();
            calculate.setDate(day, monthS, yearS);
            String shift = calculate.getShiftByDate();

            return shift;
        }


        protected void onProgressUpdate(Integer...a){
        }

        protected void onPostExecute(String result) {
            tv2.setText(result);
        }
    }





    public void aVoid()
    {

                SharedPreferences pref = context.getSharedPreferences("shift", Context.MODE_PRIVATE);
                int index = pref.getInt("listPosition", -2);
                String radio = pref.getString("shortTitle", "");
                calculate = new ShiftCalculate(index, radio);

    }


}
