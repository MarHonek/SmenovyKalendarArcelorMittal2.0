package mh.smenovykalendararcelormittal20;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

/**
 * Created by Martin on 03.02.2016.
 */
public class CalendarCustomFragment extends CaldroidFragment {

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        return new CalendarCustomAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);
    }
}
