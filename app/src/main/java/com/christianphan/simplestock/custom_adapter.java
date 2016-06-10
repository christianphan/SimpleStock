package com.christianphan.simplestock;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

/**
 * Created by christian on 6/5/16.
 */
public class custom_adapter  extends ArrayAdapter<Stock> {


    custom_adapter(Context context,ArrayList<Stock> stocks)
    {

        super(context,R.layout.stock_items,stocks);



    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.stock_items, parent, false);
        }


        Stock stocklistitem = getItem(position);
        TextView textname = (TextView) convertView.findViewById(R.id.stockitem_name);
        TextView valuename = (TextView) convertView.findViewById(R.id.stockitem_value);
        ImageView imageletter = (ImageView) convertView.findViewById(R.id.Icon);
        TextView indexname = (TextView) convertView.findViewById(R.id.stock_index);
        ImageView imagearrow = (ImageView) convertView.findViewById(R.id.DownUp);



        if(stocklistitem.color == 0) {

            ColorGenerator generator = ColorGenerator.MATERIAL;
            stocklistitem.color = generator.getRandomColor();

        }

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(stocklistitem.first, stocklistitem.color);

        Drawable arrow = ContextCompat.getDrawable(stocklistitem.context,android.R.color.transparent);




        textname.setText(stocklistitem.name);
        valuename.setText(stocklistitem.value);
        imageletter.setImageDrawable(drawable);
        indexname.setText("(" + stocklistitem.index + ")");
        imagearrow.setImageDrawable(arrow);

        return convertView;

    }



}
