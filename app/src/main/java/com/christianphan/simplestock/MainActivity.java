package com.christianphan.simplestock;



import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;



import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;


public class MainActivity extends AppCompatActivity {


    DataBaseHelper myDB;
    private String index;
    private ArrayList<Stock> arrayList;
    private custom_adapter adapter;
    private data datalist = new data();
    private Stock[] items = {};
    SwipeRefreshLayout mSwipeRefreshLayout;
    boolean deleteStock = false;
    int stockposition = 0;
    String values[] = new String[4];
    String dates[] = new String[4];



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                return true;
            case R.id.menu_settings:
                aboutdialog();
                return true;
            case R.id.version:
                versionnumber();
                return true;
            case R.id.add:
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

            default:
                return super.onOptionsItemSelected(item);


        }


    }

    public void aboutdialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog OptionDialog = builder.create();
        OptionDialog.setTitle("About");
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.about_info, null);
        OptionDialog.setView(dialoglayout);
        OptionDialog.show();

    }

    public void versionnumber()
    {

        try
        {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            final AlertDialog OptionDialog = builder.create();
            OptionDialog.setTitle("Version:");
            LayoutInflater inflater = getLayoutInflater();
            View dialoglayout = inflater.inflate(R.layout.version_info, null);
            OptionDialog.setView(dialoglayout);

            TextView versionshow = (TextView) dialoglayout.findViewById(R.id.textView3) ;
            versionshow.setText(version);
            OptionDialog.show();


        }
        catch(Exception io)
        {

        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // code for result
                deleteStock = true;
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }


    public void onResume(){
        super.onResume();
        if (deleteStock == true){
            deleteStock = false;
            deleteItem(stockposition);


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView) findViewById(R.id.listView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout. setColorSchemeResources(R.color.colorPrimary);
        arrayList = new ArrayList<Stock>(Arrays.asList(items));
        adapter = new custom_adapter(this, arrayList);
        listview.setAdapter(adapter);
        myDB = new DataBaseHelper(this);
        getList();



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Intent i = new Intent(MainActivity.this, AdditonalInfo.class);

                stockposition = position;

                String testValue = arrayList.get(stockposition).value;
                String testPercent = arrayList.get(stockposition).percent;
                String testName = arrayList.get(stockposition).name;
                String testColor = Integer.toString(arrayList.get(stockposition).color);
                String testIndex = arrayList.get(stockposition).index;
                String testVolume = arrayList.get(stockposition).volume;
                String testOpen = arrayList.get(stockposition).open;
                String testHigh = arrayList.get(stockposition).high;
                String testLow = arrayList.get(stockposition).low;
                String testChange = arrayList.get(stockposition).change;
                String testAnnual = arrayList.get(stockposition).annual;
                String testTime = arrayList.get(stockposition).time;
                String testdate1 = dates[0];
                String testdate2 = dates[1];
                String testdate3 = dates[2];
                String testdate4 = dates[3];
                String testprice1 = values[0];
                String testprice2 = values[1];
                String testprice3 = values[2];
                String testprice4 = values[3];
                String testprice5 = arrayList.get(stockposition).value;
                int intchange = datalist.IsPositive(stockposition);

                try {

                    intchange = Integer.parseInt(datalist.getChangeFromArray(stockposition).trim());
                } catch (NumberFormatException nfe) {

                }


                Bundle b = new Bundle();


                b.putString("Value", testValue);
                b.putString("Percent", testPercent);
                b.putString("Name", testName);
                b.putString("Color", testColor);
                b.putString("Index", testIndex);
                b.putString("Volume", testVolume);
                b.putString("Open", testOpen);
                b.putString("High", testHigh);
                b.putString("Low", testLow);
                b.putString("Change", testChange);
                b.putString("Annual", testAnnual);
                b.putString("Time", testTime);
                b.putInt("Test", intchange);
                b.putString("Date1", testdate1);
                b.putString("Date2", testdate2);
                b.putString("Date3", testdate3);
                b.putString("Date4", testdate4);
                b.putString("Price1", testprice1);
                b.putString("Price2", testprice2);
                b.putString("Price3", testprice3);
                b.putString("Price4", testprice4);
                b.putString("Price5", testprice5);
                i.putExtras(b);


                startActivityForResult(i, 1);


            }

        });


        listview.setLongClickable(true);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog OptionDialog = builder.create();
                OptionDialog.setTitle("Delete " + arrayList.get(pos).index );
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.stockdelete, null);
                OptionDialog.setView(dialoglayout);
                OptionDialog.show();


                Button submit = (Button) dialoglayout.findViewById(R.id.deleteButton);
                Button cancel = (Button) dialoglayout.findViewById(R.id.noDeleteButton);

                submit.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        deleteItem(pos);

                        OptionDialog.cancel();

                    }


                });


                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        OptionDialog.cancel();
                    }

                });

                return true;
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {



            @Override
            public void onRefresh()
            {
                refresh();

            }




        });
        ;

    }


    public class YahooAPI extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... args) {

            try {

                yahoofinance.Stock stock = YahooFinance.get(index);




                datalist.setVaribles(stock.getName().toString(),stock.getQuote().getPrice().toString(), stock.getSymbol().toString(),
                        stock.getQuote().getChangeInPercent().toString(), stock.getQuote().getChange().toString(), stock.getQuote().getOpen().toString(),
                        stock.getQuote().getDayHigh().toString(), stock.getQuote().getDayLow().toString(),stock.getQuote().getVolume().toString(),
                        stock.getQuote().getYearHigh().toString(), stock.getQuote().getLastTradeDateStr());

                ;

            } catch (MalformedURLException ex) {
                datalist.setStringName("error");
                return null;
            } catch (IOException ex) {
                datalist.setStringName("error");;
                return null;
            } catch (Exception e) {
                datalist.setStringName("error");
                return null;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);


            if (datalist.getStringName() != "error") {


                Stock addedStock = new Stock(datalist.getStringName(), datalist.getStringValue(),
                        datalist.getStringIndex(), getApplicationContext(), datalist.getStringPercent(), 0, 0, datalist.getStringChange(),
                        datalist.getStringAnnual(), datalist.getStringHigh(),
                        datalist.getStringVolume(), datalist.getStringOpen(), datalist.getStringLow(), datalist.getStringTime());


                datalist.setStringColor(Integer.toString(addedStock.color));

                myDB.insertData(datalist.getStringIndex(), datalist.getStringName(), datalist.getStringValue(),
                        datalist.getStringPercent(), datalist.getStringColor(), datalist.getStringChange(), datalist.getStringOpen(),datalist.getStringHigh(),
                        datalist.getStringLow(), datalist.getStringVolume(), datalist.getStringAnnual(), datalist.getStringTime());


                //grabs stock id from myDB and sets the stock to have the same id
                Cursor res = myDB.getLastData();
                String StringID = res.getString(0);
                addedStock.setID(StringID);


                arrayList.add(addedStock);
                adapter.notifyDataSetChanged();
                datalist.AddToArrayList();

                Toast toast = Toast.makeText(getApplicationContext(), "(" + datalist.getStringIndex() + ")" + " added", Toast.LENGTH_SHORT);
                toast.show();



            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Index error", Toast.LENGTH_SHORT);
                toast.show();

            }

        }


    }

    public void refresh() {

        new YahooAPIRefesh().execute();

    }


    public class YahooAPIRefesh extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {


            try {

                //gets all indexes into an array
                String[] indexArray = new String[arrayList.size()];
                for(int j = 0; j < arrayList.size(); j++)
                {
                    indexArray[j] = arrayList.get(j).index;
                }


                //single request
                Map<String, yahoofinance.Stock> stock = YahooFinance.get(indexArray);



                for (int i = 0; i < arrayList.size(); i++) {





                    //updates new datalist with information from new refresh
                    datalist.updateArrayList(i,stock.get(indexArray[i]).getQuote().getPrice().toString(),
                            stock.get(indexArray[i]).getQuote().getChangeInPercent().toString(),stock.get(indexArray[i]).getQuote().getChange().toString(),
                            stock.get(indexArray[i]).getQuote().getOpen().toString(), stock.get(indexArray[i]).getQuote().getDayHigh().toString(),
                            stock.get(indexArray[i]).getQuote().getDayLow().toString(), stock.get(indexArray[i]).getQuote().getVolume().toString(), stock.get(indexArray[i]).getQuote().getYearHigh().toString(),
                            stock.get(indexArray[i]).getQuote().getLastTradeDateStr());

                }


            }
            catch (MalformedURLException ex) {
                datalist.setStringName("error");
                return "error";
            } catch (IOException ex) {
                datalist.setStringName("error");
                return "error";
            } catch (Exception e) {
                datalist.setStringName("error");
                return "error";
            }


            return "Success";


        }


        protected void onPostExecute(String avoid) {

            super.onPostExecute(avoid);
            if (datalist.getStringName() != "error") {

                for (int i = 0; i < arrayList.size(); i++) {


                    Stock updatedStock = arrayList.get(i);
                    updatedStock.value = datalist.getValueFromArray(i);
                    updatedStock.percent = datalist.getPercentFromArray(i);
                    updatedStock.change = datalist.getChangeFromArray(i);
                    updatedStock.open = datalist.getOpenFromArray(i);
                    updatedStock.high = datalist.getHighFromArray(i);
                    updatedStock.low = datalist.getLowFromArray(i);
                    updatedStock.volume = datalist.getVolumeFromArray(i);
                    updatedStock.annual = datalist.getAnnualFromArray(i);
                    updatedStock.time = datalist.getTimeFromArray(i);

                    arrayList.set(i, updatedStock);
                    String id = Integer.toString(updatedStock.primaryid);
                    myDB.updateData(id, updatedStock.index, updatedStock.name, updatedStock.value,
                            updatedStock.percent, Integer.toString(updatedStock.color), updatedStock.change, updatedStock.open,
                            updatedStock.high, updatedStock.low, updatedStock.volume, updatedStock.annual, updatedStock.time);


                }



                adapter.notifyDataSetChanged();

                Toast toast = Toast.makeText(getApplicationContext(), "Stock List Updated", Toast.LENGTH_SHORT);
                toast.show();
                mSwipeRefreshLayout.setRefreshing(false);

            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT);
                toast.show();
                mSwipeRefreshLayout.setRefreshing(false);
            }


        }

    }









    public boolean getList() {
        Cursor res = myDB.getAllData();


        if (res.getCount() == 0) {
            return false;
        }



        while (res.moveToNext()) {


            datalist.setVaribles(res.getString(2), res.getString(3), res.getString(1), res.getString(4), res.getString(6) ,res.getString(7), res.getString(8),
                    res.getString(9), res.getString(10), res.getString(11) ,res.getString(12));



            String StringID = res.getString(0);
            // String StringIndex =res.getString(1);
            //    StringName = res.getString(2);
            //    StringValue = res.getString(3);
            //    StringPercent = res.getString(4);
            String   StringColor = res.getString(5);
            //    StringChange = res.getString(6);


            Stock stockitem = new Stock(datalist.getStringName(), datalist.getStringValue(),
                    datalist.getStringIndex(), getApplicationContext(), datalist.getStringPercent(), Integer.parseInt(StringColor),Integer.parseInt(StringID),
                    datalist.getStringChange(), datalist.getStringAnnual(), datalist.getStringHigh(),
                    datalist.getStringVolume(), datalist.getStringOpen(), datalist.getStringLow(), datalist.getStringTime());

            arrayList.add(stockitem);
            datalist.AddToArrayList();



        }

        return true;


    }

    public void deleteItem(int position)
    {
        String indexname = arrayList.get(position).index;

        Integer deletedRow = myDB.deleteData(Integer.toString(arrayList.get(position).primaryid));
        arrayList.remove(position);
        adapter.notifyDataSetChanged();
        datalist.removeArrayList(position);


        Toast toast = Toast.makeText(getApplicationContext(), "("  + indexname + ")" + " Deleted", Toast.LENGTH_LONG);
        toast.show();
    }



}
