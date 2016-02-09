package mh.smenovykalendararcelormittal20.tabFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import mh.smenovykalendararcelormittal20.CreatingEditableShift;
import mh.smenovykalendararcelormittal20.CreatingSymbol;
import mh.smenovykalendararcelormittal20.Database;
import mh.smenovykalendararcelormittal20.R;
import mh.smenovykalendararcelormittal20.adapters.ShiftListViewAdapter;
import mh.smenovykalendararcelormittal20.templates.ListTemplates;
import mh.smenovykalendararcelormittal20.templates.ShiftSymbolTemplates;

/**
 * Created by Martin on 31.01.2016.
 */
public class FragmentSymbol extends Fragment{

    ShiftListViewAdapter adapter;
    ListView listView;
    Database database;
    ArrayList<ShiftSymbolTemplates> list;
    ActionMode mActionMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_symbol, container, false);

        listView = (ListView) v.findViewById(R.id.listView_symbols);

        database = new Database(getContext());
        list = database.getSymbols();

        ArrayList<ListTemplates> adapterList = (ArrayList<ListTemplates>) list.clone();

        adapter = new ShiftListViewAdapter(getContext(), adapterList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ActionBarCallBack(position));
                return true;
            }
        });

        return v;
    }


    public void getSymbolsFromDatabase()
    {
        list = database.getSymbols();
        ArrayList<ListTemplates> adapterList = (ArrayList<ListTemplates>) list.clone();
        adapter = new ShiftListViewAdapter(getContext(), adapterList);
        listView.setAdapter(adapter);
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
                Intent intent = new Intent(getActivity(), CreatingSymbol.class);
                intent.putExtra("edit", true);
                intent.putExtra("position", position);
                startActivity(intent);
            }
            else if (item.getItemId() == R.id.ic_delete)
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Smazat směnu");
                builder.setMessage("Opravdu chcete smazat tuto směnu?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deleteSymbol(position);
                        getSymbolsFromDatabase();
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
