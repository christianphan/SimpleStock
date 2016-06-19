package com.christianphan.simplestock;


import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import yahoofinance.YahooFinance;


public class MainActivity extends AppCompatActivity {


    DataBaseHelper myDB;
    private String index;
    private ArrayList<Stock> arrayList;
    //private ArrayAdapter<Stock> adapter;
    private custom_adapter adapter;
    private Stock[] items ={};
    private String StringName = "";
    private String StringValue ="";
    private String StringIndex = "";
    private String StringPercent ="";
    private String valuearray[];
    private String percentArray[];



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.refresh:
                refresh();
                return true;
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView)findViewById(R.id.listView);
        arrayList = new ArrayList<Stock>(Arrays.asList(items));
        adapter = new custom_adapter(this,arrayList);
        listview.setAdapter(adapter);
        myDB = new DataBaseHelper(this);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Enter Index");
                dialog.setContentView(R.layout.stockinput);
                dialog.show();


                final EditText editText = (EditText) dialog.findViewById(R.id.editText);
                Button submit = (Button) dialog.findViewById(R.id.button2);
                Button cancel = (Button) dialog.findViewById(R.id.button3);

                submit.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        index = editText.getText().toString().toUpperCase();
                        new YahooAPI().execute();



                        dialog.cancel();

                    }


                });


                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }

                });

            }


        });

    }



    public class YahooAPI extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... args) {

            try {


                /*
                Old code using jsoup. use in case yahoo finance api stops support

                String url = "http://finance.yahoo.com/webservice/v1/symbols/" + index + "/quote";
                Document document = Jsoup.connect(url).get();
                Elements priceget = document.select("field[name=price]");
                Elements utctimeget = document.select("field[name=utctime");
                Elements nameget = document.select("field[name=name");
                Elements symbolget = document.select("field[name=symbol");

                String pricetext = priceget.text().toString();
                String nametext = nameget.text().toString();
                String symboltext = symbolget.text().toString();
                double value = Double.parseDouble(pricetext);
                double roundOff = Math.round(value * 100.0) / 100.0;
                StringName = nametext;
                StringValue = Double.toString(roundOff);
                StringIndex = symboltext;

                */


                yahoofinance.Stock stock = YahooFinance.get(index);
                StringIndex = stock.getSymbol().toString();
                StringName = stock.getName().toString();
                StringValue = stock.getQuote().getPrice().toString();
                StringPercent = stock.getQuote().getChangeInPercent().toString();



            }

            catch (MalformedURLException ex)
            {
                StringName = "error";
                return null;
            }
        catch(IOException ex)
        {
            StringName = "error";
            return null;
        }
            catch (Exception e)
            {
                StringName = "error";
                return null;
            }



        return null;
    }

    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);


        if(StringName != "error")
        {
            //arrayList.add(StringName);
            //adapter.notifyDataSetChanged();

            Stock addedStock = new Stock(StringName, StringValue, StringIndex,getApplicationContext(), StringPercent);

            arrayList.add(addedStock);
            adapter.notifyDataSetChanged();



                boolean returnValue = myDB.insertData(StringIndex,StringName, StringValue, StringPercent);
                if(returnValue = true)
                {

                }


        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Index error", Toast.LENGTH_SHORT);
            toast.show();

        }

    }



}

    public void refresh()
    {
           // index = arrayList.get(i).index;
           // elementposition = i;
        new YahooAPIRefesh().execute();


    }


    public class YahooAPIRefesh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... args) {


            try{

                percentArray = new String[arrayList.size()];
                valuearray = new String[arrayList.size()];

                for(int i = 0; i < arrayList.size(); i++)
                {
                    index = arrayList.get(i).index;

                    /*
                    String url = "http://finance.yahoo.com/webservice/v1/symbols/" + index + "/quote";
                    Document document = Jsoup.connect(url).get();
                    Elements priceget = document.select("field[name=price]");
                    String pricetext = priceget.text().toString();
                    double value = Double.parseDouble(pricetext);
                    double roundOff = Math.round(value * 100.0) / 100.0;
                    StringValue = Double.toString(roundOff);

                    */
                    yahoofinance.Stock stock = YahooFinance.get(index);
                    StringValue = stock.getQuote().getPrice().toString();
                    StringPercent = stock.getQuote().getChangeInPercent().toString();

                    valuearray[i] = StringValue;
                    percentArray[i] = StringPercent;

                }


            }

            catch(
                    IOException ex
                    )

            {

                //ex.printStackTrace();
                StringName = "error";

            }


            return null;



        }


        protected void onPostExecute(Void avoid) {

            super.onPostExecute(avoid);
            if(StringName != "error")
            {
                for(int i = 0; i < arrayList.size(); i++)
                {

                    Stock updatedStock = arrayList.get(i);
                    updatedStock.value = valuearray[i];
                    updatedStock.percent = percentArray[i];

                    arrayList.set(i, updatedStock);
                    String id = Integer.toString(i + 1);
                    //myDB.updateData(id, updatedStock.index, updatedStock.name, updatedStock.value, updatedStock.percent);
                    myDB.updateData(id, updatedStock.index, updatedStock.name, updatedStock.value, updatedStock.percent);


                }

            }

            adapter.notifyDataSetChanged();




        }



    }





}
