package com.christianphan.simplestock;

import android.content.Context;

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
    public int color = 0;
    public String first;
    public Context context;
    public String percent;


    public Stock(String name_input, String value_input, String index_input, Context context_input, String percent_input)
    {
        this.name = name_input;
        this.value = value_input;
        this.index = index_input;
        this.context = context_input;
        this.percent = percent_input;


        first = String.valueOf(index.charAt(0));
    }






}
