package com.christianphan.simplestock;

import android.content.Context;

import com.amulyakhare.textdrawable.util.ColorGenerator;

/**
 * Created by christian on 6/5/16.
 */
public class Stock {

    public String name;
    public String value;
    public String index;
    public int color = 0;
    public String first;
    public Context context;


    public Stock(String name_input, String value_input, String index_input, Context context_input)
    {
        this.name = name_input;
        this.value = value_input;
        this.index = index_input;
        this.context = context_input;


        first = String.valueOf(index.charAt(0));
    }


    public double getpercentage(String newValue)
    {
        double newValuedouble = Double.parseDouble(newValue);
        double valuedouble = Double.parseDouble(value);

        double percent = 100 - (newValuedouble/valuedouble);




        return 0;
    }



}
