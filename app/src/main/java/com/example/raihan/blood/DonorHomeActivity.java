package com.example.raihan.blood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import database.DatabaseHandler;

public class DonorHomeActivity extends AppCompatActivity {

    final DatabaseHandler db=new DatabaseHandler(this);
    public static String USER_ID= "UserId";
    public static String USER_NAME_Value= "UserName";
    public static String USER_Password_Value= "UserPassword";

    int id;
    private String userName,userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);

        Intent i =getIntent();
        try {
            id=i.getExtras().getInt(LoginActivity.USER_ID);
            userName = i.getExtras().getString(LoginActivity.USER_NAME);
            userPassword=i.getExtras().getString(LoginActivity.USER_Password);
        }
        catch (Exception e){
            id=i.getExtras().getInt(UserHomeActivity.USER_ID);
            userName = i.getExtras().getString(UserHomeActivity.USER_NAME_Value);
            userPassword=i.getExtras().getString(UserHomeActivity.USER_Password_Value);
        }

    }

    public void onBeCheckRequestButtonClick(View view) {
    }

    public void onExitButtonClick(View view) {
        this.finish();
    }

    public void onBloodBankButtonClick(View view) {
        Intent i = new Intent(this, BloodBankListActivity.class);
        startActivity(i);
    }

    public void onAccStButtonClick(View view) {
        Intent i = new Intent(this, DonorAccountSetting.class);
        i.putExtra(USER_NAME_Value , userName);
        i.putExtra(USER_Password_Value,userPassword);
        startActivity(i);
    }

    public void onProfileButtonClick(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra(USER_NAME_Value , userName);
        i.putExtra(USER_Password_Value,userPassword);
        startActivity(i);
    }

    public void onSearchButtonClick(View view) {
        Intent i = new Intent(this,SearchDonorActivity.class);
        i.putExtra(USER_ID , id);
        startActivity(i);
    }
}
