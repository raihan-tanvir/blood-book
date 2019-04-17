package com.example.raihan.blood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import model.User;
import database.DatabaseHandler;

public class ProfileActivity extends AppCompatActivity {

    final DatabaseHandler db=new DatabaseHandler(this);

    EditText userEmail,userPassword;
    EditText name1,name2,phoneNo,location,confirmPassword,district,station;

    TextView userGender,userBloodGrp;

    Spinner bloodSpinner;

    RadioGroup genderGrp;
    RadioButton genderBtn;

    Button updateBtn;

    String userNameValue;
    String userPasswordValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i =getIntent();

        userNameValue = i.getExtras().getString(UserHomeActivity.USER_NAME_Value);
        userPasswordValue=i.getExtras().getString(UserHomeActivity.USER_Password_Value);


        userPassword=(EditText)findViewById(R.id.profilePasswordET);
       // userPassword.setText(userPasswordValue);

        name1=(EditText)findViewById(R.id.firstNameET);
        name2=(EditText)findViewById(R.id.lastNameET);

        bloodSpinner=(Spinner)findViewById(R.id.bloodGroupSpinner);

        genderGrp=(RadioGroup)findViewById(R.id.radioGenderGroup);


        phoneNo=(EditText)findViewById(R.id.profilePhoneET);
        location=(EditText)findViewById(R.id.addressET);
        district=(EditText)findViewById(R.id.districtET);
       // station=(EditText)findViewById(R.id.stationET);

        updateBtn=(Button) findViewById(R.id.profileUpdateButton);

        userGender=(TextView)findViewById(R.id.sexTV);
        userBloodGrp=(TextView)findViewById(R.id.bloodGroupTV);

        setProfileInfo(userNameValue);
    }

    public void  setProfileInfo(String user_id)
    {
        try{
            User user=db.getProfileInfo(user_id);

            if(user!=null)
            {
                Toast.makeText(getApplicationContext(), "Fetching Profile Info!", Toast.LENGTH_SHORT).show();

                name1.setText(user.getFirstName());
                name2.setText(user.getLastName());
                phoneNo.setText(user.getPhoneNumber());
                location.setText(user.getLocation());
                district.setText(user.getDistrict());
               // station.setText(user.getPolice_station());

                userGender.setText(user.getGender());
                userBloodGrp.setText(user.getBlood_group());

                String bg=user.getBlood_group();
                String sex=user.getGender();
                userGender.setText(sex);

                if(!(bg.equals(null)))
                {
                    bloodSpinner.setEnabled(false);
                }

                if(bg.equals("A+")){
                    bloodSpinner.setSelection(1);
                }
                else if(bg.equals("A-")){
                    bloodSpinner.setSelection(2);
                }

                else if(bg.equals("B+")){
                    bloodSpinner.setSelection(3);
                }

                else if(bg.equals("B-")){
                    bloodSpinner.setSelection(4);
                }

                else if(bg.equals("AB+")){
                    bloodSpinner.setSelection(5);
                }
                else if(bg.equals("AB-")){
                    bloodSpinner.setSelection(6);
                }

                else if(bg.equals("O+")){
                    bloodSpinner.setSelection(7);
                }
                else if(bg.equals("O-")){
                    bloodSpinner.setSelection(8);
                }

            }
        }
        catch (Exception ex)
        {
           // Toast.makeText(getApplicationContext(), "Error Fetching Profile Info!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUpdateButtonClick(View view) {

        int selectedId=genderGrp.getCheckedRadioButtonId();
        genderBtn=(RadioButton)findViewById(selectedId);

        try{
            String fname=name1.getText().toString().toUpperCase().trim();
            String lname=name2.getText().toString().toUpperCase().trim();
            String phone=phoneNo.getText().toString();
            String loc=location.getText().toString().toUpperCase().trim();
            String dist=district.getText().toString().toUpperCase().trim();
           // String ps=station.getText().toString().toUpperCase().trim();
            String ps="dummy";

            String password=userPassword.getText().toString();
            String grp=bloodSpinner.getSelectedItem().toString();
            String sex=genderBtn.getText().toString();

            User user=new User();
            user.setEmail(userNameValue);
            user.setPassword(password);
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setPhoneNumber(phone);

            user.setLocation(loc);
            user.setDistrict(dist);
            user.setPolice_station(ps);

            user.setPhotoUrl(loc);
            user.setGender(genderBtn.getText().toString());

            user.setBlood_group(grp);
            user.setStatus(1);

            if(fname.equals("")||lname.equals("")||phone.equals("")||loc.equals("") ||dist.equals("")||ps.equals("")
                    ||password.equals("")||sex.equals("") || grp.equalsIgnoreCase("Select Blood Group")) {
                Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            }

            else if(!Validator.isValidText(fname)) {
                name1.setError(Validator.textPatternError);
            }
            else if(!Validator.isValidText(lname)) {
                name2.setError(Validator.textPatternError);
            }
            else if(!Validator.isValidPhone(phone)) {
                phoneNo.setError(Validator.phonePatternError);
            }
            else if(!Validator.isValidText(dist)) {
                district.setError(Validator.textPatternError);
            }
            else if(!Validator.isValidText(ps)) {
                station.setError(Validator.textPatternError);
            }
            else {
                if(password.equals(userPasswordValue)){

                    if(db.updateUserProfile(user)>0) {
                        Toast.makeText(getApplicationContext(), "Profile Updated !", Toast.LENGTH_LONG).show();
                        setProfileInfo(userNameValue);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Profile Update Failed!", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Password Empty or Mismatched!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error Updating Profile Info!", Toast.LENGTH_SHORT).show();
        }
    }
}


