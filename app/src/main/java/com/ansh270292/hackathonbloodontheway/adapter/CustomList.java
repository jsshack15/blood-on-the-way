package com.ansh270292.hackathonbloodontheway.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ansh270292.hackathonbloodontheway.R;


/**
 * Created by Ansh on 11/15/2015.
 */
public class CustomList extends ArrayAdapter<String> {

    private String[] names;
    private String[] addresses;
    private String[] mobile;
    private String[] ages;
    private Activity context;

    public CustomList(Activity context, String[] mobile, String[] names, String[] address,String[] age) {
        super(context, R.layout.list_items, mobile);
        this.context = context;
        this.mobile = mobile;
        this.names = names;
        this.addresses = address;
        this.ages = age;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.donorname);
        TextView textViewMobile = (TextView) listViewItem.findViewById(R.id.donormobile);
        TextView textViewAdress = (TextView) listViewItem.findViewById(R.id.donoraddress);
        TextView textViewAge = (TextView) listViewItem.findViewById(R.id.donorage);

        textViewMobile.setText(mobile[position]);
        textViewName.setText(names[position]);
        textViewAdress.setText(addresses[position]);
        textViewAge.setText(ages[position]);

        return listViewItem;
    }
}