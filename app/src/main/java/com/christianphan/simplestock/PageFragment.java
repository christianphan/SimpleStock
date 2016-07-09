
package com.christianphan.simplestock;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

/**
 * Created by christian on 7/1/16.
 */
// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View gettheView()
    {
        return getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_page, container, false);

        CircularProgressView progressView = (CircularProgressView) view.findViewById(R.id.progress_view);
        progressView.startAnimation();

        TextView infotitleshown = (TextView) view.findViewById(R.id.titleInfo);
        TextView datetitleshown = (TextView) view.findViewById(R.id.dateinfo);
        TextView opentitleshown = (TextView) view.findViewById(R.id.openTitle);
        TextView valuetitleshown = (TextView) view.findViewById(R.id.currentTitle);
        TextView hightitleshown = (TextView) view.findViewById(R.id.highTitle);
        TextView lowtitleshown = (TextView) view.findViewById(R.id.lowTitle) ;
        TextView perenttitleshown = (TextView) view.findViewById(R.id.percentTitle);
        TextView totalgaintitleshown = (TextView) view.findViewById(R.id.totalGainTitle);
        TextView volumetitleshown = (TextView) view.findViewById(R.id.volumeTitle);
        TextView annualtitleshown = (TextView) view.findViewById(R.id.annualTitle);
        LineChart chart = (LineChart) view.findViewById(R.id.chart);


        infotitleshown.setVisibility(view.GONE);
        valuetitleshown.setVisibility(view.GONE);
        datetitleshown.setVisibility(view.GONE);
        opentitleshown.setVisibility(view.GONE);
        hightitleshown.setVisibility(view.GONE);
        lowtitleshown.setVisibility(view.GONE);
        perenttitleshown.setVisibility(view.GONE);
        totalgaintitleshown.setVisibility(view.GONE);
        volumetitleshown .setVisibility(view.GONE);
        annualtitleshown.setVisibility(view.GONE);
        chart.setVisibility(view.GONE);

        return view;
    }

}



