package com.christianphan.simplestock;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * Created by christian on 7/1/16.
 */
// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
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

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdditonalInfo activity = (AdditonalInfo) getActivity();
        index = activity.getIndex();
        value = activity.getValue();
        color = activity.getColor();
        name = activity.getName();
        percent = activity.getPercent();
        volume = activity.getVolume();
        annual = activity.getAnnual();
        open = activity.getOpen();
        high = activity.getHigh();
        low = activity.getLow();
        change = activity.getChange();
        test = activity.getTest();
        date1 = activity.getDate1();
        date2 = activity.getDate2();
        date3 = activity.getDate3();
        date4 = activity.getDate4();
        price1 = activity.getPrice1();
        price2 = activity.getPrice2();
        price3 = activity.getPrice3();
        price4 = activity.getPrice4();
        price5 = activity.getPrice5();
        time = activity.getTime();

        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_page, container, false);


        //value
        TextView valueshown = (TextView) view.findViewById(R.id.valueadditional);
        valueshown.setText("$" + value);

        //open
        TextView openshown = (TextView) view.findViewById(R.id.openadditional);
        openshown.setText("$" + open);


        //time
        TextView timeshown = (TextView) view.findViewById(R.id.dateinfo);
        timeshown.setText("Last Trade Date: " + time);

        //high
        TextView highshown = (TextView) view.findViewById(R.id.highadditional);
        highshown.setText("$" + high );

        //low
        TextView lowshown = (TextView) view.findViewById(R.id.lowadditional);
        lowshown.setText( "$" + low);

        //change
        TextView changeshown = (TextView) view.findViewById(R.id.changeadditional);
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
        TextView percentshown = (TextView) view.findViewById(R.id.percentadditional);
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
        TextView volumeshown = (TextView) view.findViewById(R.id.volumeadditional);
        volumeshown.setText(volumeinput);

        //annual
        TextView annualshown = (TextView) view.findViewById(R.id.annualadditional);
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


        LineChart chart = (LineChart) view.findViewById(R.id.chart);


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


        return view;
    }
}