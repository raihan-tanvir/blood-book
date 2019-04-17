package com.example.raihan.blood;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import database.DatabaseHandler;
import model.Donor;
import model.User;

public class ViewDetailsActivity extends AppCompatActivity {
    public static String DONOR_ID= "DonorId";
    public static String RECEIVER_ID= "RecieverId";

    final DatabaseHandler db=new DatabaseHandler(this);

    TextView name,bloodGroup,gender,email,address,phone;
    Button callBtn,smsBtn,reqBtn;
    ImageView imgView;
    int id;
    Donor donor=new Donor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Intent i = getIntent();
        id=i.getExtras().getInt(SearchResultActivity.USER_ID);
     //   donor=(Donor) i.getSerializableExtra(SearchResultActivity.USER_OBJECT);

        imgView=findViewById(R.id.donorImageView);
        name=(TextView) findViewById(R.id.viewNameTV);
        bloodGroup=findViewById(R.id.viewGroupTV);
        gender=findViewById(R.id.viewGenderTV);
        email=findViewById(R.id.viewEmailTV);
        address=findViewById(R.id.viewAddressTV);
        phone=findViewById(R.id.viewPhoneTV);

        callBtn=findViewById(R.id.callDonorBtn);
        smsBtn=findViewById(R.id.smsDonorBtn);
        reqBtn=findViewById(R.id.reqDonorBtn);

        donor=db.getDonorInfo(id);

        setDonorInfo();
    }
    void setDonorInfo(){


        String fullName=donor.getFirstName()+" "+donor.getLastName();
        String location=donor.getLocation()+", "+donor.getDistrict();

        name.setText(fullName.toUpperCase());
        bloodGroup.setText(donor.getBlood_group());
        gender.setText(donor.getGender());
        email.setText(donor.getEmail());
        phone.setText(donor.getPhoneNumber());
        address.setText(location.toUpperCase());
        /*
        if(donor.getVisibility()==0)
        {
            phone.setTransformationMethod(new PasswordTransformationMethod());
            email.setTransformationMethod(new PasswordTransformationMethod());
            callBtn.setVisibility(View.GONE);
            smsBtn.setVisibility(View.GONE);
            reqBtn.setVisibility(View.VISIBLE);
        }
        */
    }

    public void callDonor(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make phone call!");
        builder.setMessage("Confirm to proceed...");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+donor.getPhoneNumber()));
                startActivity(callIntent);
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

    public void textDonor(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send Message!");
        builder.setMessage("Confirm to proceed...");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("smsto:"+donor.getPhoneNumber()));
                startActivity(sendIntent);
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

    public void requestDonor(View view) {

    }
}
