package mh.smenovykalendararcelormittal20.calendarHeader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import mh.smenovykalendararcelormittal20.R;

public class FragmentDefault extends Fragment {

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	         return inflater.inflate(R.layout.main_fragment_default, container, false);
	        
	 }      
	   	

	
}
