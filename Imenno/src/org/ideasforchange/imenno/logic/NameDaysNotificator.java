package org.ideasforchange.imenno.logic;

import java.util.Calendar;

import org.ideasforchange.imenno.NameDays;
import org.ideasforchange.imenno.R;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NameDaysNotificator extends BroadcastReceiver {

    private static final int NAME_DAYS_NOTIFICATION_ID = 0;

	
	@Override
	public void onReceive(Context context, Intent intent) {		
		createNameDaysNotification(context);
	}
	
	/* creates an notification that launches the NameDays activity */
    private void createNameDaysNotification(Context context) {
    	/* prepare intent for launching the NameDays activity */
    	Intent intent = new Intent(context, NameDays.class);    	
    	PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
    	
    	/* Create a notification with the created intent */
    	Notification notification = new Notification.Builder(context)
    	.setSmallIcon(R.drawable.notification_icon)
    	.setTicker(context.getResources().getString(R.string.name_days_notification_ticker))
    	.setWhen(System.currentTimeMillis())
    	.setContentTitle(context.getResources().getString(R.string.notification_content_title))
    	.setContentText(context.getResources().getString(R.string.notification_content_text))
    	.setContentIntent(contentIntent)
    	.build();
    	
    	/* Show the notification */
    	NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    	nm.notify(NAME_DAYS_NOTIFICATION_ID, notification);    	
	}
    
    /**
     * Schedules a check for NameDays at the given hour, minute and second every day
     * @param context
     * @param hourOfDay
     * @param minute
     * @param second
     */
    public void scheduleCheck(Context context, int hourOfDay, int minute, int second) {    	        
        Intent intent = new Intent(context, NameDaysNotificator.class);
        /* set the action to the one that's accepted by the receiver in the manifest */
        intent.setAction("org.ideasforchange.namedays.check");
        intent.putExtra("onetime", Boolean.FALSE);
        /* set up a pending intent that will perform broadcast with the intent */
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /* schedule the intent to broadcast every day in the specified time */
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long timeInMilis = getTimeInMilis(hourOfDay, minute, second);        
        am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMilis, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

	private long getTimeInMilis(int hourOfDay, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay); // if u need run 2PM use 14 
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        long timeInMilis = calendar.getTimeInMillis();
		return timeInMilis;
	}    
	
}
