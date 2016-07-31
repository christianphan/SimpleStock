package com.christianphan.simplestock;

/**
 * Created by christian on 7/22/16.
 */
import android.app.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;


public class BackgroundService2 extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private SharedPreferences prefs;
    private SharedPreferences prefs2;
    private SharedPreferences prefs3;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {


            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }



}