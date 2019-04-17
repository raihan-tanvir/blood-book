package com.example.raihan.blood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import database.DatabaseHandler;
import model.User;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private EditText confirmUserPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    final DatabaseHandler db=new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userEmail = (EditText) findViewById(R.id.regEmailET);
        userPassword= (EditText) findViewById(R.id.regpasswordET);
        confirmUserPassword=(EditText)findViewById(R.id.confirmPasswordET);

        firebaseAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("user");

       // databaseReference= FirebaseDatabase.getInstance().getReference("USER");
    }

    public void onRegistrationButtonClick(View view) {
        String email=userEmail.getText().toString().trim();
        String password=userPassword.getText().toString().trim();
        String rePassword=confirmUserPassword.getText().toString().trim();
        /*
        if (email.equals("") || password.equals("") || rePassword.equals("")) {
            Toast.makeText(RegistrationActivity.this, "All Field Are Required!", Toast.LENGTH_SHORT).show();
        }

        if(password.length()<6)
        {
            Toast.makeText(RegistrationActivity.this, "password must be minimum 6 character long", Toast.LENGTH_SHORT).show();
        }
        */

        if (email.equals("") || password.equals("") || rePassword.equals("")) {
            Toast.makeText(RegistrationActivity.this, "All Field Are Required!", Toast.LENGTH_SHORT).show();
        }

        if(!Validator.isValidEmail(email))
        {
            userEmail.setError(Validator.emailPatternError);
        }
        else if(!Validator.isValidPassword(password))
        {
            userPassword.setError(Validator.passwordPatternError);
        }

        else {
            final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this, "please wait...", "Processing..", true);

            if (password.equals(rePassword)) {
                //firebase signup
/*
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                String userId=mDatabase.push().getKey();

              //  Donor donor=new Donor(email,null,null,null,null,null,null,null,password);

                User user=new User(email,password);
              //  Log.d("donor", donor.getEmail());
                mDatabase.child(userId).setValue(user);
    */
                progressDialog.dismiss();
                if(db.addUser(new User(email, password))){
                    Toast.makeText (getApplicationContext(),"Registration Successful.",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegistrationActivity.this,MainActivity.class);
                    i.putExtra("Email",email);
                    startActivity(i);
                    this.finish();
                }
                else
                    Toast.makeText (getApplicationContext(),"Email is assoiciated with a registered donor",Toast.LENGTH_SHORT).show();


            } else {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Password Mismatch!", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
