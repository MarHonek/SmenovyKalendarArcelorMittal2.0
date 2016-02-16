package mh.smenovykalendararcelormittal20.calendarHeader;



import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import mh.smenovykalendararcelormittal20.MainActivity;
import mh.smenovykalendararcelormittal20.R;
import mh.smenovykalendararcelormittal20.templates.StaticShiftTemplate;

public class FragmentOriginal extends Fragment {

	 RadioButton c, d;
		RadioGroup group; 
		TextView nameOfShift;
		LinearLayout nameOfShiftLin;
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	         View v = inflater.inflate(R.layout.main_fragment_original, container, false);
	        
	 		nameOfShiftLin = (LinearLayout)v.findViewById(R.id.linearLayout_nameOfShifts);
			nameOfShift = (TextView)v.findViewById(R.id.textView_nameOfShifts);

			
			

			SharedPreferences pref = getActivity().getSharedPreferences("shift", Context.MODE_PRIVATE);
			int positionOfShifts = pref.getInt("listPosition", -2);
			String radio = pref.getString("shortTitle", "");
			ArrayList<StaticShiftTemplate> shifts = StaticShiftTemplate.createList();
			
			nameOfShift.setText(shifts.get(positionOfShifts).getTitle() + " - " + radio);
	         return v;
	    }

	 
		
}
