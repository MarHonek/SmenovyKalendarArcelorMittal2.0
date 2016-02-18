package mh.shiftcalendaram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import mh.shiftcalendaram.colorPicker.ColorAdapter;
import mh.shiftcalendaram.templates.ShiftTemplate;
import mh.shiftcalendaram.templates.StaticShiftTemplate;

public class CreatingEditableShift extends AppCompatActivity {

    Database database;
    int positionShift;
    EditText shift_kind, title;
    TextInputLayout titleLayout, shiftKindLayout;
    LinearLayout icon;
    ArrayList<ShiftTemplate> shifts;
    boolean edit = false;
    int position = -1;


    GridView gridview;
    int colorShift;
    String color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_editable_shift);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new Database(this);

        color = "#ff9800";



        shift_kind = (EditText) findViewById(R.id.editext_shift_num);
        title = (EditText)findViewById(R.id.editext_shift_title);
        icon = (LinearLayout)findViewById(R.id.shift_icon);

        titleLayout = (TextInputLayout) findViewById(R.id.input_layout_shift_title);
        shiftKindLayout = (TextInputLayout)findViewById(R.id.input_layout_shift_num);


        GradientDrawable background = (GradientDrawable) icon.getBackground();
        background.setColor(getResources().getColor(R.color.colorPrimary));

        edit = getIntent().getBooleanExtra("edit", false);

        if(edit)
        {
            position = getIntent().getIntExtra("position", -1);
            shifts = database.getAllShifts();
            title.setText(shifts.get(position).getTitle());
            background = (GradientDrawable) icon.getBackground();
            background.setColor(shifts.get(position).getColor());
            shift_kind.setText(StaticShiftTemplate.getStringArray().get(shifts.get(position).getPosition()));
        }

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatingEditableShift.this);
                GridView gridview;

                View view = getLayoutInflater().inflate(R.layout.color_dialog, null);
                builder.setTitle("Vyberte barvu");

                gridview = (GridView) view.findViewById(R.id.gridView_color);


                final ColorAdapter adapter = new ColorAdapter(CreatingEditableShift.this);
                gridview.setAdapter(adapter);

                builder.setView(view);
                final AlertDialog alert = builder.create();
                alert.show();

                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        // TODO Auto-generated method stub
                        color = adapter.getPictures(position).getColor();
                        GradientDrawable background = (GradientDrawable) icon.getBackground();
                        background.setColor(Color.parseColor(color));
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));

                        alert.dismiss();



                    }
                });

            }
        });

        shift_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatingEditableShift.this, StaticShiftChoose.class);
                startActivityForResult(intent, 1);
            }
        });


    }

    public String getCheckedRadioButton()
    {
        RadioGroup group = (RadioGroup)findViewById(R.id.radiobutton_shifts);
        int selectedId = group.getCheckedRadioButtonId();
        RadioButton radiobutton = (RadioButton)findViewById(selectedId);
        String textOfRadio = radiobutton.getText().toString();
        return textOfRadio;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                positionShift =  data.getIntExtra("position", -1);
                shift_kind.setText(StaticShiftTemplate.getStringArray().get(positionShift));
                StaticShiftTemplate staticS = StaticShiftTemplate.createList().get(positionShift);


                //if day shifts are less than 4 change visibility to invisible for C and D radiobutton
                RadioButton c = (RadioButton)findViewById(R.id.radioButton_C);
                RadioButton d = (RadioButton)findViewById(R.id.radioButton_D);

                if(staticS.getNumberOfShits() == 3)
                {
                    d.setVisibility(View.INVISIBLE);
                }
                else if(staticS.getNumberOfShits() == 2)
                {
                    c.setVisibility(View.INVISIBLE);
                    d.setVisibility(View.INVISIBLE);
                }
                else
                {
                    c.setVisibility(View.VISIBLE);
                    d.setVisibility(View.VISIBLE);
                }
                RadioButton a  = (RadioButton)findViewById(R.id.radioButton_A);
                a.setChecked(true);
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //canceled result
            }
        }
    }

    public boolean checkFormInput()
    {

        boolean ok = true;
        titleLayout.setErrorEnabled(false);

        if(title.getText().length() == 0)
        {
            titleLayout.setError("Název musí být vyplněn");
            titleLayout.setErrorEnabled(true);
            ok = false;
        }
        else titleLayout.setErrorEnabled(false);

        if (shift_kind.getText().length() == 0)
        {
            shiftKindLayout.setError("Stále schéma musí být vybráno");
            shiftKindLayout.setErrorEnabled(true);
            ok = false;
        }
        else shiftKindLayout.setErrorEnabled(false);

        shifts = database.getAllShifts();

        for(int i = 0; i < shifts.size();i++)
        {
            if(shifts.get(i).getTitle().equals(title.getText().toString().toUpperCase()))
            {
                titleLayout.setError("Název již existuje");
                titleLayout.setErrorEnabled(true);
                ok = false;

            }
        }
        return ok;
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
            if (checkFormInput()) {
                if(edit)
                    database.updateShift(title.getText().toString(), getCheckedRadioButton(), positionShift, Color.parseColor(color), position);
                else
                database.insertShift(title.getText().toString(), getCheckedRadioButton(), positionShift, Color.parseColor(color));
                this.finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
