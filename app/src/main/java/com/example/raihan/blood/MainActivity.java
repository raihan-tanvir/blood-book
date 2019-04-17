package com.example.raihan.blood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Button signupBtn,loginBtn,searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn=(Button) findViewById(R.id.homeLoginButton);
        signupBtn=(Button) findViewById(R.id.homeSignUPButton);
        searchBtn=(Button) findViewById(R.id.homeSearchButton);

    }
    public void onLoginButtonClick(View view) {

        startActivity(new Intent(this,LoginActivity.class));
    }
    public void onSignUpButtonClick(View view) {

        startActivity(new Intent(this,RegistrationActivity.class));
    }

    public void onSearchButtonClick(View view) {
        startActivity(new Intent(this,SearchDonorActivity.class));

    }
}
