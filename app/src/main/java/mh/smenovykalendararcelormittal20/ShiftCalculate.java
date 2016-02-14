package mh.smenovykalendararcelormittal20;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mh.smenovykalendararcelormittal20.templates.StaticShiftTemplate;

/**
 * Created by Martin on 03.02.2016.
 */
public class ShiftCalculate {

    private String[] schemeCykle;
    private int day,month,year;


    public ShiftCalculate(int position, String radio)
    {
        StaticShiftTemplate staticS = StaticShiftTemplate.createList().get(position);
        String shift = "";

        switch (radio)
        {
            case "A":
                shift = staticS.getShiftA();
                break;

            case "B":
                shift = staticS.getShiftB();
                break;

            case "C":
                shift = staticS.getShiftC();
                break;

            case "D":
                shift = staticS.getShiftD();
                break;
        }

        getShemeCykle(shift);

    }

    private void getShemeCykle(String shift)
    {
        schemeCykle = shift.split(";");
    }

    public String getShiftByDate()
    {
        int days = getNumDays();
        Log.d("da", String.valueOf(days));
        int index = (days % schemeCykle.length);
        String sh  = schemeCykle[index];
        return sh;

    }

    public void setDate(int day, int month, int year)
    {
        this.day = day;
        this.month = month-1;
        this.year = year;
    }

    private int getNumDays()
    {
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(2014,0,1);
        date2.clear();
        date2.set(year, month, day);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        int dayCount = (int) (diff / (24 * 60 * 60 * 1000));
        return  dayCount;


    }



}
