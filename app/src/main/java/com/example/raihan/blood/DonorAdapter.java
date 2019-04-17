package com.example.raihan.blood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import model.Donor;

/**
 * Created by Rayhaan on 02-Feb-18.
 */

public class  DonorAdapter extends ArrayAdapter<Donor> {


    public DonorAdapter(Context context, ArrayList<Donor> donor)
    {
        super(context , 0 , donor);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Donor donor = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_row_display, parent, false);
        }

        TextView nameTV = (TextView) convertView.findViewById(R.id.donorNameET);
        TextView bloodGrpTV = (TextView) convertView.findViewById(R.id.donorGroupTV);
        TextView phoneTV = (TextView) convertView.findViewById(R.id.donorPhoneTV);
        TextView locationTV = (TextView) convertView.findViewById(R.id.donorAddressTV);

        String name=donor.getFirstName()+" "+donor.getLastName();

        nameTV.setText(name.toUpperCase());
        bloodGrpTV.setText(donor.getBlood_group());
        phoneTV.setText(donor.getPhoneNumber());
        locationTV.setText(donor.getLocation().toUpperCase());

        return convertView;
    }
}
