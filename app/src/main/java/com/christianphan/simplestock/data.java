package com.christianphan.simplestock;

import java.util.ArrayList;

/**
 * Created by christian on 6/26/16.
 */
public class data {

    private String StringName = "";
    private String StringValue = "";
    private String StringIndex = "";
    private String StringPercent = "";
    private String StringColor = "";
    private String StringChange = "";
    private String StringOpen ="";
    private String StringHigh = "";
    private String StringLow = "";
    private String StringVolume = "";
    private String StringAnnual = "";
    private String StringTime = "";
    private String Stringdate = "";
    private String Stringdate2 = "";
    private String Stringdate3 = "";
    private String Stringdate4 = "";
    private String Stringprice1 = "";
    private String Stringprice2 = "";
    private String Stringprice3 = "";
    private String Stringprice4 = "";
    private String amountofShares = "";
    private String valueofShares = "";
    private ArrayList<String> valuearray = new ArrayList<String>();
    private ArrayList<String> percentArray = new ArrayList<String>();
    private ArrayList<String> changeArray = new ArrayList<String>();
    private ArrayList<String> openArray = new ArrayList<String>();
    private ArrayList<String> highArray = new ArrayList<String>();
    private ArrayList<String> lowArray = new ArrayList<String>();
    private ArrayList<String> volumeArray = new ArrayList<String>();
    private ArrayList<String> annualArray = new ArrayList<String>();
    private ArrayList<String> timeArray = new ArrayList<String>();
    private ArrayList<String> amountOfSharesArray = new ArrayList<>();
    private ArrayList<String> valueofSharesArray = new ArrayList<String>();

    public void setVaribles(String name, String value, String index, String percent,String change, String open, String high,
                            String low, String volume, String annual, String time, String amount, String valueofShare)
    {

        this.StringName = name;
        this.StringValue = value;
        this.StringIndex = index;
        this.StringPercent = percent;
        this.StringChange = change;
        this.StringOpen = open;
        this.StringHigh = high;
        this.StringLow = low;
        this.StringVolume = volume;
        this.StringAnnual = annual;
        this.StringTime = time;
        this.amountofShares = amount;
        this.valueofShares = valueofShare;

    }

    public void setStringName(String name)
    {
        this.StringName = name;
    }

    public String getStringName()
    {
        return StringName;
    }

    public String getStringValue() {
        return StringValue;
    }


    public String getStringIndex() {
        return StringIndex;
    }

    public String getStringPercent() {
        return StringPercent;
    }

    public String getStringChange() {
        return StringChange;
    }

    public String getStringOpen() {
        return StringOpen;
    }


    public String getStringHigh() {
        return StringHigh;
    }


    public String getStringLow() {
        return StringLow;
    }


    public String getStringVolume() {
        return StringVolume;
    }


    public String getStringAnnual() {
        return StringAnnual;
    }


    public String getStringTime() {
        return StringTime;
    }

    public String getStringColor() {return StringColor;}


    public String getAmountofShares() {
        return amountofShares;
    }

    public String getValueofShares() {
        return valueofShares;
    }

    public void setStringColor(String stringColor) {
        StringColor = stringColor;
    }

    public void AddToArrayList()
    {
        valuearray.add(StringValue);
        percentArray.add(StringPercent);
        changeArray.add(StringChange);
        openArray.add(StringOpen);
        highArray.add(StringHigh);
        lowArray.add(StringLow);
        volumeArray.add(StringVolume);
        annualArray.add(StringAnnual);
        timeArray.add(StringTime);
        valueofSharesArray.add(valueofShares);
        amountOfSharesArray.add(amountofShares);


    }


    public void updateArrayList(int position, String value, String percent, String change, String open, String high,
                                String low, String volume, String annual, String time)
    {
        valuearray.set(position, value);
        percentArray.set(position, percent);
        changeArray.set(position, change);
        openArray.set(position, open);
        highArray.set(position, high);
        lowArray.set(position, low);
        volumeArray.set(position, volume);
        annualArray.set(position, annual);
        timeArray.set(position, time);

    }

    public void removeArrayList(int position)
    {
        valuearray.remove(position);
        percentArray.remove(position);
        changeArray.remove(position);
        openArray.remove(position);
        highArray.remove(position);
        lowArray.remove(position);
        volumeArray.remove(position);
        annualArray.remove(position);
        timeArray.remove(position);


    }

    public String getValueFromArray(int position)
    {
        return valuearray.get(position);
    }

    public String getPercentFromArray(int position)
    {
        return percentArray.get(position);
    }

    public String getChangeFromArray(int position)
    {
        return changeArray.get(position);
    }

    public String getOpenFromArray(int position)
    {
        return openArray.get(position);
    }

    public String getHighFromArray(int position)
    {
        return highArray.get(position);
    }

    public String getLowFromArray(int position)
    {
        return lowArray.get(position);
    }

    public String getVolumeFromArray(int position)
    {
        return volumeArray.get(position);
    }

    public String getAnnualFromArray(int position)
    {
        return annualArray.get(position);
    }

    public String getTimeFromArray(int position)
    {
        return timeArray.get(position);
    }

    public String getAmountOfSharesArray(int position) {
        return amountOfSharesArray.get(position);
    }

    public String getValueofSharesArray(int position) {
        return valueofSharesArray.get(position);
    }

    public int IsPositive(int position)
    {
        if(Double.parseDouble(changeArray.get(position)) > 0)
        {
            return 1;
        }
        else
        {
            return 0;
        }


    }
}
