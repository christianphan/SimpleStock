package com.christianphan.simplestock;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by christian on 6/26/16.
 */
public class AdditonalInfo extends AppCompatActivity {



    private String values[] = new String[4];
    private String dates[] = new String[4];
    private int stockposition = 0;
    private String index = "";
    private String value = "";
    private String color = "";
    private String name = "";
    private String percent = "";
    private String volume = "";
    private String annual = "";
    private String open = "";
    private String high = "";
    private String low = "";
    private String change = "";
    private int test = 0;
    private String date1 = "";
    private String date2 = "";
    private String date3 = "";
    private String date4 = "";
    private String price1 = "";
    private String price2 = "";
    private String price3 = "";
    private String price4 = "";
    private String price5 = "";
    private String time = "";
    private String amountofShares;
    private String valueofShares;
    TabAdapter adapterPageview;
    ReadRss readRss = null;
    NewActivity newActivity = null;
    AnnualInfo loadAnnual = null;
    int hasloadedpage2 = 0;
    int hasloadedpage3 = 0;
    final DecimalFormat f = new DecimalFormat("#0.00");

    public ArrayList<News> getArrayList() {
        return arrayList;
    }

    private ArrayList<News> arrayList;

    SharedPreferences pref;
    SharedPreferences pref2;
    SharedPreferences pref3;
    SharedPreferences pref4;
    private int idcode;
    private boolean value_entered = false;




    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        pref = this.getSharedPreferences("com.christianphan.simplestock", Context.MODE_PRIVATE);
        pref2 = this.getSharedPreferences("indexes", Context.MODE_PRIVATE);
        pref3 = this.getSharedPreferences("AlarmActivated", Context.MODE_PRIVATE);
        pref4 = this.getSharedPreferences("Low_High", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_additional);


        Bundle bundle = getIntent().getExtras();
        color = bundle.getString("Color");
        value = bundle.getString("Value");
        index = bundle.getString("Index");
        name = bundle.getString("Name");
        percent = bundle.getString("Percent");
        volume = bundle.getString("Volume");
        annual = bundle.getString("Annual");
        open = bundle.getString("Open");
        high = bundle.getString("High");
        low = bundle.getString("Low");
        change = bundle.getString("Change");
        test = bundle.getInt("Test");
        date1 = bundle.getString("Date1");
        date2 = bundle.getString("Date2");
        date3 = bundle.getString("Date3");
        date4 = bundle.getString("Date4");
        price1 = bundle.getString("Price1");
        price2 = bundle.getString("Price2");
        price3 = bundle.getString("Price3");
        price4 = bundle.getString("Price4");
        price5 = bundle.getString("Price5");
        time = bundle.getString("Time");
        amountofShares = bundle.getString("Amount");
        valueofShares = bundle.getString("ValueOfShares");
        stockposition = bundle.getInt("position");
        idcode = bundle.getInt("ID");



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( name);
        getSupportActionBar().setElevation(0);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        adapterPageview = new TabAdapter(getSupportFragmentManager(),
                AdditonalInfo.this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapterPageview);

        //allows 4 fragments to be in memeory
        viewPager.setOffscreenPageLimit(4);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);



        newActivity = new NewActivity();
        newActivity.execute();



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 1 && readRss == null && hasloadedpage2 == 0)
                {
                    hasloadedpage2++;
                    readRss = new ReadRss();
                    readRss.execute();

                }

                if(position == 2 && loadAnnual == null && hasloadedpage3 == 0)
                {
                    hasloadedpage3++;
                    loadAnnual = new AnnualInfo();
                    loadAnnual.execute();
                }



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });






}


    @Override
    public void onBackPressed()
    {
        if (readRss != null) {
            readRss.cancel(true);

        }

        if (newActivity != null) {
            newActivity.cancel(true);

        }

        if(loadAnnual != null)
        {
            loadAnnual.cancel(true);
        }


        View fragment4 = adapterPageview.getRegisteredFragment(3).getView();
        TextView amountFromText = (TextView) fragment4.findViewById(R.id.amountofShares);
        TextView valueFromText = (TextView) fragment4.findViewById(R.id.pricesofshares);

        String amountsent = amountFromText.getText().toString();
        String valuesent = valueFromText.getText().toString();
        valuesent = valuesent.replace("$", "");



        Intent output = new Intent();
        output.putExtra("amount", amountsent);
        output.putExtra("value", valuesent);



        setResult(RESULT_CANCELED,output);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stock_settings:

                if (readRss != null) {
                    readRss.cancel(true);

                }

                if (newActivity != null) {
                    newActivity.cancel(true);
                }

                if(loadAnnual != null)
                {
                    loadAnnual.cancel(true);
                }
                setResult(RESULT_OK);
                finish();
                return true;
            case android.R.id.home:

                if (readRss != null) {
                    readRss.cancel(true);

                }

                if (newActivity != null) {
                    newActivity.cancel(true);
                }

                if(loadAnnual != null)
                {
                    loadAnnual.cancel(true);
                }

                View fragment4 = adapterPageview.getRegisteredFragment(3).getView();
                TextView amountFromText = (TextView) fragment4.findViewById(R.id.amountofShares);
                TextView valueFromText = (TextView) fragment4.findViewById(R.id.pricesofshares);

                String amountsent = amountFromText.getText().toString();
                String valuesent = valueFromText.getText().toString();
                valuesent = valuesent.replace("$", "");


                Intent output = new Intent();
                output.putExtra("amount", amountsent);
                output.putExtra("value", valuesent);

                setResult(RESULT_CANCELED,output);
                finish();
                return true;
            case R.id.setNotification:


                //gets pref code
                final String prefcode = Integer.toString(idcode);

                //gets low and high value. if they aren't there returns 0
                String low_alarm_watch_value = pref4.getString("LOW" + prefcode, "0");
                String high_alarm_watch_value = pref4.getString("HIGH" + prefcode, "0");

                Log.d("Pref4 high key", "HIGH" + prefcode);
                Log.d("LOw", low_alarm_watch_value);
                Log.d("HIGH", high_alarm_watch_value);

                //creates dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(AdditonalInfo.this);
                final AlertDialog OptionDialog = builder.create();
                OptionDialog.setTitle("Reset Shares");
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.notification_option, null);
                OptionDialog.setView(dialoglayout);
                OptionDialog.show();


                //buttons and edittext
                final Button add = (Button) dialoglayout.findViewById(R.id.addpref);
                final Button remove = (Button)  dialoglayout.findViewById(R.id.remove_notification);
                Button cancel = (Button) dialoglayout.findViewById(R.id.cancel_addingpref);
                final EditText low_alert = (EditText) dialoglayout.findViewById(R.id.editpricebelow);
                final EditText high_alert = (EditText) dialoglayout.findViewById(R.id.editpriceabove);


                //edittext options
                low_alert.setKeyListener(DigitsKeyListener.getInstance(true,true));
                high_alert.setKeyListener(DigitsKeyListener.getInstance(true,true));
                low_alert.setHint(low_alarm_watch_value);
                high_alert.setHint(high_alarm_watch_value);
                low_alert.setGravity(Gravity.RIGHT);
                high_alert.setGravity(Gravity.RIGHT);

                //button Options
                add.setEnabled(false);



                if(low_alarm_watch_value == "0" && high_alarm_watch_value == "0")
                {
                    remove.setEnabled(false);
                }



                add.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        //checks if first textedit is filled
                        SharedPreferences.Editor editor4 = pref4.edit();
                        if(low_alert.getText().toString().matches("") || low_alert.getText().toString().matches("."))
                        {
                            editor4.putString("LOW" + prefcode,"0").apply();
                        }
                        else
                        {
                            editor4.putString("LOW" + prefcode, low_alert.getText().toString()).apply();
                        }



                        //checks if second text edit is filled
                        if(high_alert.getText().toString().matches("") || high_alert.getText().toString().matches("."))
                        {
                            editor4.putString("HIGH" + prefcode,"0").apply();
                        }
                        else
                        {
                            String test = high_alert.getText().toString();
                            editor4.putString("HIGH" + prefcode, high_alert.getText().toString()).apply();
                        }



                        int notificationlist = pref3.getInt("Count", 0);
                        notificationlist++;
                        SharedPreferences.Editor editor = pref.edit();
                        SharedPreferences.Editor editor2 = pref2.edit();
                        SharedPreferences.Editor editor3 = pref3.edit();
                        editor.putInt(prefcode,idcode + 1).apply();
                        editor2.putString(prefcode,index).apply();
                        editor3.putInt("Count", notificationlist).apply();
                        Log.d("notification" , "added");

                        String alarmboolean = pref3.getString("alarm_ON_OR_OFF","NEUTRAL");
                        Log.d("alarmboolean" , alarmboolean);
                        if(alarmboolean.contains("NEUTRAL"))
                        {
                            editor3.putString("alarm_ON_OR_OFF","TRUE").apply();
                            int hours = 1 + pref3.getInt("Repeating", 2);

                            //background alarm
                            Calendar calendar = Calendar.getInstance();
                            Intent alarm = new Intent(getApplicationContext(), AlarmReceiver.class);
                            alarm.setAction("NEW_ALERT");

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000 * 60 * hours, pendingIntent);
                            Log.d("alarm created" , "TRUE");
                        }
                        remove.setEnabled(true);
                        OptionDialog.cancel();

                    }

                });

                remove.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        SharedPreferences.Editor editor = pref.edit();
                        SharedPreferences.Editor editor2 = pref2.edit();
                        SharedPreferences.Editor editor3 = pref3.edit();
                        SharedPreferences.Editor editor4 = pref4.edit();
                        editor4.remove("LOW" + prefcode).apply();
                        editor4.remove("HIGH" + prefcode).apply();
                        high_alert.setHint("0");
                        low_alert.setHint("0");
                        editor.remove(prefcode).apply();
                        editor2.remove(prefcode).apply();
                        int notificationlist = pref3.getInt("Count", 0);
                        Log.d("NotificationList1", Integer.toString(notificationlist));
                        notificationlist = notificationlist - 1;
                        editor3.putInt("Count", notificationlist).apply();
                        remove.setEnabled(false);
                        Log.d("Notification", "Removed");
                        Log.d("NotificationList2", Integer.toString(notificationlist));

                        if(pref3.getInt("Count", 0) == 0)
                        {

                            if(!pref3.getString("alarm_ON_OR_OFF", "NEUTRAL").contains("FALSE")) {

                                editor3.putString("alarm_ON_OR_OFF", "NEUTRAL").apply();
                                Log.d("ALARM STATUS", pref3.getString("alarm_ON_OR_OFF", "NEUTRAL"));
                                Intent alarm = new Intent(getApplicationContext(), AlarmReceiver.class);
                                alarm.setAction("NEW_ALERT");

                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                alarmManager.cancel(pendingIntent);
                                Log.d("ALARM ON/OFF", "OFF");
                            }
                        }

                    }


                });


                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        OptionDialog.cancel();

                    }


                });



                //edittext options
                low_alert.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {

                        if(s.toString().trim().length()==0){
                            value_entered = false;
                        } else {

                            if(s.toString().matches(".*[123456789].*"))
                            {
                                value_entered = true;
                                add.setEnabled(true);
                            }
                            else
                            {
                                value_entered = false;
                                add.setEnabled(false);
                            }

                        }


                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                });

                high_alert.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {

                        if(s.toString().trim().length()==0){
                            value_entered = false;
                        } else {

                            if(s.toString().matches(".*[123456789].*"))
                            {
                                value_entered = true;
                                add.setEnabled(true);
                            }
                            else
                            {
                                value_entered = false;
                                add.setEnabled(false);
                            }

                        }


                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                });






                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stock_menu, menu);


        return true;
    }

    public String getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getPercent() {
        return percent;
    }


    public String getVolume() {
        return volume;
    }

    public String getAnnual() {
        return annual;
    }

    public String getOpen() {
        return open;
    }


    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getChange() {
        return change;
    }

    public int getTest() {
        return test;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public String getDate3() {
        return date3;
    }

    public String getDate4() {
        return date4;
    }

    public String getPrice1() {
        return price1;
    }

    public String getPrice2() {
        return price2;
    }

    public String getPrice3() {
        return price3;
    }

    public String getPrice4() {
        return price4;
    }

    public String getPrice5() {
        return price5;
    }

    public String getTime() {
        return time;
    }

    public Context getContext()
    {
        return getApplicationContext();
    }

    public String getAmountofShares() {
        return amountofShares;
    }

    public String getValueofShares() {
        return valueofShares;
    }

    public class AnnualInfo extends AsyncTask<Void,Void, Void>
    {
        String result = "";
        String annualhighestdata = "";
        String annuallowestdata = "";
        String highestpercentchangedata = "";
        String lowestpercentchangedata = "";
        ArrayList<String> xaxis = new ArrayList<>();
        ArrayList<Entry> yaxis = new ArrayList<>();



        @Override
        protected Void doInBackground(Void... args) {

            try
            {
                yahoofinance.Stock stock = YahooFinance.get(index);
                annualhighestdata = stock.getQuote().getYearHigh().toString();
                annuallowestdata = stock.getQuote().getYearLow().toString();
                highestpercentchangedata = stock.getQuote().getChangeFromYearHighInPercent().toString();
                lowestpercentchangedata = stock.getQuote().getChangeFromYearLowInPercent().toString();

                List<HistoricalQuote> history = stock.getHistory(Interval.MONTHLY);

                for(int i = 0; i < 12 ; i++)
                {
                    xaxis.add(i,"");
                    yaxis.add(new Entry(Float.parseFloat(history.get(11 - i).getAdjClose().toString()), i));

                }



            }

            catch (MalformedURLException ex) {
                result = "Connection Error";
                return null;
            } catch (IOException ex) {
                result = "Connection Error";
                ;
                return null;
            } catch (Exception e) {
                result = "Connection Error";
                return null;
            }





            return null;
        }


        @Override
        protected void onPostExecute(Void avoid) {

            if (result != "Connection Error") {

                View fragment3 = adapterPageview.getRegisteredFragment(2).getView();

                CircularProgressView progressView = (CircularProgressView) fragment3.findViewById(R.id.progress_view3);
                TextView annualtitleeshown = (TextView) fragment3.findViewById(R.id.Annual_Title);
                TextView annualhighestshown = (TextView) fragment3.findViewById(R.id.annualhighest);
                TextView highesttitleshown = (TextView) fragment3.findViewById(R.id.highestTitle);
                TextView lowestshown = (TextView) fragment3.findViewById(R.id.annuallowest);
                TextView lowesttitleshown = (TextView) fragment3.findViewById(R.id.lowestTitle);
                TextView lowtitleshown = (TextView) fragment3.findViewById(R.id.lowTitle);
                TextView annualhighchangeshown = (TextView) fragment3.findViewById(R.id.annualhighchange);
                TextView highpercenttitle = (TextView) fragment3.findViewById(R.id.highPercentTitle);
                TextView annuallowchangeshown = (TextView) fragment3.findViewById(R.id.annuallowChange);
                TextView lowchangetitleshown = (TextView) fragment3.findViewById(R.id.lowTitle);
                LineChart chart = (LineChart) fragment3.findViewById(R.id.chartannual);


                annualhighchangeshown.setText(highestpercentchangedata + "%");
                if (Double.parseDouble(highestpercentchangedata) > 0) {
                    annualhighchangeshown.setText("+" + highestpercentchangedata + "%");
                    annualhighchangeshown.setTextColor(Color.parseColor("#73C82C"));

                } else if (Double.parseDouble(highestpercentchangedata) < 0) {
                    annualhighchangeshown.setText(highestpercentchangedata + "%");
                    annualhighchangeshown.setTextColor(Color.parseColor("#B00B1E"));


                }

                double annualhighestdataformat = Double.parseDouble(annualhighestdata);
                double annuallowestdataformat = Double.parseDouble(annuallowestdata);
                annualhighestshown.setText("$" + f.format(annualhighestdataformat));
                lowestshown.setText("$" + f.format(annuallowestdataformat));


                annuallowchangeshown.setText(lowestpercentchangedata + "%");
                if (Double.parseDouble(lowestpercentchangedata) > 0) {
                    annuallowchangeshown.setText("+" + lowestpercentchangedata + "%");
                    annuallowchangeshown.setTextColor(Color.parseColor("#73C82C"));

                } else if (Double.parseDouble(lowestpercentchangedata) < 0) {
                    annuallowchangeshown.setText(lowestpercentchangedata + "%");
                    annuallowchangeshown.setTextColor(Color.parseColor("#B00B1E"));


                }

                String[] Xaxis = new String[xaxis.size()];
                for (int i = 0; i < Xaxis.length; i++) {
                    Xaxis[i] = xaxis.get(i);
                }

                ArrayList<ILineDataSet> lineDataSet = new ArrayList<>();
                LineDataSet lineDataSet1 = new LineDataSet(yaxis, index + " value");
                lineDataSet1.setDrawCircles(true);
                lineDataSet1.setCircleColorHole(Color.BLUE);
                lineDataSet1.setColor(Color.BLUE);
                lineDataSet1.setDrawFilled(true);
                lineDataSet1.setCircleColor(Color.BLUE);
                lineDataSet1.setDrawCubic(true);

                lineDataSet.add(lineDataSet1);

                YAxis leftAxis = chart.getAxisLeft();
                YAxis rightAxis = chart.getAxisRight();

                float lowestprice = 0;
                float highestprice = 0;
                int degree = 0;

                for (int i = 0; i < yaxis.size(); i++) {
                    if (lowestprice > yaxis.get(i).getVal()) {
                        lowestprice = yaxis.get(i).getVal();

                    } else if (lowestprice == 0) {
                        lowestprice = yaxis.get(i).getVal();
                    }

                    if (highestprice < yaxis.get(i).getVal()) {
                        highestprice = yaxis.get(i).getVal();
                    }


                }


                degree = Math.round(highestprice - lowestprice);


                chart.getAxisRight().setStartAtZero(false);
                chart.getAxisLeft().setStartAtZero(false);
                leftAxis.setAxisMinValue(lowestprice - degree);
                rightAxis.setAxisMinValue(lowestprice - degree);
                chart.setPinchZoom(false);


                chart.setDescription("Close Value Over Last 12 Months");

                chart.setData(new LineData(Xaxis, lineDataSet));
                chart.notifyDataSetChanged();
                chart.invalidate();


                annualtitleeshown.setVisibility(fragment3.VISIBLE);
                annualhighchangeshown.setVisibility(fragment3.VISIBLE);
                annualhighestshown.setVisibility(fragment3.VISIBLE);
                highesttitleshown.setVisibility(fragment3.VISIBLE);
                lowestshown.setVisibility(fragment3.VISIBLE);
                lowesttitleshown.setVisibility(fragment3.VISIBLE);
                lowtitleshown.setVisibility(fragment3.VISIBLE);
                highpercenttitle.setVisibility(fragment3.VISIBLE);
                annuallowchangeshown.setVisibility(fragment3.VISIBLE);
                lowchangetitleshown.setVisibility(fragment3.VISIBLE);
                chart.setVisibility(fragment3.VISIBLE);

                progressView.setVisibility(fragment3.GONE);

            }
            else
            {
                View fragment3 = adapterPageview.getRegisteredFragment(2).getView();
                TextView annualtitleeshown = (TextView) fragment3.findViewById(R.id.Annual_Title);
                CircularProgressView progressView = (CircularProgressView) fragment3.findViewById(R.id.progress_view3);

                annualtitleeshown.setText("Connection Error");
                progressView.setVisibility(fragment3.GONE);
                annualtitleeshown.setVisibility(fragment3.VISIBLE);

            }
        }






    }




    public class NewActivity extends AsyncTask<View, View, View> {


        private String result;
        ArrayList<String> xaxis = new ArrayList<>();
        ArrayList<Entry> yaxis = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected View doInBackground(View... args) {

            try {

                yahoofinance.Stock stock = YahooFinance.get(index);

                value = stock.getQuote().getPrice().toString();
                percent = stock.getQuote().getChangeInPercent().toString();
                change = stock.getQuote().getChange().toString();
                open = stock.getQuote().getOpen().toString();
                high = stock.getQuote().getDayHigh().toString();
                low = stock.getQuote().getDayLow().toString();
                annual = stock.getStats().getMarketCap().toString();
                time = stock.getQuote().getLastTradeDateStr();


                List<HistoricalQuote> history = stock.getHistory(Interval.DAILY);

                for(int i = 0; i < 5 ; i++)
                {
                    if( i == 4)
                    {
                        xaxis.add(i,"Today");
                    }
                    else
                    {
                        xaxis.add(i,"");
                    }
                    yaxis.add(new Entry(Float.parseFloat(history.get(4 - i).getAdjClose().toString()), i));

                }

            } catch (MalformedURLException ex) {
                result = "Connection Error";
                return null;
            } catch (IOException ex) {
                result = "Connection Error";
                ;
                return null;
            } catch (Exception e) {
                result = "Connection Error";
                return null;
            }


            result = "success";
            return null;
        }




        @Override
        protected void onPostExecute(View avoid) {


            if(result != "Connection Error" && !isCancelled()) {


                super.onPostExecute(avoid);

                View fragment1 = adapterPageview.getRegisteredFragment(0).getView();

                //value
                TextView valueshown = (TextView) fragment1.findViewById(R.id.valueadditional);
                double valueformat = Double.parseDouble(value);
                valueshown.setText("$" + f.format(valueformat));

                //open
                TextView openshown = (TextView) fragment1.findViewById(R.id.openadditional);
                double openformat = Double.parseDouble(open);
                openshown.setText("$" + f.format(openformat));


                //time
                TextView timeshown = (TextView) fragment1.findViewById(R.id.dateinfo);
                timeshown.setText("Last Trade Date: " + time);

                //high
                TextView highshown = (TextView) fragment1.findViewById(R.id.highadditional);
                double highformat = Double.parseDouble(high);
                highshown.setText("$" + f.format(highformat ));

                //low
                TextView lowshown = (TextView) fragment1.findViewById(R.id.lowadditional);
                double lowformat = Double.parseDouble(low);
                lowshown.setText( "$" + f.format(lowformat));

                //change
                TextView changeshown = (TextView) fragment1.findViewById(R.id.changeadditional);
                double changeformat = Double.parseDouble(change);
                changeshown.setText(f.format(changeformat));
                changeshown.setTextColor(Color.parseColor("#1a1010"));

                try {
                    if (Double.parseDouble(change) > 0) {

                        changeshown.setText("+" + f.format(changeformat) );
                        changeshown.setTextColor(Color.parseColor("#73C82C"));

                    } else if (Double.parseDouble(change) < 0) {
                        changeshown.setTextColor(Color.parseColor("#B00B1E"));

                    }
                }
                catch (Exception e)
                {

                }


                //percent
                TextView percentshown = (TextView) fragment1.findViewById(R.id.percentadditional);
                percentshown.setText(percent + "%");
                percentshown.setTextColor(Color.parseColor("#1a1010"));

                try {
                    if (Double.parseDouble(percent) > 0) {

                        percentshown.setText("+" + percent + "%");
                        percentshown.setTextColor(Color.parseColor("#73C82C"));

                    } else if (Double.parseDouble(percent) < 0) {
                        percentshown.setTextColor(Color.parseColor("#B00B1E"));

                    }
                }
                catch (Exception e)
                {

                }




                //volume
                int volumeint = Integer.parseInt(volume);
                String volumeinput = String.format("%.2fM", volumeint/ 1000000.0);
                TextView volumeshown = (TextView) fragment1.findViewById(R.id.volumeadditional);
                volumeshown.setText(volumeinput);

                //marketcap
                double marketint = Double.parseDouble(annual);
                String marketinput = String.format("%.2fB", marketint/ 1000000000.0);
                TextView annualshown = (TextView) fragment1.findViewById(R.id.annualadditional);
                annualshown.setText("$" + marketinput);

                //chart
                LineChart chart = (LineChart) fragment1.findViewById(R.id.chart);


                String[] Xaxis = new String[xaxis.size()];
                for(int i = 0; i < Xaxis.length; i++)
                {
                    Xaxis[i] = xaxis.get(i);
                }

                ArrayList<ILineDataSet> lineDataSet = new ArrayList<>();
                LineDataSet lineDataSet1 = new LineDataSet(yaxis,index + " value");
                lineDataSet1.setDrawCircles(true);
                lineDataSet1.setCircleColorHole(Color.BLUE);
                lineDataSet1.setColor(Color.BLUE);
                lineDataSet1.setDrawFilled(true);
                lineDataSet1.setCircleColor(Color.BLUE);
                lineDataSet1.setDrawCubic(true);

                lineDataSet.add(lineDataSet1);

                YAxis leftAxis = chart.getAxisLeft();
                YAxis rightAxis = chart.getAxisRight();

                float lowestprice = 0;
                float highestprice = 0;
                int degree = 0;

                for(int i = 0; i < yaxis.size(); i++)
                {
                    if(lowestprice > yaxis.get(i).getVal()) {
                        lowestprice = yaxis.get(i).getVal();

                    }
                    else if(lowestprice == 0)
                    {
                        lowestprice = yaxis.get(i).getVal();
                    }

                    if(highestprice < yaxis.get(i).getVal())
                    {
                        highestprice = yaxis.get(i).getVal();
                    }


                }


                degree = Math.round(highestprice - lowestprice);



                chart.getAxisRight().setStartAtZero(false);
                chart.getAxisLeft().setStartAtZero(false);
                leftAxis.setAxisMinValue(lowestprice - degree);
                rightAxis.setAxisMinValue(lowestprice - degree);
                chart.setPinchZoom(false);


                chart.setDescription("Close Value Over Last 5 Trading Days");

                chart.setData(new LineData(Xaxis, lineDataSet));
                chart.notifyDataSetChanged();
                chart.invalidate();



                CircularProgressView progressView = (CircularProgressView) fragment1.findViewById(R.id.progress_view);
                progressView.setVisibility(fragment1.GONE);

                TextView infotitleshown = (TextView) fragment1.findViewById(R.id.titleInfo);
                TextView datetitleshown = (TextView) fragment1.findViewById(R.id.dateinfo);
                TextView opentitleshown = (TextView) fragment1.findViewById(R.id.openTitle);
                TextView valuetitleshown = (TextView) fragment1.findViewById(R.id.currentTitle);
                TextView hightitleshown = (TextView) fragment1.findViewById(R.id.highTitle);
                TextView lowtitleshown = (TextView) fragment1.findViewById(R.id.lowTitle) ;
                TextView perenttitleshown = (TextView) fragment1.findViewById(R.id.percentTitle);
                TextView totalgaintitleshown = (TextView) fragment1.findViewById(R.id.totalGainTitle);
                TextView volumetitleshown = (TextView) fragment1.findViewById(R.id.volumeTitle);
                TextView annualtitleshown = (TextView) fragment1.findViewById(R.id.annualTitle);


                infotitleshown.setVisibility(fragment1.VISIBLE);
                valuetitleshown.setVisibility(fragment1.VISIBLE);
                datetitleshown.setVisibility(fragment1.VISIBLE);
                opentitleshown.setVisibility(fragment1.VISIBLE);
                hightitleshown.setVisibility(fragment1.VISIBLE);
                lowtitleshown.setVisibility(fragment1.VISIBLE);
                perenttitleshown.setVisibility(fragment1.VISIBLE);
                totalgaintitleshown.setVisibility(fragment1.VISIBLE);
                volumetitleshown .setVisibility(fragment1.VISIBLE);
                annualtitleshown.setVisibility(fragment1.VISIBLE);
                chart.setVisibility(fragment1.VISIBLE);


            }



        }



    }

    public class ReadRss extends AsyncTask<Void,Void,Void> {
        String result = "";
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> links = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();
        private new_adapter adapter;
        private News[] items = {};



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://finance.yahoo.com/rss/headline?s=";

            try{
                Document doc = Jsoup.connect(url +  index).get();
                Elements elements = doc.select("item > title");

                Elements elements2 = doc.select("item > link");
                Elements elements3 = doc.select("item > pubDate");

                if(elements.size() == 0 || elements2.size() == 0 || elements3.size() == 0)
                {
                    throw new Exception();
                }
                for(Element element : elements)
                {

                    titles.add(element.ownText());
                }

                for(Element element : elements2)
                {
                    links.add(element.ownText());
                }

                for(Element element : elements3)
                {
                    dates.add(element.ownText());
                }

                result = "success";



            }
            catch (MalformedURLException ex) {
                result = "Connection Error";
                return null;
            } catch (IOException ex) {
                result = "error";
                return null;
            } catch (Exception e) {
                result = "No News Availible";
                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(result != "No News Availible" && !isCancelled() && result != "Connection Error")
            {

                View fragment2 = adapterPageview.getRegisteredFragment(1).getView();
                arrayList = new ArrayList<News>(Arrays.asList(items));
                adapter = new new_adapter(getContext(), arrayList);

                final ListView listview = (ListView) fragment2.findViewById(R.id.listView2);
                listview.setAdapter(adapter);

                for (int i = 0; i < titles.size() ; i++)
                {
                    News addedNews = new News(titles.get(i), links.get(i), dates.get(i));
                    arrayList.add(addedNews);
                }


                CircularProgressView progressView = (CircularProgressView) fragment2.findViewById(R.id.progress_view2);
                progressView.setVisibility(fragment2.GONE);
                adapter.notifyDataSetChanged();


            }
            else if(result == "Connection Error")
            {
                View fragment2 = adapterPageview.getRegisteredFragment(1).getView();
                arrayList = new ArrayList<News>(Arrays.asList(items));
                adapter = new new_adapter(getContext(), arrayList);

                final ListView listview = (ListView) fragment2.findViewById(R.id.listView2);
                listview.setAdapter(adapter);
                News addedNews = new News(result, null, "Not Availible");
                arrayList.add(addedNews);
                CircularProgressView progressView = (CircularProgressView) fragment2.findViewById(R.id.progress_view2);
                progressView.setVisibility(fragment2.GONE);
                adapter.notifyDataSetChanged();

            }
            else if(result == "No News Availible")
            {
                View fragment2 = adapterPageview.getRegisteredFragment(1).getView();
                arrayList = new ArrayList<News>(Arrays.asList(items));
                adapter = new new_adapter(getContext(), arrayList);

                final ListView listview = (ListView) fragment2.findViewById(R.id.listView2);
                listview.setAdapter(adapter);
                News addedNews = new News(result, null, "Not Availible");
                arrayList.add(addedNews);
                CircularProgressView progressView = (CircularProgressView) fragment2.findViewById(R.id.progress_view2);
                progressView.setVisibility(fragment2.GONE);
                adapter.notifyDataSetChanged();


            }



        }



    }








}





