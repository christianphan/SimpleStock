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
import android.view.View;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 * Created by christian on 6/26/16.
 */
public class AdditonalInfo extends AppCompatActivity {



    private String values[] = new String[4];
    private String dates[] = new String[4];
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
    TabAdapter adapterPageview;
    ReadRss readRss = null;
    NewActivity newActivity = null;

    public ArrayList<News> getArrayList() {
        return arrayList;
    }

    private ArrayList<News> arrayList;





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
        adapterPageview = new TabAdapter(getSupportFragmentManager(),
                AdditonalInfo.this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapterPageview);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);



        newActivity = new NewActivity();
        newActivity.execute();

        readRss = new ReadRss();
        readRss.execute();



    }


    @Override
    public void onBackPressed()
    {
        if (readRss != null)
            readRss.cancel(true);

        if (newActivity != null)
            newActivity.cancel(true);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stock_settings:
                setResult(RESULT_OK);
                finish();
                return true;
            case android.R.id.home:

                if (readRss != null)
                    readRss.cancel(true);

                if (newActivity != null)
                    newActivity.cancel(true);

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





    public class NewActivity extends AsyncTask<View, View, View> {


        private String result;
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
                volume = stock.getQuote().getVolume().toString();
                annual = stock.getQuote().getYearHigh().toString();
                time = stock.getQuote().getLastTradeDateStr();

                price5 = stock.getHistory(Interval.DAILY).get(0).getClose().toString();
                price4 = stock.getHistory(Interval.DAILY).get(1).getClose().toString();
                price3 = stock.getHistory(Interval.DAILY).get(2).getClose().toString();
                price2 = stock.getHistory(Interval.DAILY).get(3).getClose().toString();
                price1 = stock.getHistory(Interval.DAILY).get(4).getClose().toString();
                date4 = "";
                date3 = "";
                date2 = "";
                date1 = "";

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
                valueshown.setText("$" + value);

                //open
                TextView openshown = (TextView) fragment1.findViewById(R.id.openadditional);
                openshown.setText("$" + open);


                //time
                TextView timeshown = (TextView) fragment1.findViewById(R.id.dateinfo);
                timeshown.setText("Last Trade Date: " + time);

                //high
                TextView highshown = (TextView) fragment1.findViewById(R.id.highadditional);
                highshown.setText("$" + high );

                //low
                TextView lowshown = (TextView) fragment1.findViewById(R.id.lowadditional);
                lowshown.setText( "$" + low);

                //change
                TextView changeshown = (TextView) fragment1.findViewById(R.id.changeadditional);
                changeshown.setText(change);
                if(test == 1)
                {

                    changeshown.setText("+" + change);
                }
                else if (test == 0)
                {
                    changeshown.setTextColor(Color.parseColor("#B00B1E"));
                }


                //percent
                TextView percentshown = (TextView) fragment1.findViewById(R.id.percentadditional);
                percentshown.setText(percent + "%");
                if(test == 1)
                {

                    percentshown.setText("+" + percent + "%");
                }
                else if (test == 0)
                {
                    percentshown.setTextColor(Color.parseColor("#B00B1E"));
                }




                //volume
                int volumeint = Integer.parseInt(volume);
                String volumeinput = String.format("%.2fM", volumeint/ 1000000.0);
                TextView volumeshown = (TextView) fragment1.findViewById(R.id.volumeadditional);
                volumeshown.setText(volumeinput);

                //annual
                TextView annualshown = (TextView) fragment1.findViewById(R.id.annualadditional);
                annualshown.setText(annual);

                //chart
                ArrayList<String> xaxis = new ArrayList<>();
                ArrayList<Entry> yaxis = new ArrayList<>();



                xaxis.add(0,date1 );
                xaxis.add(1, date2);
                xaxis.add(2, date3);
                xaxis.add(3, date4);
                xaxis.add(4, "Today");



                yaxis.add(new Entry(Float.parseFloat(price1), 0));
                yaxis.add(new Entry(Float.parseFloat(price2), 1));
                yaxis.add(new Entry(Float.parseFloat(price3), 2));
                yaxis.add(new Entry(Float.parseFloat(price4), 3));
                yaxis.add(new Entry(Float.parseFloat(price5), 4));


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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(result != "error" && !isCancelled())
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



        }



    }








}





