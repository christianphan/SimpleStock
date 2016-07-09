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
        TextView percentchange = (TextView) convertView.findViewById(R.id.Percent);
        TextView valuechange = (TextView) convertView.findViewById(R.id.stock_change);

      //  if(stocklistitem.color == 0) {

      //      ColorGenerator generator = ColorGenerator.MATERIAL;
       //     stocklistitem.color = generator.getRandomColor();

     //   }

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(stocklistitem.first, stocklistitem.color);

        Drawable arrow = ContextCompat.getDrawable(stocklistitem.context,android.R.color.transparent);

        if (Double.parseDouble(stocklistitem.percent) < 0)
        {
            arrow = ContextCompat.getDrawable(stocklistitem.context,R.drawable.ic_downarrow);



        }
        else if(Double.parseDouble(stocklistitem.percent) > 0)
        {
            arrow = ContextCompat.getDrawable(stocklistitem.context,R.drawable.ic_uparrow);
        }





        textname.setText(stocklistitem.name);
        valuename.setText(stocklistitem.value + " ");
        imageletter.setImageDrawable(drawable);
        indexname.setText("(" + stocklistitem.index + ")");
        imagearrow.setImageDrawable(arrow);
        percentchange.setText(stocklistitem.percent + "%");



        if (Double.parseDouble(stocklistitem.percent) < 0)
        {

            percentchange.setTextColor(Color.parseColor("#e53935"));
            valuechange.setText(stocklistitem.change + " ");
            valuechange.setTextColor(Color.parseColor("#e53935"));


        }
        else if(Double.parseDouble(stocklistitem.percent) > 0)
        {
            percentchange.setTextColor(Color.parseColor("#43A047"));
            valuechange.setText("+" + stocklistitem.change + " ");
            valuechange.setTextColor(Color.parseColor("#43A047"));
        }





        return convertView;

    }


    public void refresh(Context context)
    {



    }



}
