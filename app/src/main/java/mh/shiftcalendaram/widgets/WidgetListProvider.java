package mh.shiftcalendaram.widgets;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import mh.shiftcalendaram.Database;
import mh.shiftcalendaram.R;
import mh.shiftcalendaram.oldCalendar.CalendarAdapter;
import mh.shiftcalendaram.templates.ShiftNotesTemplate;
import mh.shiftcalendaram.templates.ShiftTemplate;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 * 
 */
@SuppressLint("NewApi") public class WidgetListProvider implements RemoteViewsFactory{
	private ArrayList<WidgetListItem> listItemList = new ArrayList<WidgetListItem>();
	private Context context = null;
	private int appWidgetId;

	public WidgetListProvider(Context context, Intent intent) {
		this.context = context;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

	 populateListItem();
	}

	private void populateListItem() {
		
		Calendar calendar = Calendar.getInstance();
		Database database = new Database(context);
		ArrayList<ShiftNotesTemplate> list = database.getTextNote();
		List<ShiftTemplate> customs = database.getAllShifts();
		
		
		listItemList.clear();
		
		CalendarAdapter adapter = null;
	    GregorianCalendar cal = (GregorianCalendar)GregorianCalendar.getInstance();
	   
	    for(int i = 0; i< list.size()-1;i++)
	    {
	    	for(int j = 0;j <list.size()-1;j++)
	    	{
	    		if(Integer.parseInt(list.get(j).getYear()) > Integer.parseInt(list.get(j+1).getYear()))
	    		{
	    			ShiftNotesTemplate pom = list.get(j);
	    			list.set(j, list.get(j+1));
	    			list.set(j+1, pom);
	    		}
	    		
	    		if((Integer.parseInt(list.get(j).getYear()) >= Integer.parseInt(list.get(j+1).getYear())) && (Integer.parseInt(list.get(j).getMonth()) > Integer.parseInt(list.get(j+1).getMonth())))
	    		{
	    			ShiftNotesTemplate pom = list.get(j);
	    			list.set(j, list.get(j+1));
	    			list.set(j+1, pom);
	    		}
	    		
	    		if((Integer.parseInt(list.get(j).getYear()) >= Integer.parseInt(list.get(j+1).getYear())) && (Integer.parseInt(list.get(j).getMonth()) >= Integer.parseInt(list.get(j+1).getMonth())) && (list.get(j).getPosition() > list.get(j+1).getPosition()))
	    		{
	    			ShiftNotesTemplate pom = list.get(j);
	    			list.set(j, list.get(j+1));
	    			list.set(j+1, pom);
	    		}
	    				
	    				
	    	}
	    }
	    
		for(int i = 0;i < list.size();i++)
		{
			
			cal.set(Calendar.YEAR, Integer.parseInt(list.get(i).getYear()));
			cal.set(Calendar.MONTH, Integer.parseInt(list.get(i).getMonth()));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adapter = new CalendarAdapter(context, cal);

			
			
			if((Integer.parseInt(list.get(i).getYear()) <= calendar.get(Calendar.YEAR)) && (Integer.parseInt(list.get(i).getMonth()) <= calendar.get(Calendar.MONTH)) && (Integer.parseInt(adapter.getSelectedDay(list.get(i).getPosition())) < calendar.get(Calendar.DAY_OF_MONTH)))
			{

				list.remove(i);
				i--;
			}
			else
			{
			
			
			WidgetListItem listItem = new WidgetListItem();
			//listItem.heading = CalendarAdapter.getSelectedDayStatic(list.get(0).getPosition(), cal, context);
			//listItem.heading = CalendarAdapter.getSelectedDayStatic(list.get(i).getPosition(), cal, context) + ". " + String.valueOf(Integer.parseInt(list.get(i).getMonth())+1) + ". " + list.get(i).getYear();
			listItem.heading = adapter.getSelectedDay(list.get(i).getPosition()) + ". " + String.valueOf(Integer.parseInt(list.get(i).getMonth())+1) + ". " + list.get(i).getYear();
			//listItem.heading = list.get(i).getPosition() + ". " + String.valueOf(Integer.parseInt(list.get(i).getMonth())+1) + ". " + list.get(i).getYear();
			listItem.content = list.get(i).getNotes();
			listItem.custom = customs.get(list.get(i).getCustom()).getTitle();
			listItemList.add(listItem);		
			}
		}

	}
	
	
	
/*	public void pppp(ArrayList<ShiftNotesTemplate> list, Calendar calendar)
	{
		for(int i = 0; i < list.size();i++)
		{
		if((Integer.parseInt(list.get(i).getYear()) <= calendar.get(Calendar.YEAR)) && (Integer.parseInt(list.get(i).getMonth()) <= calendar.get(Calendar.MONTH)) && (Integer.parseInt(adapter.getSelectedDay(list.get(i).position)) < calendar.get(Calendar.DAY_OF_MONTH)))
		{

			list.remove(i);
		}
		}
	}*/
	
	
	public void pp()
	{
		Calendar calendar = Calendar.getInstance();
		Database database = new Database(context);
		ArrayList<ShiftNotesTemplate> list = database.getTextNote();
		List<ShiftTemplate> customs = database.getAllShifts();
		
		
		listItemList.clear();
		
		CalendarAdapter adapter = null;
		
		for(int i = 0; i < list.size();i++)
		{
		WidgetListItem listItem = new WidgetListItem();
		
		listItem.heading = adapter.getSelectedDay(list.get(i).getPosition()) + ". " + String.valueOf(Integer.parseInt(list.get(i).getMonth())+1) + ". " + list.get(i).getYear();
		listItem.content = list.get(i).getNotes();
		listItem.custom = customs.get(list.get(i).getCustom()).getTitle();
		listItemList.add(listItem);	
		}
	}
	
	@Override
	public int getCount() {
		return listItemList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 *Similar to getView of Adapter where instead of View
	 *we return RemoteViews 
	 * 
	 */
	@Override
	public RemoteViews getViewAt(int position) {
		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.list_row);
		WidgetListItem listItem = listItemList.get(position);
		remoteView.setTextViewText(R.id.heading, listItem.heading);
		remoteView.setTextViewText(R.id.content, listItem.content);
		remoteView.setTextViewText(R.id.textView_customWidget, listItem.custom);

		return remoteView;
	}
	

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onDataSetChanged() {
		populateListItem();
	}

	@Override
	public void onDestroy() {
	}

}
