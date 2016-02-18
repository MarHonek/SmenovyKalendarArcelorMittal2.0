package mh.shiftcalendaram.tabFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mh.shiftcalendaram.R;
import mh.shiftcalendaram.templates.StaticShiftTemplate;

/**
 * Created by Martin on 31.01.2016.
 */
public class FragmentStaticShift extends Fragment {

    ListView listView;
    ArrayList<String> array;
    ArrayAdapter<String> adapter;

    String sh = "";

    AlertDialog alertDialog;



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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final CharSequence[] items = getItemss(position);



                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Zvolte směnu");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {


                        switch(item)
                        {
                            case 0:
                                sh = "A";
                                break;
                            case 1:
                                sh = "B";

                                break;
                            case 2:
                                sh = "C";
                                break;
                            case 3:
                                sh = "D";
                                break;

                        }


                        SharedPreferences pref = getActivity().getSharedPreferences("shift", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("positionOfCustom", -1);
                        editor.putInt("listPosition", position);
                        editor.putString("shortTitle", sh);
                        editor.putBoolean("customShift", false);
                        editor.commit();

                        getActivity().finish();
                        alertDialog.dismiss();


                    }
                })
                        .setNegativeButton("Zrušit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();

            }
        });

        return v;
    }

    public CharSequence[] getItemss(int position)
    {
        StaticShiftTemplate staticS = StaticShiftTemplate.createList().get(position);
        int i = staticS.getNumberOfShits();
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");


        if(i == 3) {
            list.add("C");
        }
        else if(i == 4) {
            list.add("C");
            list.add("D");
        }

        CharSequence[] copyList = list.toArray(new CharSequence[list.size()]);


        return copyList;


    }

}
