package mh.shiftcalendaram;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.antonyt.infiniteviewpager.InfiniteViewPager;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import mh.shiftcalendaram.calendarHeader.FragmentCustom;
import mh.shiftcalendaram.calendarHeader.FragmentOriginal;
import mh.shiftcalendaram.oldCalendar.CalendarAdapter;
import mh.shiftcalendaram.oldCalendar.Holidays;
import mh.shiftcalendaram.templates.AlternativeShifts;
import mh.shiftcalendaram.templates.ShiftNotesTemplate;
import mh.shiftcalendaram.templates.ShiftTemplate;
import mh.shiftcalendaram.templates.StaticShiftTemplate;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {



    SharedPreferences pref;



    GridView gridview;
    GestureDetectorCompat gestureDetectorCompat;


    String[] monthTitles = new String[] {"Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Září", "Říjen", "Listopad", "Prosinec"};


    public GregorianCalendar month;// calendar instances.
    public CalendarAdapter adapter;// adapter instance
    View pom;
    int itemPositionClick = -1;
    int positionOfCustom;

    int positionOfShifts;
    boolean cancel = false;
    LinearLayout linNote;

    boolean customShift;
    int defaultYear;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      //  fromPreftoSharedPref();

        gridview = (GridView) findViewById(R.id.gridView_calendar);

        gestureDetectorCompat = new GestureDetectorCompat(this,this);

        final Database datab = new Database(MainActivity.this);

        //part of creates instances
        month = (GregorianCalendar) GregorianCalendar.getInstance();

        defaultYear = month.get(GregorianCalendar.YEAR);


        setCalendarAdapter();

        getSupportActionBar().setTitle(adapter.getActualMonthAndYear(monthTitles));

      //  getSupportActionBar().setTitle(adapter.getActualMonthString(monthTitles) + " " + adapter.getActualYear());

        gridview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub;

                if (gestureDetectorCompat.onTouchEvent(event)) {
                    MotionEvent cancelEvent = MotionEvent.obtain(event);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                    v.onTouchEvent(cancelEvent);
                    return true;
                }
                return false;
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String textNote = "";
                String holiday= "";
                ArrayList<ShiftNotesTemplate> alterList = datab.getTextNote();



                for(int i = 0; i < alterList.size();i++)
                {
                    if((alterList.get(i).getPosition() == position) && (Integer.parseInt(alterList.get(i).getMonth()) == month.get(Calendar.MONTH)) && ((Integer.parseInt(alterList.get(i).getYear()) == month.get(Calendar.YEAR))) && (alterList.get(i).getCustom() == positionOfCustom))
                    {
                        textNote = alterList.get(i).getNotes();
                    }
                }

                ArrayList<Holidays> holidays = Holidays.getHolidayList();
                for(int i = 0; i < holidays.size();i++)
                {
                    if((holidays.get(i).getDay() == Integer.parseInt(adapter.getSelectedDay(position)))&&(holidays.get(i).getMonth().equals(adapter.getActualMonth())) && (adapter.isPositionInActualMonth(position)))
                    {
                        holiday = holidays.get(i).getName();

                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                if(holiday == "")
                {
                    builder.setMessage("Poznámka:\n" + textNote)
                            .setTitle("Poznámky a Svátky")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                    dialog.dismiss();
                                }
                            });
                }
                else if(textNote == "")
                {
                    builder.setMessage("Svátek:\n"+holiday)
                            .setTitle("Poznámky a Svátky")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                    dialog.dismiss();
                                }
                            });
                }
                else
                {
                    builder.setMessage("Poznámka:\n"+textNote + "\n\n" + "Svátek:\n"+holiday)
                            .setTitle("Poznámky a Svátky")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                    dialog.dismiss();
                                }
                            });
                }
                // 3. Get the AlertDialog from create()
                if((textNote != "") || (holiday != ""))
                {
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


                adapter.setClickOnItems(position);
                //((CalendarAdapter) parent.getAdapter()).setSelected(view);

                getSupportActionBar().setTitle(adapter.getActualMonthAndYear(monthTitles));


            }

        });

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                final int positionOfGrid = position;
                if((customShift) && (adapter.isPositionInActualMonth(positionOfGrid)))
                {
                    Intent intent = new Intent(MainActivity.this, ChangeShiftActivity.class);
                    intent.putExtra("Month", adapter.getActualMonth());
                    intent.putExtra("Year", adapter.getActualYear());
                    intent.putExtra("month", month);
                    intent.putExtra("position", position);
                    intent.putExtra("positionOfCustom", positionOfCustom);
                    intent.putExtra("day", adapter.getSelectedDay(position));

                    startActivity(intent);
                }
                return true;

            }
        });


    }


    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // TODO Auto-generated method stub

        if(velocityX > 100)
        {
            adapter.setPreviousMonth();
            adapter.refreshCalendar();
            getSupportActionBar().setTitle(adapter.getActualMonthAndYear(monthTitles));

        }
        else if(velocityX < -50)
        {
            adapter.setNextMonth();
            adapter.refreshCalendar();
            getSupportActionBar().setTitle(adapter.getActualMonthAndYear(monthTitles));
        }
        return true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ic_managing) {
            startActivity(new Intent(MainActivity.this,ManagingActivity.class));
            return true;
        }
        else if (id == R.id.ic_today)
        {
            adapter.defaultMonth();
            adapter.defaultYear(defaultYear);
            adapter.refreshCalendar();
            getSupportActionBar().setTitle(adapter.getActualMonthAndYear(monthTitles));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        setCalendarAdapter();
        super.onResume();
    }

    public String getCzechMonth(int month)
    {
        String[] months = new String[]{"Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Září", "Říjen", "Listopad", "Prosinec"};
        return months[month];
    }

    public void setCalendarAdapter()
    {

        SharedPreferences pref = getSharedPreferences("shift",Context.MODE_PRIVATE);
        customShift = pref.getBoolean("customShift", false);
        positionOfCustom = pref.getInt("positionOfCustom", -2);
        positionOfShifts = pref.getInt("listPosition", -2);
        String radio = pref.getString("shortTitle", "");
        ArrayList<StaticShiftTemplate> shifts = StaticShiftTemplate.createList();

        if (positionOfShifts != -2) {

            adapter = new CalendarAdapter(this, month, shifts.get(positionOfShifts).getABCDShifts(radio) , customShift, positionOfCustom);
            gridview.setAdapter(adapter);

            Fragment fr = null;

            if (!customShift)
                fr = new FragmentOriginal();
            else fr = new FragmentCustom();


            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();


        }
        else
        {
            adapter = new CalendarAdapter(this, month,"" , customShift, positionOfCustom);
            gridview.setAdapter(adapter);
        }
    }

    public void fromPreftoSharedPref()
    {


        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        boolean customShift = pref.getBoolean("customShift", false);
        int positionOfCustom = pref.getInt("positionOfCustom", -2);
        int positionOfShifts = pref.getInt("listPosition", -2);
        String radio = pref.getString("shortTitle", "");
        ArrayList<StaticShiftTemplate> shifts = StaticShiftTemplate.createList();


        SharedPreferences sharedPreferences = getSharedPreferences("shift",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("positionOfCustom", positionOfCustom);
        editor.putInt("listPosition", positionOfShifts);
        editor.putString("shortTitle", radio);
        editor.putBoolean("customShift", customShift);
        editor.commit();
    }



}
