package com.example.raihan.blood;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHandler;
import model.Donor;

public class SearchResultActivity extends AppCompatActivity {

    public static String USER_ID= "UserId";
    public static String USER_OBJECT= "UserObj";

    final DatabaseHandler db = new DatabaseHandler(this);

    List<Donor> availableDonorList;

    TextView phoneTV;

    private ListView donorListView;

    String bloodGroupValue,locationValue,districtValue,stationValue;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent i = getIntent();
        id=i.getExtras().getInt(SearchDonorActivity.USER_ID);
        bloodGroupValue = i.getExtras().getString(SearchDonorActivity.BLOOD_GROUP);
        locationValue = i.getExtras().getString(SearchDonorActivity.LOCATION);
        stationValue = i.getExtras().getString(SearchDonorActivity.STATION);
        districtValue = i.getExtras().getString(SearchDonorActivity.DISTRICT);

        donorListView = (ListView) findViewById(R.id.donorListLV);
        getDonorList();
        setDonorList();

        donorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Donor donor = availableDonorList.get(position);
                int donorId=donor.getUserId();

                Intent i = new Intent(SearchResultActivity.this,ViewDetailsActivity.class);
                i.putExtra(USER_ID,donorId);
                i.putExtra(USER_OBJECT, donor);

                startActivity(i);                /*
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:"+donor.getPhoneNumber()));
                    startActivity(callIntent);
`               */

/*
                final Dialog dialog = new Dialog(getApplicationContext());

                dialog.setContentView(R.layout.layout_call_to_donor_option);
                dialog.setTitle("Contact With Donor Via Phone Call or SMS");

                Button callBtn=(Button)dialog.findViewById(R.id.callButton);
                Button smsBtn=(Button)dialog.findViewById(R.id.smsButton);

                callBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Intent callIntent = new Intent(Intent.ACTION_DIAL);
                         callIntent.setData(Uri.parse("tel:"+donor.getContactNumber()));
                         startActivity(callIntent);
                    }
                });

                smsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.setData(Uri.parse("smsto:"+donor.getContactNumber()));
                        startActivity(sendIntent);
                    }
                });

                dialog.show();
*/

            }

        });
    }

    public void getDonorList() {

        try {
            availableDonorList = db.getAllDonor(bloodGroupValue,locationValue,stationValue,districtValue,id);

            if (availableDonorList.size() == 0)
                Toast.makeText(getApplicationContext(), "Sorry! No Donor Available !", Toast.LENGTH_SHORT).show();
            else{
                Toast.makeText(getApplicationContext(), "Donor Found: "+availableDonorList.size() , Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setDonorList(){
        DonorAdapter itemsAdapter = new DonorAdapter(this, (ArrayList<Donor>) availableDonorList);

        donorListView.setAdapter(itemsAdapter);

    }

    public int getConfirmation()
    {
        final int[] result = {0};
        final int res=0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm!");
        builder.setMessage("Request to donate ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_SHORT).show();
                result[0] =1;
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "No pressed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();

        return result[0];
    }
}