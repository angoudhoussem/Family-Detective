package com.family.detective.message;


import com.example.family__detective.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * This activity will be invoked by SmsManager via Pending intents defined in MainActivity 
 */
public class SmsStatus extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		/** Getting bundle object attached to the intent */
		Bundle data = getIntent().getExtras();
		
		/** Getting the data "status" from the bundle object */

		int status = data.getInt("status");
		
		/** Getting the data "number" from the bundle object */
		String number = data.getString("number");
				
		String notificationTitle = "";
		String notificationContent  = "";
		String tickerMessage = "";
		
		
		if(status==MainActivity.SENT){	/** Detecting which intent invoked this execution of activity */
			Toast.makeText(getBaseContext(), "Message successfully Sent", Toast.LENGTH_SHORT).show();
			notificationTitle="Message Successfully Sent";
			notificationContent="Message is successfully sent to " + number ;
			tickerMessage = "Message Successfully sent";
			
		}else if(status==MainActivity.DELIVERED){	/** Detecting which intent invoked this execution of activity */
			Toast.makeText(getBaseContext(), "Message successfully Delivered", Toast.LENGTH_SHORT).show();
			notificationTitle="Message Successfully Delivered";
			notificationContent="Message is successfully delivered to " + number ;
			tickerMessage = "Message Successfully delivered";
		}
		
		/** Creating a notification intent which creates the activity NotificationView when the user clicks 
		 *  a notification from the notification list
		 */
		Intent notificationIntent = new Intent(getApplicationContext(),NotificationView.class);
		notificationIntent.putExtra("number", notificationContent );
		
		/** This is needed to make this intent different from its previous intents */
		notificationIntent.setData(Uri.parse("tel:/"+ (int)System.currentTimeMillis()));
		
		/** Creating different tasks for each notification. See the flag Intent.FLAG_ACTIVITY_NEW_TASK */
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        
		/** Getting the System service NotificationManager */
        NotificationManager nManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		
        /** Configuring notification builder to create a notification */
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
							.setWhen(System.currentTimeMillis())
							.setContentText(notificationContent)
							.setContentTitle(notificationTitle)
							.setSmallIcon(R.drawable.ic_launcher)
							.setAutoCancel(true)
							.setTicker(tickerMessage)
							.setContentIntent(pendingIntent);
		
		/** Creating a notification from the notification builder */
		Notification notification = notificationBuilder.build();
		
		/** Sending the notification to system. 
		 * The first argument ensures that each notification is having a unique id 
		 * If two notifications share same notification id, then the last notification replaces the first notification 
		 * */
		nManager.notify((int)System.currentTimeMillis(), notification);
		
		/** Finishes the execution of this activity */
		finish();
	}

}
