package com.christianphan.simplestock;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by christian on 7/1/16.
 */
public class PageFragment2 extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private String index;
    private ArrayList<String> myStringArray1 = new ArrayList<String>();


    public static PageFragment2 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment2 fragment = new PageFragment2();
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


        View view = inflater.inflate(R.layout.news_page, container, false);


        CircularProgressView progressView = (CircularProgressView) view.findViewById(R.id.progress_view2);
        progressView.startAnimation();

        final ListView listview = (ListView) view.findViewById(R.id.listView2);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                if(activity.getArrayList().get(position).getLink() != null) {


                    Intent j = new Intent(activity.getApplicationContext(), WebView.class);
                    Bundle b = new Bundle();
                    b.putString("URL", activity.getArrayList().get(position).getLink());
                    j.putExtras(b);
                    startActivity(j);

         //           Intent intent = new Intent(Intent.ACTION_VIEW,
          //                  Uri.parse(activity.getArrayList().get(position).getLink()));
         //           startActivity(intent);

                }

            }

        });


        return view;
    }







}