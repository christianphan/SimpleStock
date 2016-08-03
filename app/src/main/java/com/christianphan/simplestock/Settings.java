package com.christianphan.simplestock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by christian on 7/29/16.
 */
public class Settings  extends AppCompatActivity{

    SharedPreferences pref3;
    Switch aSwitch;
    LinearLayout linearLayout;
    int repeatingtime;
    TextView defaulttime;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //back button and settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        setContentView(R.layout.settings_layout);
        pref3 = this.getSharedPreferences("AlarmActivated", Context.MODE_PRIVATE);
        repeatingtime = pref3.getInt("Repeating", -1);

        aSwitch = (Switch) findViewById(R.id.switchnotificaiton);
        linearLayout = (LinearLayout) findViewById(R.id.repeatingtime);
        defaulttime = (TextView) findViewById(R.id.hourtime);


        if(repeatingtime > 0)
        {
            String messagetext = repeatingtime + 1 + " Hours";
            defaulttime.setText(messagetext);
        }
        else if (repeatingtime == 0)
        {
            String messagetext2 = repeatingtime + 1 + " Hour";
            defaulttime.setText(messagetext2);
        }



        if(!pref3.getString("alarm_ON_OR_OFF", "").contains("FALSE"))
        {
            aSwitch.toggle();
        }


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                SharedPreferences.Editor editor3 = pref3.edit();

                //true
                if(isChecked)
                {
                    if(pref3.getInt("Count", 0) > 0)
                    {
                        editor3.putString("alarm_ON_OR_OFF","TRUE").apply();
                        int hours = 1 + pref3.getInt("Repeating", 2);


                        //background alarm
                        Calendar calendar = Calendar.getInstance();
                        Intent alarm = new Intent(getApplicationContext(), AlarmReceiver.class);
                        alarm.setAction("NEW_ALERT");

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60000 * 60 * hours, pendingIntent);
                        Log.d("alarm created" , pref3.getString("alarm_ON_OR_OFF","NEUTRAL"));

                    }
                    else
                    {
                        editor3.putString("alarm_ON_OR_OFF","NEUTRAL").apply();
                        Log.d("alarm created" , pref3.getString("alarm_ON_OR_OFF","NEUTRAL"));
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "Notifications Turned On", Toast.LENGTH_SHORT);
                    toast.show();

                }

                //false
                if(!isChecked)
                {
                    editor3.putString("alarm_ON_OR_OFF","FALSE").apply();
                    Log.d("alarm status", pref3.getString("alarm_ON_OR_OFF","NEUTRAL"));
                    Calendar calendar = Calendar.getInstance();
                    Intent alarm = new Intent(getApplicationContext(), AlarmReceiver.class);
                    alarm.setAction("NEW_ALERT");

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);


                    Toast toast = Toast.makeText(getApplicationContext(), "Notifications Turned Off", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {



                final CharSequence[] items = {"1 Hour", "2 Hours", "3 Hours", "4 Hours", "5 Hours",
                "6 Hours", "7 Hours", "8 Hours", "9 Hours", "10 Hours", "11 Hours", "12 Hours",
                "13 Hours", "14 Hours", "15 Hours", "16 Hours", "17 Hours", "18 Hours", "19 Hours",
                "20 Hours", "21 Hours", "22 Hours", "23 Hours", "24 Hours"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle("Select the frequency of the alarm. (If enabled alarm will run Mon-Fri)");
                builder.setSingleChoiceItems(items, repeatingtime , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        repeatingtime = item;
                    }
                });

                builder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Log.d("repeatingtime", Integer.toString(repeatingtime));
                                if(repeatingtime > 0)
                                {
                                    String messagetext = repeatingtime + 1 + " Hours";
                                    defaulttime.setText(messagetext);
                                    pref3.edit().putInt("Repeating", repeatingtime).apply();
                                }
                                else if (repeatingtime == 0)
                                {
                                    String messagetext2 = repeatingtime + 1 + " Hour";
                                    defaulttime.setText(messagetext2);
                                    pref3.edit().putInt("Repeating", repeatingtime).apply();
                                }

                                if(!pref3.getString("alarm_ON_OR_OFF","NEUTRAL").contains("FALSE") && !pref3.getString("alarm_ON_OR_OFF","NEUTRAL").contains("NEUTRAL") )
                                {
                                    createAlarm();
                                }
                            }
                        });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
    });



    }

    @Override
    public void onBackPressed()
    {
        setResult(RESULT_CANCELED);
        finish();
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    public void createAlarm()
    {
        int hours = 1 + pref3.getInt("Repeating", 2);
        //background alarm
        Calendar calendar = Calendar.getInstance();
        Intent alarm = new Intent(getApplicationContext(), AlarmReceiver.class);
        alarm.setAction("NEW_ALERT");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60000 * 60 * hours, pendingIntent);
        Log.d("alarm created" , pref3.getString("alarm_ON_OR_OFF","NEUTRAL"));
    }


}
