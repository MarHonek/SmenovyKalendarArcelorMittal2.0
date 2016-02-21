package mh.shiftcalendaram.widgets;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WidgetPhoneBootReciever extends BroadcastReceiver {

	   public static boolean wasPhoneBootSucessful = false;
	   
	
	@Override
	public void onReceive(Context context, Intent intent) {
		 if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
	            wasPhoneBootSucessful = true;
		 }
	}

}
