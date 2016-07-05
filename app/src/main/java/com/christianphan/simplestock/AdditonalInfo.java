package com.christianphan.simplestock;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

/**
 * Created by christian on 6/26/16.
 */
public class AdditonalInfo extends AppCompatActivity {



    private String values[] = new String[4];
    private String dates[] = new String[4];
    private String returnValue = "";
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




    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
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



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( name);
        getSupportActionBar().setElevation(0);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(),
                AdditonalInfo.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stock_settings:
                setResult(RESULT_OK);
                finish();
                return true;
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

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





}





