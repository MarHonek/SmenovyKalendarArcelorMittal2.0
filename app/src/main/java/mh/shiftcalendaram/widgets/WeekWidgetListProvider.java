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
import android.graphics.Color;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import mh.shiftcalendaram.Database;
import mh.shiftcalendaram.R;
import mh.shiftcalendaram.oldCalendar.CalendarAdapter;
import mh.shiftcalendaram.oldCalendar.ShiftCalculating;
import mh.shiftcalendaram.templates.AlternativeShifts;
import mh.shiftcalendaram.templates.ShiftNotesTemplate;
import mh.shiftcalendaram.templates.ShiftTemplate;
import mh.shiftcalendaram.templates.StaticShiftTemplate;
import mh.shiftcalendaram.widgets.WidgetListItem;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 * 
 */
@SuppressLint("NewApi") public class WeekWidgetListProvider implements RemoteViewsFactory{
	private ArrayList<WidgetListItem> listItemList = new ArrayList<WidgetListItem>();
	private Context context = null;
	private int appWidgetId;

	public WeekWidgetListProvider(Context context, Intent intent) {
		this.context = context;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

	 populateListItem();
	}

	private void populateListItem() {
		
		Database data = new Database(context);
		
		List<ShiftTemplate> custom = data.getAllShifts();
		ArrayList<AlternativeShifts> alter = data.getSpecial();
		ArrayList<ShiftNotesTemplate> notesList = data.getTextNote();
		ArrayList<StaticShiftTemplate> orig = StaticShiftTemplate.createList();
		
		
		
		listItemList.clear();
	
		Calendar cal = Calendar.getInstance();
		GregorianCalendar greg = (GregorianCalendar) cal.clone();
	    CalendarAdapter adapter;
		
		
	    
	    ShiftCalculating calculate = new ShiftCalculating();
	    
	    if(custom.size() > 0)
	    {
	    
		for(int i = 0;i < 7;i++)
		{

			
			
			
			String background = "#EEEEEE";
			boolean notes = false;
			adapter = new CalendarAdapter(context, greg);
			
			
			
			int first = adapter.getFirstDay();
			
			
			String[] kal = calculate.monthShifts(first, orig.get(custom.get(0).getPosition()).getABCDShifts(custom.get(0).getShortTitle()), cal);
			
			
			first -= 1;
			if(first < 0)
				first = 0;
			
			String customPom = kal[cal.get(Calendar.DAY_OF_MONTH)-1+first];
			
			for(int j = 0; j < alter.size();j++)
			{
				if((Integer.parseInt(adapter.getSelectedDay(alter.get(j).getPosition())) == cal.get(Calendar.DAY_OF_MONTH)) && (Integer.parseInt(alter.get(j).getMonth()) == cal.get(Calendar.MONTH)) && (Integer.parseInt(alter.get(j).getYear()) == cal.get(Calendar.YEAR)) && (alter.get(j).getCustom() == 0))
				{
					customPom = alter.get(j).getKind();
					background = alter.get(j).getColor();
					
				}
			
			}
			
			for(int j = 0;j < notesList.size();j++)
			{
				int dayFromPosition = Integer.parseInt(adapter.getSelectedDay(notesList.get(j).getPosition()));
				if((dayFromPosition == cal.get(Calendar.DAY_OF_MONTH)) && (Integer.parseInt(notesList.get(j).getMonth()) == cal.get(Calendar.MONTH)) && (Integer.parseInt(notesList.get(j).getYear()) == cal.get(Calendar.YEAR)) && (notesList.get(j).getCustom() == 0))
				{
					notes = true;
					
				}
			}
			

			
			WidgetListItem listItem = new WidgetListItem();
			//listItem.heading = adapter.getSelectedDay(list.get(i).getPosition()) + ". " + String.valueOf(Integer.parseInt(list.get(i).getMonth())+1) + ". " + list.get(i).getYear();
			listItem.heading = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
			if(customPom != "")
			listItem.content = customPom;
			else listItem.content = "";
			
			listItem.notes = notes;
			listItem.background = background;
			
			listItemList.add(listItem);
			
			
			
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+1);
			if(cal.get(Calendar.DAY_OF_MONTH) > cal.getActualMaximum(Calendar.DAY_OF_MONTH))
			{
				cal.set(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
				
				if(cal.get(Calendar.MONTH) > 11)
				{
					cal.set(Calendar.MONTH, 0);
					cal.set(Calendar.YEAR,cal.get(Calendar.YEAR)+1);
				}
			}
			
			
			
			greg = (GregorianCalendar)cal.clone();
			}
		}
		}
	
	public void refreshEmptyWidget()
	{
		
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
				context.getPackageName(), R.layout.week_widget_item);
		
		
		
		WidgetListItem listItem = listItemList.get(position);
		remoteView.setTextViewText(R.id.date_widget, listItem.heading);
		remoteView.setTextViewText(R.id.textView_shift_widget, listItem.content);
		
		if(listItem.notes)
		remoteView.setViewVisibility(R.id.imageView_note_indicates_widget, View.VISIBLE);
		else
		{
			remoteView.setViewVisibility(R.id.imageView_note_indicates_widget, View.INVISIBLE);
		}
		
		
		String color = listItem.background;
		if(!color.equals("#EEEEEE"))
		{
			remoteView.setTextColor(R.id.date_widget, Color.parseColor("#FFFFFF"));
			remoteView.setTextColor(R.id.textView_shift_widget, Color.parseColor("#FFFFFF"));
			
		}
		else
		{
			remoteView.setTextColor(R.id.date_widget, Color.parseColor("#000000"));
			remoteView.setTextColor(R.id.textView_shift_widget, Color.parseColor("#000000"));
		}
		
		remoteView.setInt(R.id.linearLayout_week_widget, "setBackgroundColor", Color.parseColor(color));
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
