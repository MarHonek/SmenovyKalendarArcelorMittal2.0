package mh.smenovykalendararcelormittal20;

import android.view.View;

import com.antonyt.infiniteviewpager.InfiniteViewPager;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

/**
 * Created by Martin on 03.02.2016.
 */
public class CalendarCustomFragment extends CaldroidFragment {


    InfiniteViewPager pager;
    CalendarCustomAdapter adapter;

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        // TODO Auto-generated method stub
        adapter = new CalendarCustomAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);
        return adapter;
    }

    public void aa()
    {
        adapter.aVoid();
    }









}
