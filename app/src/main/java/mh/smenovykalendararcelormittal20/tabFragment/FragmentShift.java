package mh.smenovykalendararcelormittal20.tabFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mh.smenovykalendararcelormittal20.CreatingEditableShift;
import mh.smenovykalendararcelormittal20.Database;
import mh.smenovykalendararcelormittal20.ManagingActivity;
import mh.smenovykalendararcelormittal20.R;
import mh.smenovykalendararcelormittal20.adapters.ShiftListViewAdapter;
import mh.smenovykalendararcelormittal20.templates.ListTemplates;
import mh.smenovykalendararcelormittal20.templates.ShiftSymbolTemplates;
import mh.smenovykalendararcelormittal20.templates.ShiftTemplate;
import mh.smenovykalendararcelormittal20.templates.StaticShiftTemplate;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                SharedPreferences pref = getActivity().getSharedPreferences("shift", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("positionOfCustom", position);
                editor.putInt("listPosition", list.get(position).getPosition());
                editor.putString("shortTitle", list.get(position).getShortTitle());
                editor.putBoolean("customShift", true);
                editor.commit();

                getActivity().finish();

            }
        });


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
        ArrayList<ListTemplates> adapterList = new ArrayList<>();
        ArrayList<String> staticShifts = StaticShiftTemplate.getStringArray();
        if(list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                adapterList.add(new ListTemplates(list.get(i).getTitle(), list.get(i).getShortTitle(), list.get(i).getColor(), staticShifts.get(list.get(i).getPosition())));
            }
            adapter = new ShiftListViewAdapter(getContext(), adapterList);
            listView.setAdapter(adapter);
        }
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
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.ic_edit)
            {
                Intent intent = new Intent(getActivity(), CreatingEditableShift.class);
                intent.putExtra("edit", true);
                intent.putExtra("position", position);
                startActivity(intent);
                mode.finish();

            }
            else if (item.getItemId() == R.id.ic_delete)
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Smazat vlastní schéma");
                builder.setMessage("Opravdu chcete smazat toto vlastní schéma?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deleteShift(position);
                        getShiftsFromDatabase();
                        mode.finish();
                    }
                });
                builder.setNegativeButton("Zrušit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mode.finish();

                    }
                });
                builder.show();

            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

    }
}
