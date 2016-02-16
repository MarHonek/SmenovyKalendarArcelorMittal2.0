package mh.smenovykalendararcelormittal20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.antonyt.infiniteviewpager.InfiniteViewPager;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import mh.smenovykalendararcelormittal20.calendarHeader.FragmentCustom;
import mh.smenovykalendararcelormittal20.calendarHeader.FragmentOriginal;

public class MainActivity extends AppCompatActivity {

    private boolean undo = false;
    private CalendarCustomFragment caldroidFragment;

    SharedPreferences pref;

    boolean customShift;
    int index;
    InfiniteViewPager infiniteViewPager;
    LinearLayout lin;



    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        switchCustomOriginalFragment();

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CalendarCustomFragment();




        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
            args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);

            // Uncomment this line to use Caldroid in compact mode







            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }





        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
              /*  Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();*/

                caldroidFragment.setBackgroundResourceForDate(Color.RED, date);
                caldroidFragment.refreshView();
            }

            @Override
            public void onChangeMonth(int month, int year) {
             //   String text = "month: " + month + " year: " + year;
              /*  Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();*/

                getSupportActionBar().setTitle(getCzechMonth(month-1) + " " + String.valueOf(year));

            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Intent intent = new Intent(MainActivity.this,ChangeShiftActivity.class);

                String intMonth = (String) android.text.format.DateFormat.format("MM", date); //06
                String year = (String) android.text.format.DateFormat.format("yyyy", date); //2013
                String day = (String) android.text.format.DateFormat.format("dd", date); //20

                intent.putExtra("month", intMonth);
                intent.putExtra("year", year);
                intent.putExtra("day", day);
                SharedPreferences pref = getSharedPreferences("shift", Context.MODE_PRIVATE);

                if(customShift) {
                    startActivity(intent);
                }
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    hideHeader(caldroidFragment);
                  /*  Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();*/

                }



            }



        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);


        // Customize the calendar



        final Bundle state = savedInstanceState;








    }
    private void hideHeader(CaldroidFragment fragCalendar) {
        fragCalendar.getMonthTitleTextView().setVisibility(View.GONE);
        fragCalendar.getLeftArrowButton().setVisibility(View.GONE);
        fragCalendar.getRightArrowButton().setVisibility(View.GONE);
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
            Calendar today = Calendar.getInstance();
            Date todayTime = today.getTime();
            caldroidFragment.moveToDate(todayTime);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        switchCustomOriginalFragment();
        caldroidFragment.aa();
        caldroidFragment.refreshView();
        super.onResume();
    }

    public String getCzechMonth(int month)
    {
        String[] months = new String[]{"Leden", "Únor", "Březen", "Duben", "Květen", "Červen", "Červenec", "Srpen", "Září", "Říjen", "Listopad", "Prosinec"};
        return months[month];
    }

    public void switchCustomOriginalFragment()
    {
        pref = getSharedPreferences("shift", Context.MODE_PRIVATE);
        customShift = pref.getBoolean("customShift", false);
        index = pref.getInt("listPosition", -2);


        if (index != -2) {
            Fragment fr = null;

            if (!customShift)
                fr = new FragmentOriginal();
            else fr = new FragmentCustom();


            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fr);
            fragmentTransaction.commit();
        }
    }




    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }

    }
}
