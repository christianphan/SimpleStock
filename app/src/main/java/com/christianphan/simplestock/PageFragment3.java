package com.christianphan.simplestock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;

public class PageFragment3 extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private String index;
    private ArrayList<String> myStringArray1 = new ArrayList<String>();


    public static PageFragment3 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment3 fragment = new PageFragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final AdditonalInfo activity = (AdditonalInfo) getActivity();
        index = activity.getIndex();
        View view = inflater.inflate(R.layout.annual_page, container, false);

        CircularProgressView progressView = (CircularProgressView) view.findViewById(R.id.progress_view3);
        progressView.startAnimation();

        TextView annualtitleeshown = (TextView) view.findViewById(R.id.Annual_Title);
        TextView annualhighestshown = (TextView) view.findViewById(R.id.annualhighest);
        TextView highesttitleshown = (TextView) view.findViewById(R.id.highestTitle);
        TextView lowestshown = (TextView) view.findViewById(R.id.annuallowest);
        TextView lowesttitleshown = (TextView) view.findViewById(R.id.lowestTitle);
        TextView lowtitleshown = (TextView) view.findViewById(R.id.lowTitle);
        TextView annualhighchangeshown = (TextView) view.findViewById(R.id.annualhighchange);
        TextView highpercenttitle= (TextView) view.findViewById(R.id.highPercentTitle);
        TextView annuallowchangeshown = (TextView) view.findViewById(R.id.annuallowChange);
        TextView lowchangetitleshown = (TextView) view.findViewById(R.id.lowTitle);
        LineChart chart = (LineChart) view.findViewById(R.id.chartannual);


        annualtitleeshown.setVisibility(view.GONE);
        annualhighchangeshown.setVisibility(view.GONE);
        annualhighestshown.setVisibility(view.GONE);
        highesttitleshown.setVisibility(view.GONE);
        lowestshown.setVisibility(view.GONE);
        lowesttitleshown.setVisibility(view.GONE);
        lowtitleshown.setVisibility(view.GONE);
        highpercenttitle.setVisibility(view.GONE);
        annuallowchangeshown.setVisibility(view.GONE);
        lowchangetitleshown.setVisibility(view.GONE);
        chart.setVisibility(view.GONE);


        return view;
    }


}
