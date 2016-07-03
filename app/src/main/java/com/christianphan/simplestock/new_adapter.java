package com.christianphan.simplestock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by christian on 7/2/16.
 */
public class new_adapter extends ArrayAdapter<News> {


    new_adapter(Context context, ArrayList<News> news )
    {

        super(context,R.layout.news_items,news);

    }


    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.news_items, parent, false);
        }

        News newsitem = getItem(position);
        TextView titleText = (TextView) convertView.findViewById(R.id.newsTitle);
        TextView dateText = (TextView) convertView.findViewById(R.id.dateTitle);

        titleText.setText(newsitem.getTitle());
        dateText.setText(newsitem.getDate());


        return convertView;
    }
}
