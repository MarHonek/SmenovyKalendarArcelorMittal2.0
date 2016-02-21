package mh.shiftcalendaram.widgets;

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import mh.shiftcalendaram.Database;
import mh.shiftcalendaram.MainActivity;
import mh.shiftcalendaram.R;
import mh.shiftcalendaram.oldCalendar.CalendarAdapter;
import mh.shiftcalendaram.templates.ShiftTemplate;

public class WeekWidgetProvider extends AppWidgetProvider {

	int customNum = 0;
	Database data;
	List<ShiftTemplate> customList;
	
	
	public static final String DATA_FETCHED = "mh.shiftcalendaram.DATA_FETCHED";
	/* 
	 * this method is called every 30 mins as specified on widgetinfo.xml
	 * this method is also called on every phone reboot
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		data = new Database(context);
		customList = data.getAllShifts();
		
		
		Log.v("asdfg", "OK");
		final int N = appWidgetIds.length;
		/*int[] appWidgetIds holds ids of multiple instance of your widget
		 * meaning you are placing more than one widgets on your homescreen*/
		for (int i = 0; i < N; ++i) {
			Intent intent = new Intent(context, MainActivity.class);
	        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
	      
			
			RemoteViews remoteViews = updateWidgetGridView(context,
					appWidgetIds[i]);
			  remoteViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
			 
			  
			  
			appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i], R.id.gridView_week_widget);	
		}
	  
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	
	
	

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}





	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		if (intent.getAction().equals(DATA_FETCHED)) {
			int appWidgetId = intent.getIntExtra(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			RemoteViews remoteViews = updateWidgetGridView(context, appWidgetId);
			appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
		}
	}



	@SuppressLint("NewApi") private RemoteViews updateWidgetGridView(Context context, int appWidgetId) {

		

		//which layout to show on widget
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.week_widget_layout);
		
		//RemoteViews Service needed to provide adapter for ListView
		Intent svcIntent = new Intent(context, WeekWidgetService.class);
		//passing app widget id to that RemoteViews Service
		svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		//setting a unique Uri to the intent
		//don't know its purpose to me right now
		svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
		//setting adapter to listview of the widget
		remoteViews.setRemoteAdapter(appWidgetId, R.id.gridView_week_widget,
				svcIntent);
		//setting an empty view in case of no data
		remoteViews.setEmptyView(R.id.gridView_week_widget, R.id.empty_view);
		
		
		Calendar cal = Calendar.getInstance();
		
		int month = cal.get(Calendar.MONTH)+1;
		String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + ". " + String.valueOf(month) + ". " + String.valueOf(cal.get(Calendar.YEAR));
		
		remoteViews.setTextViewText(R.id.textView_noteWidget_date, date);
		remoteViews.setTextViewText(R.id.textView_noteWidget_dayOfWeek, CalendarAdapter.getDayOfWeekString(cal.get(Calendar.DAY_OF_WEEK)));
		
		remoteViews.setTextViewText(R.id.textView_week_widget_custom_name, customList.get(customNum).getTitle());
		//setting an empty view in case of no data
		return remoteViews;
	}
	

}
