package mh.smenovykalendararcelormittal20;

import android.app.AlertDialog;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mh.smenovykalendararcelormittal20.adapters.ShiftListViewAdapter;
import mh.smenovykalendararcelormittal20.templates.ListTemplates;
import mh.smenovykalendararcelormittal20.templates.ShiftSymbolTemplates;

public class ChangeShiftActivity extends AppCompatActivity {

    Database database;
    ListView listView;
    String color;
    LinearLayout shiftLayout, icon;

    ShiftListViewAdapter adapter;
    ArrayList<ShiftSymbolTemplates> list;
    ShiftSymbolTemplates symbol;
    int positionOfSymbol;
    EditText note;

    TextView changeShiftTitle, iconText;

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

        shiftLayout = (LinearLayout)findViewById(R.id.linearLayout_change_shift);
        changeShiftTitle = (TextView)findViewById(R.id.textView_change_shift_title);
        iconText = (TextView)findViewById(R.id.textView_change_shift_icon_text);
        icon = (LinearLayout) findViewById(R.id.linearLayout_change_shift_circle);

        note = (EditText)findViewById(R.id.editext_change_snift_note);

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
           if(note.getText().length() > 0)
           {
               // database.insertNote(0, );
           }
        }
        return super.onOptionsItemSelected(item);
    }

}
