package mh.shiftcalendaram;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mh.shiftcalendaram.templates.StaticShiftTemplate;

public class StaticShiftChoose extends AppCompatActivity {

    ListView listView;
    ArrayList<String> array;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_static_shift);

        listView = (ListView)findViewById(R.id.listView_static_shift);
        array = StaticShiftTemplate.getStringArray();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("position",position);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        listView.setAdapter(adapter);
    }
}
