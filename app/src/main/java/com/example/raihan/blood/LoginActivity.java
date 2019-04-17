package com.example.raihan.blood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import database.DatabaseHandler;
import model.User;

public class LoginActivity extends AppCompatActivity {

    public static String USER_ID= "UserId";
    public static String USER_NAME= "UserName";
    public static String USER_Password= "UserPassword";
    public static String USER_STATUS="status";

    private EditText loginEmail;
    private EditText loginPassword;
    private CheckBox passShowCB;
    private FirebaseAuth firebaseAuth;

    final DatabaseHandler db=new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail=findViewById(R.id.loginEmailET);

        loginPassword = (EditText) findViewById(R.id.loginPasswordET);

        passShowCB = (CheckBox)findViewById(R.id.showPassCB);

        firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseHandler db=new DatabaseHandler(this);

    }

    public void onCheckBoxClick(View view) {
        if(passShowCB.isChecked()){
            loginPassword.setTransformationMethod(null);
        }
        if(!(passShowCB.isChecked())){
            loginPassword.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    public void onLoginButtonClick(View view) {

        final String email=loginEmail.getText().toString().trim();
        final String password=loginPassword.getText().toString().trim();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Email or Password Field is Empty!", Toast.LENGTH_LONG).show();
        }

        if(!Validator.isValidEmail(email))
        {
            loginEmail.setError(Validator.emailPatternError);
        }
        /*
        else if(!Validator.isValidPassword(password))
        {
            loginPassword.setError(Validator.passwordPatternError);
        }
        */
        else {
            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "Proccessing...", true);
            progressDialog.dismiss();
            try{
                /*
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(LoginActivity.this, UserHomeActivity.class);
                                    i.putExtra(USER_NAME , email);
                                    i.putExtra(USER_Password,password);
                                    startActivity(i);
                                } else {
                                    Log.e("ERROR", task.getException().toString());
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                  */
                 User user=db.getAuthentication(new User(email,password));
               // int status=db.getAuthentication(new User(email,password));
                if(user!=null) {
                    Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                    
                    Intent i = null;
                    if(user.getStatus()==2) {
                        i = new Intent(this, DonorHomeActivity.class);
                    }
                    else {
                        i = new Intent(this, UserHomeActivity.class);
                    }
                    i.putExtra(USER_NAME , email);
                    i.putExtra(USER_Password,password);
                    i.putExtra(USER_ID,user.getUserId());
                    i.putExtra(USER_STATUS,user.getStatus());

                    startActivity(i);
                    this.finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
