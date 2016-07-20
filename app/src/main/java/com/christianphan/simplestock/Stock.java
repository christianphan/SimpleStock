package com.christianphan.simplestock;

import android.content.Context;
import android.content.Intent;

import com.amulyakhare.textdrawable.util.ColorGenerator;

/**
 * Created by christian on 6/5/16.
 */
public class Stock {

    public String name;
    public String value;
    public String oldValue = "0";
    public String dayStart = "0";
    public String index;
    public int color;
    public String first;
    public Context context;
    public String percent;
    public int primaryid;
    public String change;
    public String annual;
    public String high;
    public String volume;
    public String open;
    public String low;
    public String time;
    public String amountofShares;
    public String valueofShares;


    public Stock(String name_input, String value_input, String index_input, Context context_input,
                 String percent_input, int color_input, int id_input, String change_input, String annual_input, String high_input,
                 String volume_input, String open_input, String low_input, String time_input, String amountofshares_input, String valueofShares_input)
    {
        this.name = name_input;
        this.value = value_input;
        this.index = index_input;
        this.context = context_input;
        this.percent = percent_input;
        this.color = color_input;
        this.primaryid = id_input;
        this.change = change_input;
        this.annual = annual_input;
        this.high = high_input;
        this.volume = volume_input;
        this.open = open_input;
        this.low = low_input;
        this.time = time_input;
        this.amountofShares = amountofshares_input;
        this.valueofShares = valueofShares_input;




        if(color == 0) {

            ColorGenerator generator = ColorGenerator.MATERIAL;
            color = generator.getRandomColor();

        }


        first = String.valueOf(index.charAt(0));
    }


    public void setID(String id_input)
    {
        this.primaryid = Integer.parseInt(id_input);

    }






}
