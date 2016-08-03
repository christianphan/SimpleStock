package com.christianphan.simplestock;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by christian on 7/31/16.
 */
public class Portfolio extends AppCompatActivity {

    private PieChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView equity;
    private TextView value;
    private double equityDouble;
    private double valueDouble;
    private ArrayList<Entry> entries = new ArrayList<>();
    private ArrayList<String> label = new ArrayList<>();
    private DataBaseHelper myDB;
    private Float totalequity = 0f;
    private Float originalprice = 0f;
    private Float difference = 0f;
    private DecimalFormat df = new DecimalFormat("#.00");

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //back button and settings
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Portfolio");

        setContentView(R.layout.portfolio_layout);

        mChart = (PieChart) findViewById(R.id.piechart);
        equity = (TextView) findViewById(R.id.EquityPortfolio);
        value = (TextView) findViewById(R.id.PortfolioValue);
        myDB = new DataBaseHelper(this);

        setData();
        if(entries.size() > 0)
        {
            mChart.setDescription("Share Ratio");
            mChart.setUsePercentValues(false);
            mChart.setTouchEnabled(false);
            PieDataSet dataset = new PieDataSet(entries, "Stock Index");
            dataset.setColors(ColorTemplate.JOYFUL_COLORS);
            PieData data = new PieData(label, dataset);
            data.setValueTextSize(11f);
            data.setValueFormatter(new MyValueFormatter());
            mChart.setHoleRadius(58f);
            mChart.setData(data);

        }
        else
        {
            mChart.setVisibility(View.INVISIBLE);

        }







        String formattedequity = "$" + df.format(totalequity);
        equity.setText(formattedequity);

        difference = totalequity - originalprice;
        if(difference > 0)
        {
            String formatteddifference = "+" + df.format(difference);
            value.setText(formatteddifference);
            value.setTextColor(Color.parseColor("#73C82C"));
        }
        else if(difference < 0)
        {
            value.setText(df.format(difference));
            value.setTextColor(Color.parseColor("#B00B1E"));


        }
        else
        {
            value.setText(df.format(difference));
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private void setData() {
        Cursor res = myDB.getAllData();
        int count = 0;


        if (res.getCount() == 0) {

        }
        else
        {
            while (res.moveToNext())
            {
                int amount = Integer.parseInt(res.getString(13));
                String indexname = res.getString(1);
                if(amount > 0)
                {
                    float value = (float) amount * Float.parseFloat(res.getString(3));
                    float pricespent = (float) Float.parseFloat(res.getString(14));
                    originalprice += pricespent;
                    totalequity += value;
                    entries.add(new Entry(value, count));
                    label.add(indexname);
                    count++;
                }
            }
        }

    }

    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("#.00");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            // write your logic here
            if(value < 10) return "";

            return mFormat.format(value);
        }
    }

}
