package com.christianphan.simplestock;


import android.app.Dialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;


import yahoofinance.YahooFinance;


public class MainActivity extends AppCompatActivity {


    DataBaseHelper myDB;
    private String index;
    private ArrayList<Stock> arrayList;
    private custom_adapter adapter;
    private Stock[] items ={};
    private String StringName = "";
    private String StringValue ="";
    private String StringIndex = "";
    private String StringPercent ="";
    private String StringColor = "";
    private ArrayList<String> valuearray = new ArrayList<String>();
    private ArrayList<String> percentArray = new ArrayList<String>();
    private int IntID;



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
        getList();


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog OptionDialog = builder.create();
                OptionDialog.setTitle("Enter Index");
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.stockinput, null);
                OptionDialog.setView(dialoglayout);
                OptionDialog.show();




                final EditText editText = (EditText) dialoglayout.findViewById(R.id.editText);
                Button submit = (Button) dialoglayout.findViewById(R.id.button2);
                Button cancel = (Button) dialoglayout.findViewById(R.id.button3);

                submit.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        index = editText.getText().toString().toUpperCase();
                        new YahooAPI().execute();



                        OptionDialog.cancel();

                    }


                });


                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        OptionDialog.cancel();
                    }

                });

            }


        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog OptionDialog = builder.create();
                OptionDialog.setTitle("Delete Index (" + arrayList.get(position).index + ")");
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.stockdelete, null);
                OptionDialog.setView(dialoglayout);
                OptionDialog.show();


                Button submit = (Button) dialoglayout.findViewById(R.id.deleteButton);
                Button cancel = (Button) dialoglayout.findViewById(R.id.noDeleteButton);

                submit.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        deleteItem(position);
                        OptionDialog.cancel();

                    }


                });





                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        OptionDialog.cancel();
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

            Stock addedStock = new Stock(StringName, StringValue, StringIndex,getApplicationContext(), StringPercent, 0, 0);
            StringColor = Integer.toString(addedStock.color);
            myDB.insertData(StringIndex,StringName, StringValue, StringPercent, StringColor);

            Cursor res = myDB.getLastData();
            String StringID = res.getString(0);
            addedStock.setID(StringID);


            arrayList.add(addedStock);
            adapter.notifyDataSetChanged();
            valuearray.add(StringValue);
            percentArray.add(StringPercent);








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

               // percentArray = new String[arrayList.size()];
               // valuearray = new String[arrayList.size()];

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
                    updatedStock.value = valuearray.get(i);
                    updatedStock.percent = percentArray.get(i);

                    arrayList.set(i, updatedStock);
                    String id = Integer.toString(updatedStock.primaryid);
                    myDB.updateData(id, updatedStock.index, updatedStock.name, updatedStock.value, updatedStock.percent, Integer.toString(updatedStock.color));


                }

            }

            adapter.notifyDataSetChanged();




        }



    }

    public boolean getList() {
        Cursor res = myDB.getAllData();


        if (res.getCount() == 0) {
            return false;
        }



        int position = 0;
        while (res.moveToNext()) {

            String StringID = res.getString(0);
            StringIndex = res.getString(1);
            StringName = res.getString(2);
            StringValue = res.getString(3);
            StringPercent = res.getString(4);
            StringColor = res.getString(5);


            Stock stockitem = new Stock(StringName, StringValue, StringIndex, getApplicationContext(), StringPercent,
                    Integer.parseInt(StringColor),Integer.parseInt(StringID));

            arrayList.add(stockitem);

            valuearray.add(StringValue);
            percentArray.add(StringPercent);
            position++;

        }

        return true;


    }

    public void deleteItem(int position)
    {
        Integer deletedRow = myDB.deleteData(Integer.toString(arrayList.get(position).primaryid));
        arrayList.remove(position);
        adapter.notifyDataSetChanged();
        valuearray.remove(position);
        percentArray.remove(position);

    }



}
