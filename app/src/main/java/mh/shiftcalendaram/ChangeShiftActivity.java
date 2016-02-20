package mh.shiftcalendaram;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mh.shiftcalendaram.adapters.ShiftListViewAdapter;
import mh.shiftcalendaram.templates.AlternativeShifts;
import mh.shiftcalendaram.templates.ListTemplates;
import mh.shiftcalendaram.templates.ShiftNotesTemplate;
import mh.shiftcalendaram.templates.ShiftSymbolTemplates;

public class ChangeShiftActivity extends AppCompatActivity {

    Database database;
    ListView listView;
    String color;
    LinearLayout shiftLayout, icon;
    ImageView deleteShift;

    GradientDrawable background;

    ShiftListViewAdapter adapter;
    ArrayList<ShiftSymbolTemplates> list;
    ArrayList<ShiftNotesTemplate> notes;
    ShiftSymbolTemplates symbol;
    int positionOfSymbol;
    EditText note;

    TextView changeShiftTitle, iconText, date;
    ImageView deleteShiftIcon;
    Button deleteNote;
    SharedPreferences pref;
    String day,month,year;

    String noteText = "";

    int positionOfCalendar;

    int symbolEditIndex;

    int positionOfCustom;

    boolean editAlter = false;
    boolean editNote = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_shift);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database= new Database(ChangeShiftActivity.this);
        getSymbolsFromDatabase();

        color = "#ff9800";
        positionOfSymbol = -1;

        shiftLayout = (LinearLayout)findViewById(R.id.linearLayout_change_shift);
        changeShiftTitle = (TextView)findViewById(R.id.textView_change_shift_title);
        iconText = (TextView)findViewById(R.id.textView_change_shift_icon_text);
        icon = (LinearLayout) findViewById(R.id.linearLayout_change_shift_circle);
        date = (TextView)findViewById(R.id.textView_change_shift_date);
        deleteShift = (ImageView)findViewById(R.id.imageView_change_shift_delete_shift);
        deleteShiftIcon = (ImageView)findViewById(R.id.imageView_change_shift_delete_shift);

        note = (EditText)findViewById(R.id.editext_change_snift_note);
        deleteNote = (Button)findViewById(R.id.button_change_shift_delete_text);


        pref = getSharedPreferences("shift", Context.MODE_PRIVATE);
        positionOfCustom = pref.getInt("positionOfCustom", -1);

        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        month = intent.getStringExtra("Month");
        year = intent.getStringExtra("Year");
        positionOfCalendar = intent.getIntExtra("position", -1);
        positionOfCustom = intent.getIntExtra("positionOfCustom", -1);
        date.setText(day + ". " + month + ". " + year);

        notes = database.getTextNote();

        noteText = noteForToday(notes,positionOfCalendar,month,year, positionOfCustom);
        note.setText(noteText);

        background = (GradientDrawable) icon.getBackground();
        background.setColor(getResources().getColor(R.color.colorPrimary));


        ArrayList<AlternativeShifts> alter = database.getSpecial();
        for (int i = 0; i<alter.size();i++)
        {
            if ((alter.get(i).getPosition() == positionOfCalendar) && (alter.get(i).getMonth().equals(month)) && (alter.get(i).getYear()).equals(year) &&(alter.get(i).getCustom() == positionOfCustom))
            {

                for (int j = 0;j<list.size();j++)
                {
                    if (list.get(j).getShortTitle().equals(alter.get(i).getKind())) {
                        changeShiftTitle.setText(list.get(j).getTitle());
                        symbolEditIndex = j;
                    }
                }
                background = (GradientDrawable) icon.getBackground();
                background.setColor(Color.parseColor(alter.get(i).getColor()));

                iconText.setText(alter.get(i).getKind());
                deleteShiftIcon.setVisibility(View.VISIBLE);
                editAlter = true;
            }
        }

        deleteShiftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteAlter(positionOfCalendar, month, year, positionOfCustom);

                background = (GradientDrawable) icon.getBackground();
                background.setColor(getResources().getColor(R.color.colorPrimary));


                iconText.setText("?");
                changeShiftTitle.setText("Vybrat směnu");

                positionOfSymbol = -1;

            }
        });


        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setText("");
            }
        });

        shiftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeShiftActivity.this);

                View view =  getLayoutInflater().inflate(R.layout.fragment_shift, null);

              //  LinearLayout lin = (LinearLayout)view.findViewById(R.id.linearLayout_nocalendar);
                listView = (ListView) view.findViewById(R.id.listView_shift);
                listView.setAdapter(adapter);

                builder.setTitle("Vyberte směnu");
                builder.setView(view);
                final AlertDialog alert = builder.create();
                alert.show();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0,
                                            View arg1, int arg2, long arg3) {

                        positionOfSymbol = arg2;

                        changeShiftTitle.setText(list.get(positionOfSymbol).getTitle());
                        iconText.setText(list.get(positionOfSymbol).getShortTitle());
                        GradientDrawable background = (GradientDrawable) icon.getBackground();
                        background.setColor(list.get(positionOfSymbol).getColor());
                        alert.dismiss();

                    }

                });


            }
        });



    }

    public void getSymbolsFromDatabase()
    {
        list = database.getSymbols();
        ArrayList<ListTemplates> adapterList = (ArrayList<ListTemplates>) list.clone();
        adapter = new ShiftListViewAdapter(ChangeShiftActivity.this, adapterList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creating_shift, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ic_accept) {
            if (positionOfSymbol != -1)
            {
                String color = String.format("#%06X", (0xFFFFFF & list.get(positionOfSymbol).getColor()));
                if(editAlter)
                {

                    database.updateAlter(positionOfCalendar,month,year,positionOfCustom,list.get(positionOfSymbol).getShortTitle(), color);
                }
                else
                    database.insertAlternative(list.get(positionOfSymbol).getShortTitle(), positionOfCalendar,month,year,positionOfCustom, color);
            }




            if(note.getText().length() > 0)
            {
                if (editNote)
                database.updateNote(positionOfCalendar,month,year,positionOfCustom,note.getText().toString());
                else
                database.insertNote(positionOfCalendar, month,year, note.getText().toString(),positionOfCustom);
            }
            else
            {
                if (!noteText.equals(""))
                {
                    database.deleteNotes(positionOfCalendar,month,year,positionOfCustom);
                }
            }
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public String noteForToday(ArrayList<ShiftNotesTemplate> notes, int day, String month, String year, int positionOfCustom)
    {
        String note = "";
        for (int i = 0; i < notes.size();i++)
        {
            if((notes.get(i).getYear().equals(year)) && (notes.get(i).getMonth().equals(month)) && (notes.get(i).getPosition() == day)&& (notes.get(i).getCustom() == positionOfCustom))
            {
                note = notes.get(i).getNotes();
                editNote = true;
            }
        }

        return  note;

    }





}
