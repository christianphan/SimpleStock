package com.christianphan.simplestock;


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
    private ArrayList<News> arrayList;
    private new_adapter adapter;
    private News[] items = {};


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
        AdditonalInfo activity = (AdditonalInfo) getActivity();
        index = activity.getIndex();


        View view = inflater.inflate(R.layout.news_page, container, false);


        CircularProgressView progressView = (CircularProgressView) view.findViewById(R.id.progress_view2);
        progressView.startAnimation();

        final ListView listview = (ListView) view.findViewById(R.id.listView2);
        arrayList = new ArrayList<News>(Arrays.asList(items));
        adapter = new new_adapter(((AdditonalInfo) getActivity()).getContext(), arrayList);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(arrayList.get(position).getLink()));
                startActivity(intent);

            }

        });


       new ReadRss(activity.getContext()).execute();



        return view;
    }


    public class ReadRss extends AsyncTask<Void,Void,Void> {
        Context context;
        String result = "";
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> links = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();

        public ReadRss(Context context) {
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = "http://finance.yahoo.com/rss/headline?s=";

            try{
                Document doc = Jsoup.connect(url +  index).get();
                Elements elements = doc.select("item > title");
                Elements elements2 = doc.select("item > link");
                Elements elements3 = doc.select("item > pubDate");

                for(Element element : elements)
                {
                    titles.add(element.ownText());
                }

                for(Element element : elements2)
                {
                    links.add(element.ownText());
                }

                for(Element element : elements3)
                {
                    dates.add(element.ownText());
                }

                result = "success";



            }
            catch (MalformedURLException ex) {
                result = "error";
                return null;
            } catch (IOException ex) {
                result = "error";
                return null;
            } catch (Exception e) {
                result = "error";
                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(result != "error" && !isCancelled())
            {


                for (int i = 0; i < titles.size() ; i++)
                {
                    News addedNews = new News(titles.get(i), links.get(i), dates.get(i));
                    arrayList.add(addedNews);
                }


                CircularProgressView progressView = (CircularProgressView) getView().findViewById(R.id.progress_view2);
                progressView.setVisibility(getView().GONE);
                adapter.notifyDataSetChanged();


            }



        }



    }





}