package com.example.optimus.blooddonation;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.widget.Toast.*;

public class Custom_Adapter extends ArrayAdapter<Application> {


    private List<Application> items;

    public Custom_Adapter(Context context, List<Application> items){

        super(context, R.layout.custom_list, items);
        this.items = items;
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if(v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.custom_list, null);
        }

        Application app = items.get(position);

        if(app != null) {
            ImageView blood_icon = (ImageView)v.findViewById(R.id.blood_icon);
            TextView donor_name = (TextView)v.findViewById(R.id.donor_name);
            TextView donor_location = (TextView)v.findViewById(R.id.donor_location);
            final ImageView phone_icon = (ImageView)v.findViewById(R.id.phone_icon);


            if(blood_icon != null) {
                Resources res = getContext().getResources();
                String temp = app.getBlood_group().toString();

                if(temp.equals("A positive")) temp = "one";
                else if(temp.equals("A negative")) temp = "two";
                else if(temp.equals("B positive")) temp = "three";
                else if(temp.equals("B negative")) temp = "four";
                else if(temp.equals("O positive")) temp = "five";
                else if(temp.equals("O negative")) temp = "six";
                else if(temp.equals("AB positive")) temp = "seven";
                else temp = "eight";

                String sIcon = "com.practice.customlistviewpractice:drawable/" + temp;
                blood_icon.setImageDrawable(res.getDrawable(res.getIdentifier(sIcon, null, null)));
            }

            if(donor_name != null) donor_name.setText(app.getName());

            if(donor_location != null) {
                donor_location.setText(app.getLocation());
            }

            if(phone_icon != null) {
                Resources res = getContext().getResources();
                String sIcon = "com.practice.customlistviewpractice:drawable/phone_icon";
                phone_icon.setImageDrawable(res.getDrawable(res.getIdentifier(sIcon, null, null)));
                phone_icon.setTag(app.getPhone());


                phone_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_icon.getTag().toString()));
                        getContext().startActivity(in);
                    }
                });

                phone_icon.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(v==phone_icon)
                        {
                            if(event.getAction() == MotionEvent.ACTION_DOWN){
                                v.setAlpha(.64f);
                            }
                            else
                            {
                                v.setAlpha(1f);
                            }

                        }
                        return false;
                    }
                });
            }
        }

        return v;
    }
}