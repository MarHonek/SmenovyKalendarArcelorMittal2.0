package mh.shiftcalendaram;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.roomorama.caldroid.CaldroidGridAdapter;


import java.util.ArrayList;
import java.util.HashMap;

import hirondelle.date4j.DateTime;
import mh.shiftcalendaram.templates.AlternativeShifts;
import mh.shiftcalendaram.templates.ShiftNotesTemplate;

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
    Database data;
    ArrayList<ShiftNotesTemplate> notes;
    ArrayList<AlternativeShifts> alter;


    /// upravit názvy proměných a třídy aspol. u Alternative/custom/special shift


    public CalendarCustomAdapter(Context context, int month, int year, HashMap<String, Object> caldroidData, HashMap<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
        data = new Database(context);
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
        ImageView note;

        tv1 = (TextView) cellView.findViewById(R.id.textView_date);
        tv2 = (TextView) cellView.findViewById(R.id.textView_shift);
        note = (ImageView)cellView.findViewById(R.id.imageView_note);

        DateTime dateTime = this.datetimeList.get(position);

        new TestAsync(calculate,tv2,tv1,dateTime, cellView).execute();

        for(int i = 0;i<notes.size();i++)
        {
            if(Integer.parseInt(notes.get(i).getYear()) == dateTime.getYear())
            {
                if (Integer.parseInt(notes.get(i).getMonth()) == dateTime.getMonth()) {
                    if (notes.get(i).getPosition() == dateTime.getDay()) {
                        note.setVisibility(View.VISIBLE);
                        break;
                    }
                }else break;
            }
            else
            {
                break;
            }
        }


        for(int i = 0;i<alter.size();i++) {
            if (Integer.parseInt(alter.get(i).getYear()) == dateTime.getYear()) {
                if (Integer.parseInt(alter.get(i).getMonth()) == dateTime.getMonth()) {
                    if (alter.get(i).getPosition() == dateTime.getDay()) {
                        cellView.setBackgroundColor(Color.parseColor(alter.get(i).getColor()));
                        tv2.setText(alter.get(i).getKind());
                    }
                }

            }
        }



        return cellView;
    }

    @Override
    public void notifyDataSetChanged() {
        aVoid();
        super.notifyDataSetChanged();
    }

    class TestAsync extends AsyncTask<Void, Integer, String>
    {
        ShiftCalculate calculate;
        TextView tv2,tv1;
        int day,monthS,yearS;
        DateTime dateTime;
        View cellView;


        String lightGrayColor = "#EEEEEE";
        String darkGrayColor = "#AAAAAA";

        public TestAsync(ShiftCalculate calculate, TextView tv2, TextView tv1, DateTime dateTime, View cellView)
        {
            this.calculate = calculate;
            this.tv2 = tv2;
            this.tv1 = tv1;
            this.dateTime = dateTime;
            this.cellView = cellView;
        }

        protected void onPreExecute (){



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
            tv1.setText("" + dateTime.getDay());
        }
    }





    public void aVoid()
    {

                SharedPreferences pref = context.getSharedPreferences("shift", Context.MODE_PRIVATE);
                int index = pref.getInt("listPosition", -2);
                String radio = pref.getString("shortTitle", "");
                calculate = new ShiftCalculate(index, radio);


        notes = data.getTextNote();
        alter = data.getSpecial();

    }


}
