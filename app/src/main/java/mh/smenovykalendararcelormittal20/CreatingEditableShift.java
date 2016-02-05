package mh.smenovykalendararcelormittal20;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.text.method.CharacterPickerDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import mh.smenovykalendararcelormittal20.templates.StaticShiftTemplate;

public class CreatingEditableShift extends AppCompatActivity {

    Database database;
    int positionShift;
    EditText shift_kind, title, shortTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_editable_shift);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = new Database(this);

        shift_kind = (EditText) findViewById(R.id.editext_shift_num);
        title = (EditText)findViewById(R.id.editext_shift_title);
        shortTitle = (EditText)findViewById(R.id.editext_shift_shortTitle);

        shift_kind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatingEditableShift.this, StaticShiftChoose.class);
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                positionShift =  data.getIntExtra("position", -1);
                shift_kind.setText(StaticShiftTemplate.getStringArray().get(positionShift));

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //canceled result
            }
        }
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
            database.insertShift(title.getText().toString(), shortTitle.getText().toString(), positionShift, Color.RED);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
