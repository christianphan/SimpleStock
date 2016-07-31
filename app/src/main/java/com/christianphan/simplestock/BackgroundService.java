package com.christianphan.simplestock;

/**
 * Created by christian on 7/20/16.
 */
import android.app.*;
import android.content.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Map;

import yahoofinance.YahooFinance;


public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private SharedPreferences prefs;
    private String prefcode;
    private SharedPreferences prefs2;
    private  SharedPreferences pref4;

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
            prefs = context.getSharedPreferences("com.christianphan.simplestock", Context.MODE_PRIVATE);
            prefs2 = context.getSharedPreferences("indexes", Context.MODE_PRIVATE);
            pref4 = context.getSharedPreferences("Low_High", Context.MODE_PRIVATE);
            new YahooAPI().execute();

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


    public class YahooAPI extends AsyncTask<Void, Void, Void> {

        String result;
        private ArrayList<String> price = new ArrayList<String>();;
        int count = 0;
        private ArrayList<String> index = new ArrayList<String>();;
        private ArrayList<String> prefcode = new ArrayList<String>();


        @Override
        protected Void doInBackground(Void... args) {

            try
            {
                Map<String, ?> allEntries = prefs2.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                    index.add(entry.getValue().toString());
                    prefcode.add(entry.getKey());
                    yahoofinance.Stock stock = YahooFinance.get(index.get(count));
                    price.add(stock.getQuote().getPrice().toString());
                    count++;
                }




            }
            catch (MalformedURLException ex) {
                result = "error";
                return null;
            } catch (IOException ex) {
                result = "error";
                return null;
            } catch (Exception e) {
                result = "error";
                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {


            for(int i = 0; i < index.size(); i++ ) {


                if(pref4.getString("LOW" + prefcode.get(i),"0") != "0" && Double.parseDouble(pref4.getString("LOW" + prefcode.get(i),"0")) > Double.parseDouble(price.get(i))) {

                    Bitmap largeicon = BitmapFactory.decodeResource( getResources(), R.mipmap.ic_launcher);
                    PendingIntent notificationintent = PendingIntent.getActivity(context, Integer.parseInt(prefcode.get(i)), new Intent(context, MainActivity.class), 0);

                    NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context).setContentTitle(index.get(i)).setTicker(index + " Closed Value").setContentText("Value:" + price.get(i)).setSmallIcon(R.drawable.ic_stat_notification).setLargeIcon(largeicon);

                    notificationbuilder.setContentIntent(notificationintent);
                    notificationbuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
                    notificationbuilder.setAutoCancel(true);

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(Integer.parseInt(prefcode.get(i)), notificationbuilder.build());
                    Log.d("alarm", index + " Notification Made PrefCode " + prefcode);

                }

                String prefcodethis = prefcode.get(i);
                String test = pref4.getString("HIGH" + prefcodethis, "0");
                Log.d("high server", test);
                Log.d("pref key access", "HIGH"  + prefcode.get(i));

                if(pref4.getString("HIGH" + prefcode.get(i), "0") != "0" && Double.parseDouble(pref4.getString("HIGH" + prefcode.get(i),"0")) < Double.parseDouble(price.get(i)))
                {
                    //creates bitmap of icon
                    Bitmap largeicon = BitmapFactory.decodeResource( getResources(), R.mipmap.ic_launcher);
                    PendingIntent notificationintent = PendingIntent.getActivity(context, Integer.parseInt(prefcode.get(i)), new Intent(context, MainActivity.class), 0);

                    NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context).setContentTitle(index.get(i)).setTicker(index + " Closed Value").setContentText("Value:" + price.get(i)).setSmallIcon(R.drawable.ic_stat_notification).setLargeIcon(largeicon);

                    notificationbuilder.setContentIntent(notificationintent);
                    notificationbuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
                    notificationbuilder.setAutoCancel(true);

                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(Integer.parseInt(prefcode.get(i)), notificationbuilder.build());
                    Log.d("alarm", index + " Notification Made PrefCode " + prefcode);

                }


            }

                stopSelf();




        }




    }



}