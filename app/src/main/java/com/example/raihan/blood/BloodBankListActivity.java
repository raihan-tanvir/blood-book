package com.example.raihan.blood;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHandler;
import model.BloodBank;
import model.Donor;

public class BloodBankListActivity extends AppCompatActivity {
    final DatabaseHandler db = new DatabaseHandler(this);
    private ListView bloodBankLV;
    List<BloodBank> bbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_list);

        bloodBankLV = (ListView) findViewById(R.id.bloodBankListLV);

        getBBList();
        //setBBList();

        bloodBankLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final BloodBank bloodBank = bbList.get(position);
                promtCall(bloodBank);
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

    public void getBBList() {

        try {
            bbList = db.getBloodBankList();

            if (bbList.size() == 0)
                Toast.makeText(getApplicationContext(), "Sorry! No Blood Bank Found !", Toast.LENGTH_SHORT).show();
            else{
                Toast.makeText(getApplicationContext(), "Blood Bank Found: "+bbList.size() , Toast.LENGTH_SHORT).show();
                setBBList();
            }
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
        }
    }
    public void setBBList(){
        BloodBankAdapter itemsAdapter = new BloodBankAdapter(this, (ArrayList<BloodBank>) bbList);

        bloodBankLV.setAdapter(itemsAdapter);

    }
    public void promtCall(final BloodBank bloodBank){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm!");
        builder.setMessage("Contact with "+bloodBank.getName());
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_SHORT).show();
                callBloodBank(bloodBank);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "No pressed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
    public void callBloodBank(BloodBank bloodBank) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+bloodBank.getContactNo()));
        startActivity(callIntent);
    }

}
