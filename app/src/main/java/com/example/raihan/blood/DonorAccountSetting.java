package com.example.raihan.blood;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import database.DatabaseHandler;

public class DonorAccountSetting extends AppCompatActivity {

    final DatabaseHandler db=new DatabaseHandler(this);

    EditText emailBox,oldpassBox,newPassBox,confirmPassBox;
    Button updateButton,deleteButton;
    Switch flagButton;

    String userNameValue;
    String userPasswordValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_account_setting);

        Intent i =getIntent();
        userNameValue = i.getExtras().getString(UserHomeActivity.USER_NAME_Value);
        userPasswordValue=i.getExtras().getString(UserHomeActivity.USER_Password_Value);

        emailBox=(EditText)findViewById(R.id.accStEmailET);
        oldpassBox=(EditText)findViewById(R.id.accStPasswordET);
        newPassBox=(EditText)findViewById(R.id.accStNewPasswordET);
        confirmPassBox=(EditText)findViewById(R.id.accStConfirmPasswordET);

        updateButton=(Button)findViewById(R.id.accStUpdateButton);
        deleteButton=(Button)findViewById(R.id.deleteAccBtn);
        flagButton=(Switch)findViewById(R.id.donationStatusSwitchBtn);

        if(db.getVisibilityStatus(userNameValue)==1)
        {
            flagButton.setChecked(true);
        }

    }

    public void onAccStUpdateButtonClick(View view) {

        String email=emailBox.getText().toString();
        String oldPass=oldpassBox.getText().toString();
        String newPass=newPassBox.getText().toString();
        String confirmPass=confirmPassBox.getText().toString();

        if(email.equals("")||oldPass.equals("")||newPass.equals("")||confirmPass.equals(""))
        {
            Toast.makeText(getApplicationContext(), "ALL FIELDS ARE REQUIRED", Toast.LENGTH_SHORT).show();
        }
        if(!Validator.isValidEmail(email))
        {
            emailBox.setError(Validator.emailPatternError);
        }
        else if(!Validator.isValidPassword(newPass))
        {
            newPassBox.setError(Validator.passwordPatternError);
        }
        else{
            if(oldPass.equals(userPasswordValue) && newPass.equals(confirmPass))
            {
                try{

                    if(db.updateProfileCredential(userNameValue,email,newPass)>0)
                    {
                        Toast.makeText(getApplicationContext(), "Profile Updated !", Toast.LENGTH_SHORT).show();
                        userNameValue=email;
                        Intent i = new Intent(this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Failed To Update Profile!", Toast.LENGTH_SHORT).show();

                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), "Error Updating Profile Info!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Password Mismatch !", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void onAccStDeleteButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning!");
        builder.setMessage("Confirm to proceed...");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Processing...", Toast.LENGTH_SHORT).show();
                db.deleteUserAccount(userNameValue);
                Toast.makeText(getApplicationContext(), "Account Deleted!", Toast.LENGTH_SHORT).show();
                toMain();
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
    void toMain(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }


    public void onAccStSwitchButtonClick(View view) {

        String flag=flagButton.getText().toString();
        boolean isChecked=flagButton.isChecked();

        int status = 0;
        if(isChecked==true)
            status=1;


        if(db.updateDonationFlag(userNameValue,status)>0)
        {
            Toast.makeText(getApplicationContext(), "Visibility "+isChecked, Toast.LENGTH_SHORT).show();

        }
    }
}
