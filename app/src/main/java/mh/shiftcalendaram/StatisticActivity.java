package mh.shiftcalendaram;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mh.shiftcalendaram.oldCalendar.ShiftCalculating;
import mh.shiftcalendaram.templates.AlternativeShifts;
import mh.shiftcalendaram.templates.ShiftSymbolTemplates;
import mh.shiftcalendaram.templates.ShiftTemplate;
import mh.shiftcalendaram.templates.StaticShiftTemplate;

public class StatisticActivity extends AppCompatActivity {



    ListView listView;
    Database data;
    TextView yearLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView_statistic);
        yearLabel = (TextView)findViewById(R.id.textView_statistic_actual_year_label);

        Calendar cal = Calendar.getInstance();
        yearLabel.setText("Statistika pro rok: " + String.valueOf(cal.get(Calendar.YEAR)));

        data = new Database(StatisticActivity.this);

        statisticListview(0);


    }


    public void statisticListview(int position) {

        List<ShiftTemplate> list;
        ArrayList<AlternativeShifts> alter;
        ArrayAdapter<String> adapter;
        String[] dataArray;


        ArrayList<ShiftSymbolTemplates> symbols = data.getSymbols();

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> counts = new ArrayList<Integer>();
        list = data.getAllShifts();

        for (int i = 0; i < symbols.size(); i++) {
            names.add(symbols.get(i).getTitle());
            counts.add(0);
        }

        names.add("Ranní");
        names.add("Odpolední");
        names.add("Noční");
        names.add("Volno");


        alter = data.getSpecial();
        Calendar cal = Calendar.getInstance();


        String d = "";
        ArrayList<StaticShiftTemplate> pernament = StaticShiftTemplate.createList();
        switch (list.get(position).getShortTitle()) {
            case "A":
                d = pernament.get(list.get(position).getPosition()).getShiftA();
                break;

            case "B":
                d = pernament.get(list.get(position).getPosition()).getShiftB();
                break;

            case "C":
                d = pernament.get(list.get(position).getPosition()).getShiftC();
                break;

            case "D":
                d = pernament.get(list.get(position).getPosition()).getShiftD();
                break;
        }

        counts.add(ShiftCalculating.getMorning(d, "R"));
        counts.add(ShiftCalculating.getMorning(d, "O"));
        counts.add(ShiftCalculating.getMorning(d, "N"));
        counts.add(ShiftCalculating.getMorning(d, "V"));


        for (int j = 0; j < alter.size(); j++) {
            if ((alter.get(j).getCustom() == position) && (alter.get(j).getYear().equals(String.valueOf(cal.get(Calendar.YEAR))))) {


                for (int k = 0; k < symbols.size(); k++) {

                    if (alter.get(j).getKind().equals(symbols.get(k).getShortTitle())) {
                        counts.set(k, counts.get(k) + 1);

                    }
                }
            }
        }

        dataArray = new String[counts.size()];
        for (int i = 0; i < counts.size(); i++) {
            dataArray[i] = names.get(i) + ": " + counts.get(i);
        }
        adapter = new ArrayAdapter<String>(StatisticActivity.this, android.R.layout.simple_list_item_1, dataArray);
        listView.setAdapter(adapter);

    }

}




