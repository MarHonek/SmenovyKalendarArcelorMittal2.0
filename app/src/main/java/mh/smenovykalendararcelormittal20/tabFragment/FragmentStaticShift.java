package mh.smenovykalendararcelormittal20.tabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mh.smenovykalendararcelormittal20.R;
import mh.smenovykalendararcelormittal20.adapters.ShiftListViewAdapter;
import mh.smenovykalendararcelormittal20.templates.StaticShiftTemplate;

/**
 * Created by Martin on 31.01.2016.
 */
public class FragmentStaticShift extends Fragment {

    ListView listView;
    ArrayList<String> array;
    ArrayAdapter<String> adapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_static_shift, container, false);
        listView = (ListView)v.findViewById(R.id.listView_static_shift);
        array = StaticShiftTemplate.getStringArray();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, array);

        listView.setAdapter(adapter);

        return v;
    }
}
