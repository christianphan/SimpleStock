package com.christianphan.simplestock;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private String index;
    private ArrayList<Stock> arrayList;
    //private ArrayAdapter<Stock> adapter;
    private custom_adapter adapter;
    private Stock[] items ={};
    private String[] items2 ={};
    private String StringName = "";
    private String StringValue ="";
    private String StringIndex = "";



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView)findViewById(R.id.listView);
        arrayList = new ArrayList<Stock>(Arrays.asList(items));
        adapter = new custom_adapter(this,arrayList);
        listview.setAdapter(adapter);


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

                        index = editText.getText().toString();
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
                StringValue = "Price:" + Double.toString(roundOff);
                StringIndex = symboltext;


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

    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);


        if(StringName != "error")
        {
            //arrayList.add(StringName);
            //adapter.notifyDataSetChanged();

            Stock addedStock = new Stock(StringName, StringValue, StringIndex,getApplicationContext());

            arrayList.add(addedStock);
            adapter.notifyDataSetChanged();

        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Index error", Toast.LENGTH_SHORT);

        }

    }



}



}
