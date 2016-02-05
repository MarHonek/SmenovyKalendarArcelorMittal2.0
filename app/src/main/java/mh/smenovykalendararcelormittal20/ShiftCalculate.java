package mh.smenovykalendararcelormittal20;

import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Martin on 03.02.2016.
 */
public class ShiftCalculate {

    String[] schemeChar;
    String[] monthShift = new String[42];

    private void charOfScheme(String shiftScheme)
    {

        schemeChar = shiftScheme.split(";");

    }

    public String[] monthShifts(int firstdayPosition, String shiftScheme,Calendar calendar)
    {
        charOfScheme(shiftScheme);
        Calendar gr = (Calendar) calendar.clone();
        int numberOfdaysInYearToday = gr.get(GregorianCalendar.DAY_OF_YEAR) - gr.get(GregorianCalendar.DAY_OF_MONTH);


        if(gr.get(Calendar.YEAR) > 2014)
        {
            int numberOfYear = gr.get(Calendar.YEAR) - 2014;
            for(int i = 0;i < numberOfYear;i++)
            {
                gr.set(Calendar.YEAR, 2014+i);
                numberOfdaysInYearToday += gr.getActualMaximum(Calendar.DAY_OF_YEAR);
            }
        }
        else
        {
            int numberOfYear = gr.get(Calendar.YEAR) - 2014;
            for(int i = numberOfYear; i < 0;i++)
            {
                gr.set(Calendar.YEAR, 2014-i);
                numberOfdaysInYearToday -= gr.getActualMaximum(Calendar.DAY_OF_YEAR);
            }
        }

        int schemeCharFirstDayIndex = numberOfdaysInYearToday % schemeChar.length;

        int startSchemeChar = schemeCharFirstDayIndex - firstdayPosition;
        startSchemeChar +=1;
        if(startSchemeChar < 0)
        {
            while(startSchemeChar<0)
            {
                startSchemeChar = startSchemeChar + schemeChar.length;
            }
        }

        for(int i = 0;i<monthShift.length;i++)
        {
            monthShift[i] = schemeChar[startSchemeChar];
            startSchemeChar++;
            if(startSchemeChar > schemeChar.length-1)
                startSchemeChar = 0;
        }


        return monthShift;
    }

    public static int getMorning(String scheme, String shift)
    {
        Log.v("dwq", scheme);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar2.set(calendar.get(Calendar.YEAR), 0, 1);

        long diff = calendar.getTimeInMillis() - calendar2.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);

        int length = scheme.length();
        String[] g = scheme.split("");
        int count = 0;

        int dd = (int)days / length;
        int ddd = (int)days % length;
        Log.v("nevim", String.valueOf(length));
        for(int i = 0; i < dd;i++)
        {
            for(int j = 0; j < length;j++)
            {
                if(g[j].equals(shift))
                    count++;
            }
        }

        for(int k = 0; k < ddd;k++)
        {
            if(g[k].equals(shift))
                count++;
        }
        return count;

    }



}
