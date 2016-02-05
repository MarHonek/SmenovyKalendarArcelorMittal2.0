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
import android.widget.ListView;

import java.util.ArrayList;

import mh.smenovykalendararcelormittal20.Database;
import mh.smenovykalendararcelormittal20.R;
import mh.smenovykalendararcelormittal20.adapters.ShiftListViewAdapter;
import mh.smenovykalendararcelormittal20.templates.ListTemplates;
import mh.smenovykalendararcelormittal20.templates.ShiftSymbolTemplates;
import mh.smenovykalendararcelormittal20.templates.ShiftTemplate;

/**
 * Created by Martin on 31.01.2016.
 */
public class FragmentShift extends Fragment {

    ListView listView;
    ShiftListViewAdapter adapter;
    ArrayList<ShiftTemplate> list;
    Database database;
    ActionMode mActionMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shift, container, false);
        listView = (ListView)v.findViewById(R.id.listView_shift);

        database = new Database(getContext());

        getShiftsFromDatabase();



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ActionBarCallBack(position));
                return true;
            }
        });


        return v;
    }

    public void getShiftsFromDatabase()
    {
        list = database.getAllShifts();
        ArrayList<ListTemplates> adapterList = (ArrayList<ListTemplates>) list.clone();
        adapter = new ShiftListViewAdapter(getContext(), adapterList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        getShiftsFromDatabase();
        super.onResume();
    }

    class ActionBarCallBack implements ActionMode.Callback {

        int position;

        public ActionBarCallBack(int position)
        {
            this.position = position;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.ic_edit)
            {

            }
            else if (item.getItemId() == R.id.ic_delete)
            {
                database.deleteShift(position);
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

    }
}
