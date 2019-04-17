package com.example.raihan.blood;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import database.DatabaseHandler;

public class UserHomeActivity extends AppCompatActivity {

    final DatabaseHandler db=new DatabaseHandler(this);
    public static String USER_ID= "UserId";
    public static String USER_NAME_Value= "UserName";
    public static String USER_Password_Value= "UserPassword";
    public static String USER_STATUS="status";

    private String userName,userPassword;
    int id,status;

    Button donorBtn,checkReqBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Intent i =getIntent();
        userName = i.getExtras().getString(LoginActivity.USER_NAME);
        userPassword=i.getExtras().getString(LoginActivity.USER_Password);
        id=i.getExtras().getInt(LoginActivity.USER_ID);
        status=i.getExtras().getInt(LoginActivity.USER_STATUS);

        donorBtn=(Button)findViewById(R.id.gotoBeDonorBtn);
        if(status==2){
            donorBtn.setVisibility(View.INVISIBLE);
        }

    }


    public void onProfileButtonClick(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra(USER_NAME_Value , userName);
        i.putExtra(USER_Password_Value,userPassword);
        startActivity(i);
    }

    public void onAccStButtonClick(View view) {
        Intent i = new Intent(this, UserAccountSettingActivity.class);
        i.putExtra(USER_NAME_Value , userName);
        i.putExtra(USER_Password_Value,userPassword);
        startActivity(i);
    }


    public void onSearchButtonClick(View view) {
        Intent i = new Intent(this,SearchDonorActivity.class);
        i.putExtra(USER_ID , id);
        startActivity(i);
    }

    public void onExitButtonClick(View view) {
        this.finish();
    }

    public void onBeDonorButtonClick(View view) {
        final boolean[] flag = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm!");
        builder.setMessage("You are about to register yourself as donor. Do you really want to proceed ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_SHORT).show();
                flag[0] =true;
                Log.d("Flag", "onClick: "+flag[0]);
                registerDonor();
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

    public void onBloodBankButtonClick(View view) {
        Intent i = new Intent(this, BloodBankListActivity.class);
        startActivity(i);
    }

    public void registerDonor()
    {
        if(db.addDonor(userName))
        {
            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, DonorHomeActivity.class);
            i.putExtra(USER_NAME_Value , userName);
            i.putExtra(USER_Password_Value,userPassword);
            i.putExtra(USER_ID,id);

            startActivity(i);
        }else{
            Toast.makeText(getApplicationContext(), "Please complete profile first!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, ProfileActivity.class);
            i.putExtra(USER_NAME_Value , userName);
            i.putExtra(USER_Password_Value,userPassword);
            startActivity(i);
        }
    }
}
