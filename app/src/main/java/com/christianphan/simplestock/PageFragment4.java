package com.christianphan.simplestock;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PageFragment4 extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private String index;
    private ArrayList<String> myStringArray1 = new ArrayList<String>();
    private boolean edittext1filled = false;
    private boolean edittext2filled = false;
    private String value;
    private String amount;
    private String difference;
    private String currentprice;
    private String equity;


    public static PageFragment4 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment4 fragment = new PageFragment4();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final AdditonalInfo activity = (AdditonalInfo) getActivity();
        index = activity.getIndex();
        amount = activity.getAmountofShares();
        value = activity.getValueofShares();
        currentprice = activity.getValue();
        final DecimalFormat f = new DecimalFormat("#0.00");


        final View view = inflater.inflate(R.layout.your_shares_page, container, false);
        final TextView amountofsharesshown = (TextView) view.findViewById(R.id.amountofShares);
        final TextView valueofsharesshown = (TextView) view.findViewById(R.id.pricesofshares);
        final TextView differenceshown = (TextView) view.findViewById(R.id.differencedearned);
        final TextView equityshown = (TextView) view.findViewById(R.id.equity);

        amountofsharesshown.setText(amount);
        double valueformat = Double.parseDouble(value);
        valueofsharesshown.setText( "$" + f.format(valueformat));
        double equitydouble = Double.parseDouble(currentprice) * Integer.parseInt(amount);
        equityshown.setText("$" + f.format(equitydouble));

        double differencecalculated = (Double.parseDouble(currentprice) * Integer.parseInt(amount)) - (Double.parseDouble(value));
        difference = f.format(differencecalculated);
        differenceshown.setText("$" + difference);
        if(differencecalculated > 0)
        {
            differenceshown.setTextColor(Color.parseColor("#73C82C"));
        }
        else if(differencecalculated < 0)
        {
            differenceshown.setTextColor(Color.parseColor("#B00B1E"));

        }
        final Button button = (Button) view.findViewById(R.id.addstocksbutton);
        final Button button2 = (Button) view.findViewById(R.id.removesharesbutton);



        button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final AlertDialog OptionDialog = builder.create();
                OptionDialog.setTitle("Add Shares");
                View dialoglayout = getActivity().getLayoutInflater().inflate(R.layout.add_shares, null);
                OptionDialog.setView(dialoglayout);
                OptionDialog.show();

                final Button addButton = (Button) dialoglayout.findViewById(R.id.addstocks);
                addButton.setEnabled(false);
                final Button cancelbutton = (Button) dialoglayout.findViewById(R.id.cancel_addingstock);
                final EditText editText = (EditText) dialoglayout.findViewById(R.id.edittextone);
                final EditText editText2 = (EditText) dialoglayout.findViewById(R.id.edittexttwo);
                editText.setGravity(Gravity.RIGHT);
                editText2.setGravity(Gravity.RIGHT);
                editText2.setKeyListener(DigitsKeyListener.getInstance(true,true));


                editText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {

                        if(s.toString().trim().length()==0){
                            edittext1filled = false;
                        } else {
                            edittext1filled = true;
                        }

                        if (edittext1filled == true && edittext2filled == true)
                        {
                            addButton.setEnabled(true);
                        }

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                });



                editText2.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {

                        if(s.toString().trim().length()==0){
                            edittext2filled = false;
                        } else {

                            if(s.toString().matches(".*[123456789].*"))
                            {
                                edittext2filled = true;
                            }
                            else
                            {
                                edittext2filled = false;
                            }

                        }

                        if (edittext1filled == true && edittext2filled == true)
                        {
                            addButton.setEnabled(true);
                        }
                        if(edittext1filled == false || edittext2filled == false)
                        {
                            addButton.setEnabled(false);
                        }

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                });



                addButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        int intamount = Integer.parseInt(editText.getText().toString());
                        double value_input = Double.parseDouble(editText2.getText().toString()) * intamount;

                        int originalamount = Integer.parseInt(amount);
                        double originaldouble = Double.parseDouble(value);

                        int combinedamount = intamount + originalamount;
                        double combineddouble = originaldouble + value_input;
                        amount = Integer.toString(combinedamount);
                        value = f.format(combineddouble);



                        amountofsharesshown.setText(amount);
                        valueofsharesshown.setText("$" + value);
                        Double equitydouble2 = Double.parseDouble(currentprice) * Integer.parseInt(amount);
                        equityshown.setText("$" + f.format(equitydouble2));




                        double differencecalculated  = (Double.parseDouble(currentprice) * Integer.parseInt(amount)) - (Double.parseDouble(value));
                        difference = f.format(differencecalculated);
                        differenceshown.setText("$" + difference);
                        if(differencecalculated > 0)
                        {
                            differenceshown.setTextColor(Color.parseColor("#73C82C"));
                        }
                        else if(differencecalculated < 0)
                        {
                            differenceshown.setTextColor(Color.parseColor("#B00B1E"));

                        }

                        OptionDialog.cancel();

                    }


                });


                cancelbutton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        OptionDialog.cancel();

                    }


                });


            }


            });


        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final AlertDialog OptionDialog = builder.create();
                OptionDialog.setTitle("Reset Shares");
                View dialoglayout = getActivity().getLayoutInflater().inflate(R.layout.stockdelete, null);
                OptionDialog.setView(dialoglayout);
                OptionDialog.show();

                Button delete = (Button) dialoglayout.findViewById(R.id.deleteButton);
                Button cancel = (Button)  dialoglayout.findViewById(R.id.noDeleteButton);

                delete.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v)
                    {
                        amount = "0";
                        value = "0";

                        amountofsharesshown.setText(amount);
                        double valueformat = Double.parseDouble(value);
                        valueofsharesshown.setText( "$" + f.format(valueformat));
                        double equitydouble = Double.parseDouble(currentprice) * Integer.parseInt(amount);
                        equityshown.setText("$" + f.format(equitydouble));

                        double differencecalculated = (Double.parseDouble(currentprice) * Integer.parseInt(amount)) - (Double.parseDouble(value));
                        difference = f.format(differencecalculated);
                        differenceshown.setText("$" + difference);
                        differenceshown.setTextColor(Color.parseColor("#1a1010"));
                        OptionDialog.cancel();
                    }


                });

                cancel.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v)
                    {
                        OptionDialog.cancel();
                    }

                });


            }

        });




        return view;
    }

    public String getValue() {
        return value;
    }

    public String getAmount() {
        return amount;
    }
}