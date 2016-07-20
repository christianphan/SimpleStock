package com.christianphan.simplestock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by christian on 7/9/16.
 */
public class options_adapter extends ArrayAdapter<String>
{

    options_adapter(Context context, ArrayList<String> list)
    {

        super(context,R.layout.optionitems,list);

    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.stock_items, parent, false);
        }

        String optiontext = getItem(position);
        TextView optiontitle = (TextView) convertView.findViewById(R.id.openTitle);
        optiontitle.setText(optiontext);


        return  convertView;
    }

}
