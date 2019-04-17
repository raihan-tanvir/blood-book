package com.example.raihan.blood;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.BloodBank;
import model.Donor;

/**
 * Created by Rayhaan on 02-Feb-18.
 */

public class BloodBankAdapter extends ArrayAdapter<BloodBank> {


    public BloodBankAdapter(Context context, ArrayList<BloodBank> bloodBanks)
    {
        super(context , 0 , bloodBanks);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BloodBank bloodBank = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_row_blood_bank, parent, false);
        }

        TextView nameTV = (TextView) convertView.findViewById(R.id.bbNameTV);
        TextView phoneTV = (TextView) convertView.findViewById(R.id.bbContactTV);
        TextView locationTV = (TextView) convertView.findViewById(R.id.bbLocationTV);
        TextView emailTV = (TextView) convertView.findViewById(R.id.bbEmailTV);


        nameTV.setText(bloodBank.getName());
        emailTV.setText(bloodBank.getEmail());
        phoneTV.setText(bloodBank.getContactNo());
        locationTV.setText(bloodBank.getAddress());

        Log.d("checkBB", "getView: "+nameTV.getText().toString());
        return convertView;
    }
}
