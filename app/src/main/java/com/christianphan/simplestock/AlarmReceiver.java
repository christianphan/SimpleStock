package com.christianphan.simplestock;

/**
 * Created by christian on 7/20/16.
 */
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class AlarmReceiver extends BroadcastReceiver {

    SharedPreferences prefs3;
    private Context mcontext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mcontext = context;


        if (intent != null) {
            String action = intent.getAction();
            action.length();

            switch (action) {
                case Intent.ACTION_BOOT_COMPLETED:

                    prefs3 = context.getSharedPreferences("AlarmActivated", Context.MODE_PRIVATE);
                    String alarm = prefs3.getString("alarm_ON_OR_OFF", "NEUTRAL");
                    Log.d("boot", alarm);
                    if(alarm.contains("TRUE")) {

                                int hours = 1 + prefs3.getInt("Repeating", 2);
                                Calendar calendar = Calendar.getInstance();
                                Intent alarmintent = new Intent(context, AlarmReceiver.class);
                                alarmintent.setAction("NEW_ALERT");

                                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmintent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager alarmManager = (AlarmManager) mcontext.getSystemService(context.ALARM_SERVICE);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000 * 60 * hours, pendingIntent);
                                Log.d("alarm", " ADDED");
                    }

                    break;
                case "NEW_ALERT":

                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    Log.d("NEW_ALERT", "ATTEMPTED");
                    if(hour >= 9 && hour <= 16) {
                        Intent background = new Intent(context, BackgroundService.class);
                        context.startService(background);

                    }

                    break;
                default:
                    break;
            }
        }

    }

}